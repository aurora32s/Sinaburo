package com.haman.dearme.ui.screen.challenge.post

import android.net.Uri
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haman.dearme.data.db.entity.toModel
import com.haman.dearme.domain.usecase.*
import com.haman.dearme.ui.model.*
import com.haman.dearme.ui.screen.post.PostUiState
import com.haman.dearme.util.ext.toLocalDate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class ChallengePostViewModel @Inject constructor(
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val addChallengeUseCase: AddChallengeUseCase,
    private val getChallengeByIdUseCase: GetChallengeByIdUseCase,
    private val getChallengeDetailByIdUseCase: GetChallengeDetailByIdUseCase,
    private val changeChallengeDetailStateUseCase: ChangeChallengeDetailStateUseCase,
    private val getCompletedChallengeUseCase: GetCompletedChallengeUseCase,
    private val addCategoryUseCase: AddCategoryUseCase,
    private val removeChallengeUseCase: RemoveChallengeUseCase
) : ViewModel() {

    private val _challengeUiState =
        MutableStateFlow<ChallengeUiState>(ChallengeUiState.UnInitialized)
    val challengeUiState = _challengeUiState.asStateFlow()

    val categories = getCategoriesUseCase()
        .map { it.map { category -> category.toModel() } }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = emptyList()
        )

    private val _isModifyMode = MutableStateFlow(true)
    val isModifyMode = _isModifyMode.asStateFlow()

    fun toggleModifyMode() {
        if (_isModifyMode.value.not()) _isModifyMode.value = true
        else {
            init(id = challengeId!!)
        }
    }

    var challengeId: Long? = null
        private set

    private val _name = MutableStateFlow("")
    val name = _name.asStateFlow()

    fun changeName(newName: String) {
        _name.value = newName
    }

    private val _selectedCategoryId = MutableStateFlow<Long?>(null)
    val selectedCategoryId = _selectedCategoryId.asStateFlow()

    fun setSelectedCategoryId(categoryId: Long) {
        _selectedCategoryId.value = categoryId
    }

    private val _content = MutableStateFlow("")
    val content = _content.asStateFlow()

    fun changeContent(newContent: String) {
        _content.value = newContent
    }

    private val deletedPlans = mutableListOf<Long>()
    private val _details = mutableStateListOf<ChallengeDetailModel>()
    val detail: MutableList<ChallengeDetailModel>
        get() = _details

    fun addDetail() {
        _details.add(ChallengeDetailModel(title = ""))
    }

    fun removeDetailChallenge(index: Int) {
        _details[index].id?.let { deletedPlans.add(it) }
        _details.removeAt(index)
    }

    fun completeChallenge(challenge: ChallengeDetailModel) {
        val index = _details.indexOf(challenge)
        val newChallenge = challenge.copy(
            completed = challenge.completed.not(),
            date = if (challenge.completed) null else LocalDate.now()
        )

        viewModelScope.launch {
            changeChallengeDetailStateUseCase(newChallenge)
                .onSuccess { _details[index] = newChallenge }
        }
    }

    fun changeDetailChallenge(index: Int, newContent: String) {
        _details[index] = _details[index].copy(title = newContent)
    }

    private val _selectedImageUri = MutableStateFlow<Uri?>(null)
    val selectedImageUri = _selectedImageUri.asStateFlow()

    fun setSelectedImageUri(imageUri: Uri) {
        _selectedImageUri.value = imageUri
    }

    private val _selectedDate = mutableStateListOf<LocalDate>()
    val selectedDate: MutableList<LocalDate>
        get() = _selectedDate

    fun selectDate(date: LocalDate) {
        if (_selectedDate.size == 2) {
            _selectedDate.clear()
        }
        _selectedDate.add(date)
    }

    private val _completedChallenge = MutableStateFlow<List<CompletedChallengeModel>>(emptyList())
    val completedChallenge = _completedChallenge.asStateFlow()

    fun addChallenge() {
        // 유효성 검사 1. 제목
        if (_name.value.isBlank()) return
        // 유효성 검사 3. 기간
        if (_selectedDate.size != 2) return
        // 유효성 검사 4. 세부일정이 있을 경우, title 을 다 적었는지
        if (_details.any { it.title.isBlank() }) return

        viewModelScope.launch {
            val challenge = ChallengeModel(
                id = challengeId,
                title = _name.value,
                categoryId = _selectedCategoryId.value,
                content = _content.value,
                startedAt = _selectedDate.minOf { it },
                endedAt = _selectedDate.maxOf { it },
                image = _selectedImageUri.value
            )
            addChallengeUseCase(
                challenge = challenge,
                detailChallenge = _details,
                ids = deletedPlans
            ).onSuccess {
                challengeId = it
                toggleModifyMode()
            }.onFailure {
                println(it.message)
            }
        }
    }

    fun init(id: Long) {
        viewModelScope.launch {
            val challengeResult = async { getChallengeByIdUseCase(id) }
            val detailResult = async { getChallengeDetailByIdUseCase(id) }
            val completedChallengeResult = async { getCompletedChallengeUseCase(id) }

            challengeResult.await().onSuccess {
                it.toModel().run {
                    challengeId = this.id
                    _name.value = this.title
                    _content.value = this.content
                    _selectedCategoryId.value = this.categoryId
                    _selectedDate.clear()
                    _selectedDate.addAll(listOf(this.startedAt, this.endedAt))
                    _selectedImageUri.value = this.image
                }
            }
            detailResult.await().onSuccess {
                _details.clear()
                _details.addAll(it.map { it.toModel() })
//                _details = it.map { it.toModel() }.toMutableStateList()
            }
            completedChallengeResult.await().onSuccess {
                _completedChallenge.value = it.map { it.toModel() }
            }
            _isModifyMode.value = false
            challengeId = id
        }
    }

    fun addCategory(categoryModel: CategoryModel) {
        if (categoryModel.name.isBlank()) return
        _challengeUiState.value = ChallengeUiState.Loading
        viewModelScope.launch {
            addCategoryUseCase(categoryModel = categoryModel)
                .onSuccess {
                    _challengeUiState.value = ChallengeUiState.Success.ADD_CATEGORY
                }
        }
    }

    fun removeChallenge() {
        challengeId?.let {
            viewModelScope.launch {
                _challengeUiState.value = ChallengeUiState.Loading
                removeChallengeUseCase(it)
                    .onSuccess {
                        _challengeUiState.value = ChallengeUiState.Success.REMOVE_CHALLENGE
                    }
            }
        }
    }
}

sealed interface ChallengeUiState {
    object UnInitialized : ChallengeUiState
    object Loading : ChallengeUiState
    enum class Success : ChallengeUiState {
        ADD_CATEGORY,
        REMOVE_CHALLENGE
    }
}
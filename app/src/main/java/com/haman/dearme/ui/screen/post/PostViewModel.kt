package com.haman.dearme.ui.screen.post

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haman.dearme.data.db.entity.toModel
import com.haman.dearme.domain.usecase.*
import com.haman.dearme.ui.model.CategoryModel
import com.haman.dearme.ui.model.DetailModel
import com.haman.dearme.ui.model.plan.PlanModel
import com.haman.dearme.ui.model.plan.PlanState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(
    private val getPlanUseCase: GetPlanUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val getDetailUseCase: GetDetailUseCase,
    private val addPlanUseCase: AddPlanUseCase,
    private val completeDetailPlanUseCase: CompleteDetailPlanUseCase,
    private val removePlanUseCase: RemovePlanUseCase,
    private val startPlanUseCase: StartPlanUseCase,
    private val pausePlanUseCase: PausePlanUseCase,
    private val stopPlanUseCase: StopPlanUseCase,
    private val getTimeListUseCase: GetTimeListUseCase,
    private val addCategoryUseCase: AddCategoryUseCase
) : ViewModel() {

    private val _postUiState = MutableStateFlow<PostUiState>(PostUiState.UnInitialized)
    val postUiState = _postUiState.asStateFlow()

    val categories = getCategoriesUseCase()
        .map { it.map { category -> category.toModel() } }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = emptyList()
        )

    private val _isModifyMode = MutableStateFlow(true)
    val isModifyMode = _isModifyMode.asStateFlow()

    private var plan: PlanModel? = null
    var planId: Long? = null
        private set

    fun toggleModifyMode() {
        if (_isModifyMode.value.not()) _isModifyMode.value = true
        else {
            getPlanInfo(postId = planId!!)
        }
    }

    fun timeStamp(postId: Long) = getTimeListUseCase(postId)
        .map { it.map { time -> time.toModel() } }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = emptyList()
        )

    fun init(postId: Long?) {
        postId ?: return
        getPlanInfo(postId)
    }

    private fun getPlanInfo(postId: Long) {
        viewModelScope.launch {
            val planResult = async { getPlanUseCase(postId) }
            val detailResult = async { getDetailUseCase(postId) }

            planResult.await().onSuccess { result ->
                plan = result.toModel()
                plan?.let {
                    _categoryName.value = it.title
                    _content.value = it.content
                    _selectedCategoryId.value = it.categoryId
                    _planState.value = it.state
                }
                planId = postId
            }
            detailResult.await().onSuccess { result ->
                _detailPlans = result.map { it.toModel() }.toMutableStateList()
            }
            _isModifyMode.value = false
        }
    }

    private val _categoryName = MutableStateFlow("")
    val categoryName = _categoryName.asStateFlow()

    fun setCategoryName(name: String) {
        _categoryName.value = name
    }

    private val _selectedCategoryId = MutableStateFlow<Long?>(null)
    val selectedCategoryId = _selectedCategoryId.asStateFlow()

    fun setSelectedCategoryId(categoryId: Long) {
        _selectedCategoryId.value = categoryId
    }

    private val _content = MutableStateFlow("")
    val content = _content.asStateFlow()

    fun setContent(newContent: String) {
        _content.value = newContent
    }

    private val deletedPlans = mutableListOf<Long>()
    private var _detailPlans = mutableStateListOf<DetailModel>()
    val detailPlans: MutableList<DetailModel>
        get() = _detailPlans

    fun addDetailPlans() {
        _detailPlans.add(DetailModel(title = ""))
    }

    fun removeDetailPlans(index: Int) {
        _detailPlans[index].id?.let { deletedPlans.add(it) }
        _detailPlans.removeAt(index)
    }

    fun changeDetailPlanContent(index: Int, detail: String) {
        _detailPlans[index] = _detailPlans[index].copy(title = detail)
    }

    fun completeDetailPlan(detailPlan: DetailModel) {
        val index = _detailPlans.indexOf(detailPlan)
        val newDetailPlan = detailPlan.copy(completed = detailPlan.completed.not())

        viewModelScope.launch {
            completeDetailPlanUseCase(newDetailPlan)
                .onSuccess { _detailPlans[index] = newDetailPlan }
        }
    }

    fun savePlan(year: Int, month: Int, day: Int) {
        // 유효성 검사 1. 제목
        if (_categoryName.value.isBlank()) return
        // 유효성 검사 3. 세부일정이 있을 경우, title 을 다 적었는지
        if (_detailPlans.size > 0 && _detailPlans.any { it.title.isBlank() }) return

        viewModelScope.launch {
            val tmpPlan = PlanModel(
                id = planId,
                title = _categoryName.value,
                content = _content.value,
                startedAt = plan?.startedAt ?: 0L,
                state = plan?.state ?: PlanState.INIT,
                year = plan?.year ?: year,
                month = plan?.month ?: month,
                day = plan?.day ?: day,
                categoryId = _selectedCategoryId.value
            )
            addPlanUseCase(tmpPlan, _detailPlans, deletedPlans)
                .onSuccess {
                    planId = it
                    toggleModifyMode()
                }
                .onFailure {
                    println(it)
                }
        }
    }

    fun removePlan() {
        planId ?: return

        viewModelScope.launch {
            removePlanUseCase(planId!!)
                .onSuccess {
                    _postUiState.value = PostUiState.Success.REMOVE_PLAN
                }
        }
    }

    private val _planState = MutableStateFlow(PlanState.INIT)
    val planState = _planState.asStateFlow()

    fun startPlan() {
        viewModelScope.launch {
            plan?.let {
                startPlanUseCase(it)
                    .onSuccess {
                        _planState.value = PlanState.START
                    }
            }
        }
    }

    fun pausePlan() {
        viewModelScope.launch {
            plan?.let {
                pausePlanUseCase(it)
                    .onSuccess { _planState.value = PlanState.PAUSE }
            }
        }
    }

    fun stopPlan() {
        viewModelScope.launch {
            plan?.let {
                stopPlanUseCase(it)
                    .onSuccess { _planState.value = PlanState.STOP }
            }
        }
    }

    fun addCategory(categoryModel: CategoryModel) {
        if (categoryModel.name.isBlank()) return
        _postUiState.value = PostUiState.Loading
        viewModelScope.launch {
            addCategoryUseCase(categoryModel = categoryModel)
                .onSuccess {
                    _postUiState.value = PostUiState.Success.ADD_CATEGORY
                }
        }
    }
}

sealed interface PostUiState {
    object UnInitialized : PostUiState
    object Loading : PostUiState
    enum class Success : PostUiState {
        REMOVE_PLAN,
        ADD_CATEGORY
    }
}
package com.haman.dearme.ui.screen.setting

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haman.dearme.data.db.entity.toModel
import com.haman.dearme.domain.usecase.*
import com.haman.dearme.ui.model.CategoryModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val addCategoryUseCase: AddCategoryUseCase,
    private val removeCategoryUseCase: RemoveCategoryUseCase,
    private val getAllScheduleUseCase: GetAllScheduleUseCase,
    private val getAllGoodPointUseCase: GetAllGoodPointUseCase,
    private val getAllBadPointUseCase: GetAllBadPointUseCase,
    private val getAllDiaryUseCase: GetAllDiaryUseCase,
    private val removeGoodPointUseCase: RemoveGoodPointUseCase,
    private val removeBadPointUseCase: RemoveBadPointUseCase,
    private val removeScheduleUseCase: RemoveScheduleUseCase
) : ViewModel() {
    private val _settingUiState = MutableStateFlow<SettingUiState>(SettingUiState.UnInitialized)
    val settingUiState = _settingUiState.asStateFlow()

    val categories = getCategoriesUseCase()
        .map { it.map { category -> category.toModel() } }
        .catch {
            // 에러 처리
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = emptyList()
        )
    val schedules = getAllScheduleUseCase()
        .map { it.map { schedule -> schedule.toModel() } }
        .catch { }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = emptyList()
        )
    val goods = getAllGoodPointUseCase()
        .map {
            it.map { point -> point.toModel() }.groupBy { LocalDate.of(it.year, it.month, it.day) }
        }
        .catch { }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = emptyMap()
        )
    val bads = getAllBadPointUseCase()
        .map {
            it.map { point -> point.toModel() }.groupBy { LocalDate.of(it.year, it.month, it.day) }
        }
        .catch { }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = emptyMap()
        )
    val diaries = getAllDiaryUseCase()
        .map { it.map { diary -> diary.toModel() } }
        .catch {
            Log.e("Setting", it.message ?: "Error")
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = emptyList()
        )

    private val _currentCategory = MutableStateFlow<CategoryModel?>(null)
    val currentCategory = _currentCategory.asStateFlow()

    fun setCurrentCategory(categoryModel: CategoryModel?) {
        _currentCategory.value = categoryModel
    }

    fun addCategory(categoryModel: CategoryModel) {
        if (categoryModel.name.isBlank()) {
            _settingUiState.value = SettingUiState.Error.LESS_CATEGORY_INFO
            return
        }
        _settingUiState.value = SettingUiState.Loading
        viewModelScope.launch {
            addCategoryUseCase(
                categoryModel = categoryModel
            ).onSuccess {
                _currentCategory.value = categoryModel
                _settingUiState.value = SettingUiState.Success.ADD_CATEGORY
            }.onFailure {
                println("fail to add category $it")
                _settingUiState.value = SettingUiState.Error.ADD_CATEGORY
            }
        }
    }

    fun removeCategory(categoryModel: CategoryModel) {
        categoryModel.id ?: return

        _settingUiState.value = SettingUiState.Loading
        viewModelScope.launch {
            removeCategoryUseCase(categoryModel)
                .onSuccess {
                    _settingUiState.value = SettingUiState.Success.REMOVE_CATEGORY
                }.onFailure {
                    println("fail to add category $it")
                    _settingUiState.value = SettingUiState.Error.REMOVE_CATEGORY
                }
        }
    }

    fun removeGoodPoint(pointId: Long) {
        viewModelScope.launch { removeGoodPointUseCase(pointId) }
    }

    fun removeBadPoint(pointId: Long) {
        viewModelScope.launch { removeBadPointUseCase(pointId) }
    }

    fun removeSchedule(scheduleId: Long) {
        viewModelScope.launch { removeScheduleUseCase(scheduleId) }
    }
}

sealed interface SettingUiState {
    object UnInitialized : SettingUiState
    object Loading : SettingUiState
    enum class Error : SettingUiState {
        GET_CATEGORIES,
        ADD_CATEGORY,
        REMOVE_CATEGORY,
        LESS_CATEGORY_INFO
    }

    enum class Success : SettingUiState {
        ADD_CATEGORY,
        REMOVE_CATEGORY
    }
}

enum class MyTab(val title: String) {
    DIARY("기록"),
    GOOD("잘한 점"),
    BAD("못한 점"),
    SCHEDULE("일정"),
    CATEGORY("카테고리 관리")
}
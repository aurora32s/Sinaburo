package com.haman.dearme.ui.screen.diary

import android.net.Uri
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haman.dearme.data.db.entity.toModel
import com.haman.dearme.domain.usecase.*
import com.haman.dearme.ui.model.*
import com.haman.dearme.ui.model.plan.PlanDiaryModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class DiaryViewModel @Inject constructor(
    private val getDiaryUseCase: GetDiaryUseCase,
    private val addGoodPointUseCase: AddGoodPointUseCase,
    private val addBadPointUseCase: AddBadPointUseCase,
    private val removeGoodPointUseCase: RemoveGoodPointUseCase,
    private val removeBadPointUseCase: RemoveBadPointUseCase,
    private val getGalleryUseCase: GetGalleryUseCase,
    private val addDiaryUseCase: AddDiaryUseCase,
    private val removeDiaryUseCase: RemoveDiaryUseCase,
    private val addRateUseCase: AddRateUseCase,
    private val getScheduleByDateUseCase: GetScheduleByDateUseCase,
    private val removeScheduleUseCase: RemoveScheduleUseCase
) : ViewModel() {

    // 완료한 일정
    private val _completedPlans = MutableStateFlow<List<PlanDiaryModel>>(emptyList())
    val completedPlans = _completedPlans.asStateFlow()

    // 일기
    private val _diaryUiState = MutableStateFlow<DiaryUiState>(DiaryUiState.Initialized)
    val diaryUiState = _diaryUiState.asStateFlow()

    private var diaryId: Long? = null
    private val _content = MutableStateFlow("")
    val content = _content.asStateFlow()

    private val _allCount = MutableStateFlow(0)
    val allCount = _allCount.asStateFlow()

    private val _challenges = MutableStateFlow<List<MainChallengeModel>>(emptyList())
    val challenges = _challenges.asStateFlow()

    // 잘한 점
    private val _goods = mutableStateListOf<PointModel>()
    val goods: List<PointModel> = _goods

    // 못한 점
    private val _bads = mutableStateListOf<PointModel>()
    val bads: List<PointModel> = _bads

    // 이미지
    private val _galleryImage = MutableStateFlow<List<Uri>>(emptyList())
    val galleryImage = _galleryImage.asStateFlow()

    private val _rate = MutableStateFlow<RateModel?>(null)
    val rate = _rate.asStateFlow()

    private val _schedules = MutableStateFlow<List<ScheduleModel>>(emptyList())
    val schedules = _schedules.asStateFlow()

    fun removeSchedule(scheduleId: Long) {
        viewModelScope.launch {
            removeScheduleUseCase(scheduleId = scheduleId)
                .onSuccess {
                    _schedules.value = _schedules.value.filter { it.id != scheduleId }
                }
        }
    }

    fun getDiaryInfo(year: Int, month: Int, day: Int) {
        viewModelScope.launch {
            // 완료한 일정 요청
            getDiaryUseCase(year, month, day).onSuccess {
                _completedPlans.value = it.planDiary.map { plan -> plan.toModel() }
                _goods.clear()
                _goods.addAll(it.goods.map { good -> good.toModel() })
                _bads.clear()
                _bads.addAll(it.bads.map { bad -> bad.toModel() })
                diaryId = it.diaryEntity?.id
                _content.value = it.diaryEntity?.content ?: ""
                _allCount.value = it.planCnt
                _rate.value = it.rateEntity?.toModel()
                _challenges.value = it.challenges.map { it.toModel() }

                _diaryUiState.value = DiaryUiState.Initialized
            }
            getGalleryUseCase(year, month, day).onSuccess {
                _galleryImage.value = it
            }
            getScheduleByDateUseCase(LocalDate.of(year, month, day))
                .onSuccess {
                    _schedules.value = it.map { it.toModel() }
                }
        }
    }

    fun addGood(content: String, year: Int, month: Int, day: Int) {
        if (content.isBlank()) return
        viewModelScope.launch {
            val good = PointModel(
                content = content,
                year = year,
                month = month,
                day = day
            )
            addGoodPointUseCase(good).onSuccess { _goods.add(good.copy(id = it)) }
        }
    }

    fun addBad(content: String, year: Int, month: Int, day: Int) {
        if (content.isBlank()) return
        viewModelScope.launch {
            val bad = PointModel(
                content = content,
                year = year,
                month = month,
                day = day
            )
            addBadPointUseCase(bad).onSuccess { _bads.add(bad.copy(id = it)) }
        }
    }

    fun removeGood(pointId: Long) {
        viewModelScope.launch {
            removeGoodPointUseCase(pointId).onSuccess {
                _goods.removeIf { it.id == pointId }
            }
        }
    }

    fun removeBad(pointId: Long) {
        viewModelScope.launch {
            removeBadPointUseCase(pointId).onSuccess {
                _bads.removeIf { it.id == pointId }
            }
        }
    }

    private var timer: Job? = null
    fun changeDiary(year: Int, month: Int, day: Int, newDiary: String) {
        if (newDiary.length >= 250) return
        _content.value = newDiary
        _diaryUiState.value = DiaryUiState.Loading

        timer?.cancel()
        timer = viewModelScope.launch {
            delay(1500)
            if (newDiary.isBlank() && diaryId != null) {
                // 삭제
                removeDiaryUseCase(diaryId!!).onSuccess {
                    diaryId = null
                    _diaryUiState.value = DiaryUiState.Initialized
                }
            } else if (newDiary.isBlank().not()) {
                val diary = DiaryModel(
                    id = diaryId,
                    content = newDiary,
                    year = year,
                    month = month,
                    day = day
                )
                addDiaryUseCase(diary)
                    .onSuccess {
                        diaryId = it
                        _diaryUiState.value = DiaryUiState.Success
                    }
                    .onFailure { println(it.message) }
            }
        }
    }

    fun changeRate(year: Int, month: Int, day: Int, rate: Int) {
        viewModelScope.launch {
            val newRate = RateModel(
                id = _rate.value?.id,
                rate = rate,
                year = year,
                month = month,
                day = day
            )
            addRateUseCase(newRate).onSuccess {
                _rate.value = newRate.copy(id = it)
            }
        }
    }
}

sealed interface DiaryUiState {
    object Initialized : DiaryUiState
    object Loading : DiaryUiState
    object Success : DiaryUiState
}
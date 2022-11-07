package com.haman.dearme.ui.screen.montly

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haman.dearme.data.db.entity.toModel
import com.haman.dearme.domain.usecase.*
import com.haman.dearme.ui.model.*
import com.haman.dearme.ui.model.plan.PlanCountModel
import com.haman.dearme.ui.screen.diary.DiaryUiState
import com.haman.dearme.util.ext.getTime
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.YearMonth
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class MonthlyViewModel @Inject constructor(
    private val getMonthlyPlanUseCase: GetMonthlyPlanUseCase,
    private val getMonthlyPlanByCategoryUseCase: GetMonthlyPlanByCategoryUseCase,
    private val getAllGoodPointByDateUseCase: GetAllGoodPointByDateUseCase,
    private val getAllBadPointByDateUseCase: GetAllBadPointByDateUseCase,
    private val getMonthlyDiaryUseCase: GetMonthlyDiaryUseCase,
    private val addDiaryUseCase: AddDiaryUseCase,
    private val removeDiaryUseCase: RemoveDiaryUseCase,
    private val getRateUseCase: GetRateUseCase,
    private val addRateUseCase: AddRateUseCase,
    private val getMonthlyChallengeUseCase: GetMonthlyChallengeUseCase,
    private val removeScheduleUseCase: RemoveScheduleUseCase,
    private val removeGoodPointUseCase: RemoveGoodPointUseCase,
    private val removeBadPointUseCase: RemoveBadPointUseCase,
    private val getScheduleByMonthUseCase: GetScheduleByMonthUseCase
) : ViewModel() {

    private val _monthlyPlan = MutableStateFlow<List<PlanCountModel>>(emptyList())
    val monthlyPlan =
        _monthlyPlan.asStateFlow()
            .map { it.associateBy { plan -> plan.date } }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(),
                initialValue = emptyMap()
            )

    private val _monthlyPlanByCategory = MutableStateFlow<List<MonthlyPlanByCategoryModel>>(
        emptyList()
    )
    val monthlyPlanByCategory = _monthlyPlanByCategory.asStateFlow()

    private val _goodPoints = MutableStateFlow<List<PointModel>>(emptyList())
    val goodPoints = _goodPoints.asStateFlow()
    private val _badPoints = MutableStateFlow<List<PointModel>>(emptyList())
    val badPoints = _badPoints.asStateFlow()

    private val _diaryUiState = MutableStateFlow<DiaryUiState>(DiaryUiState.Initialized)
    val diaryUiState = _diaryUiState.asStateFlow()

    private var diaryId: Long? = null
    private val _content = MutableStateFlow("")
    val content = _content.asStateFlow()

    private val _rate = MutableStateFlow<RateModel?>(null)
    val rate = _rate.asStateFlow()

    private val _challenges = MutableStateFlow<List<MainChallengeModel>>(emptyList())
    val challenges = _challenges.asStateFlow()

    private val _selectedDate = MutableStateFlow(LocalDate.now())
    val selectedDate = _selectedDate.asStateFlow()
    private val _schedules = MutableStateFlow<List<ScheduleModel>>(emptyList())
    val allSchedules = _schedules.asStateFlow()
    val schedules = _schedules
        .combine(_selectedDate) { schedule, date ->
            val startedAt = LocalDateTime.of(date, LocalTime.of(0, 0))
            val endedAt = LocalDateTime.of(date, LocalTime.of(23, 59))
            schedule.filter { it.startedAt <= endedAt && it.endedAt >= startedAt }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = emptyList()
        )

    fun setSelectedDate(newDate: LocalDate) {
        viewModelScope.launch {
            _selectedDate.value = newDate
        }
    }

    fun removeSchedule(scheduleId: Long) {
        viewModelScope.launch {
            removeScheduleUseCase(scheduleId = scheduleId)
                .onSuccess {
                    _schedules.value = _schedules.value.filter { it.id != scheduleId }
                }
        }
    }

    fun removeGood(pointId: Long) {
        viewModelScope.launch {
            removeGoodPointUseCase(pointId).onSuccess {
                _goodPoints.value = _goodPoints.value.filter { it.id != pointId }
            }
        }
    }

    fun removeBad(pointId: Long) {
        viewModelScope.launch {
            removeBadPointUseCase(pointId).onSuccess {
                _badPoints.value = _badPoints.value.filter { it.id != pointId }
            }
        }
    }

    fun getMonthlyInfo(year: Int, month: Int) {
        viewModelScope.launch {
            getMonthlyPlanUseCase(year, month).onSuccess {
                _monthlyPlan.value = it.map { plan -> plan.toModel() }
            }
            getMonthlyPlanByCategoryUseCase(year, month).onSuccess {
                _monthlyPlanByCategory.value = it.map { plan -> plan.toModel() }
            }
            getAllGoodPointByDateUseCase(year, month).onSuccess {
                _goodPoints.value = it.map { it.toModel() }
            }
            getAllBadPointByDateUseCase(year, month).onSuccess {
                _badPoints.value = it.map { it.toModel() }
            }
            getMonthlyDiaryUseCase(year, month).onSuccess {
                diaryId = it?.id
                _content.value = it?.content ?: ""
            }
            getRateUseCase(year, month, -1).onSuccess {
                _rate.value = it?.toModel()
            }
            getMonthlyChallengeUseCase(year, month).onSuccess {
                _challenges.value = it.map { it.toModel() }
            }
            getScheduleByMonthUseCase(year, month).onSuccess {
                _schedules.value = it.map { it.toModel() }
            }
        }
    }

    private var timer: Job? = null
    fun changeDiary(yearMonth: YearMonth, newDiary: String) {
        if (newDiary.length >= 250) return
        _content.value = newDiary
        _diaryUiState.value = DiaryUiState.Initialized

        timer?.cancel()
        timer = viewModelScope.launch {
            delay(1500)
            _diaryUiState.value = DiaryUiState.Loading
            if (newDiary.isBlank() && diaryId != null) {
                removeDiaryUseCase(diaryId!!).onSuccess {
                    diaryId = null
                    _diaryUiState.value = DiaryUiState.Initialized
                }
            } else if (newDiary.isBlank().not()) {
                val diary = DiaryModel(
                    id = diaryId,
                    content = newDiary,
                    year = yearMonth.year,
                    month = yearMonth.month.value,
                    day = -1
                )
                addDiaryUseCase(diary).onSuccess {
                    diaryId = it
                    _diaryUiState.value = DiaryUiState.Success
                }
            }
        }
    }

    fun changeRate(yearMonth: YearMonth, rate: Int) {
        viewModelScope.launch {
            val newRate = RateModel(
                id = _rate.value?.id,
                rate = rate,
                year = yearMonth.year,
                month = yearMonth.month.value,
                day = -1
            )
            addRateUseCase(newRate).onSuccess {
                _rate.value = newRate.copy(id = it)
            }
        }
    }
}
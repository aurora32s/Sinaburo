package com.haman.dearme.ui.screen.montly.daily

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haman.dearme.data.db.entity.toModel
import com.haman.dearme.domain.usecase.GetAllMonthlyPlanUseCase
import com.haman.dearme.domain.usecase.GetMonthlyPlanUseCase
import com.haman.dearme.ui.model.plan.PlanCountModel
import com.haman.dearme.ui.model.plan.PlanDiaryModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class DailyViewModel @Inject constructor(
    private val getMonthlyPlanUseCase: GetMonthlyPlanUseCase,
    private val getAllMonthlyPlanUseCase: GetAllMonthlyPlanUseCase
) : ViewModel() {

    private val _monthlyPlanCount = MutableStateFlow<List<PlanCountModel>>(emptyList())
    val monthlyPlanCount = _monthlyPlanCount.asStateFlow()
        .map { it.associateBy { plan -> plan.date } }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = emptyMap()
        )
    private val _monthlyPlan = MutableStateFlow<List<PlanDiaryModel>>(emptyList())
    val monthlyPlan = _monthlyPlan.asStateFlow()
        .map { it.groupBy { plan -> LocalDate.of(plan.year, plan.month, plan.day) } }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = emptyMap()
        )

    fun init(year: Int, month: Int) {
        viewModelScope.launch {
            getMonthlyPlanUseCase(year, month).onSuccess {
                _monthlyPlanCount.value = it.map { plan -> plan.toModel() }
            }
            getAllMonthlyPlanUseCase(year, month).onSuccess {
                _monthlyPlan.value = it.map { plan -> plan.toModel() }
            }
        }
    }
}
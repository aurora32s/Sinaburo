package com.haman.dearme.ui.screen.montly.category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haman.dearme.data.db.entity.toModel
import com.haman.dearme.domain.usecase.GetAllMonthlyPlanByCategoryUseCase
import com.haman.dearme.domain.usecase.GetMonthlyPlanCountByCategoryUseCase
import com.haman.dearme.ui.model.plan.PlanCountModel
import com.haman.dearme.ui.model.plan.PlanDiaryModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val getMonthlyPlanCountByCategoryUseCase: GetMonthlyPlanCountByCategoryUseCase,
    private val getAllMonthlyPlanByCategoryUseCase: GetAllMonthlyPlanByCategoryUseCase
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

    fun init(year: Int, month: Int, category: Long) {
        viewModelScope.launch {
            getMonthlyPlanCountByCategoryUseCase(year, month, category).onSuccess {
                _monthlyPlanCount.value = it.map { it.toModel() }
            }
            getAllMonthlyPlanByCategoryUseCase(year, month, category).onSuccess {
                _monthlyPlan.value = it.map { it.toModel() }
            }
        }
    }
}
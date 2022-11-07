package com.haman.dearme.ui.screen.history

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haman.dearme.data.db.entity.toModel
import com.haman.dearme.domain.usecase.*
import com.haman.dearme.ui.model.MainChallengeModel
import com.haman.dearme.ui.model.plan.PlanMainModel
import com.haman.dearme.ui.model.ScheduleModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val getAllPlanUseCase: GetAllPlanUseCase,
    private val removePlanUseCase: RemovePlanUseCase,
    private val getMainChallengeUseCase: GetMainChallengeUseCase,
    private val getCompletedChallengeByDateUseCase: GetCompletedChallengeByDateUseCase,
    private val addCompletedChallengeUseCase: AddCompletedChallengeUseCase,
    private val removeCompletedChallengeUseCase: RemoveCompletedChallengeUseCase,
    private val getScheduleByDateUseCase: GetScheduleByDateUseCase,
    private val removeScheduleUseCase: RemoveScheduleUseCase
) : ViewModel() {

    val plans = mutableStateListOf<PlanMainModel>()

    private val _challenges = MutableStateFlow<List<MainChallengeModel>>(emptyList())
    val challenge = _challenges.asStateFlow()

    val completedChallenge = mutableStateListOf<Long>()

    private val _schedules = MutableStateFlow<List<ScheduleModel>>(emptyList())
    val schedules = _schedules.asStateFlow()

    fun getPlans(year: Int, month: Int, day: Int) {
        viewModelScope.launch {
            getAllPlanUseCase(year, month, day)
                .onSuccess {
                    plans.clear()
                    plans.addAll(it.map { plan -> plan.toModel() })
                }
            getMainChallengeUseCase(year, month, day)
                .onSuccess {
                    _challenges.value = it.map { challenge -> challenge.toModel() }
                }
            getCompletedChallengeByDateUseCase(year, month, day)
                .onSuccess {
                    completedChallenge.clear()
                    completedChallenge.addAll(it.map { it.challengeId })
                }
            getScheduleByDateUseCase(LocalDate.of(year, month, day))
                .onSuccess {
                    _schedules.value = it.map { it.toModel() }
                }
        }
    }

    fun removePlans(planId: Long) {
        viewModelScope.launch {
            removePlanUseCase(planId = planId)
                .onSuccess {
                    plans.removeIf { it.id == planId }
                }
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

    fun completedChallenge(challengeId: Long, year: Int, month: Int, day: Int) {
        val completed = challengeId in completedChallenge
        viewModelScope.launch {
            if (completed) {
                removeCompletedChallengeUseCase(
                    challengeId = challengeId,
                    year = year,
                    month = month,
                    day = day
                ).onSuccess {
                    completedChallenge.remove(challengeId)
                }
            } else {
                addCompletedChallengeUseCase(
                    challengeId = challengeId,
                    year = year,
                    month = month,
                    day = day
                ).onSuccess {
                    completedChallenge.add(challengeId)
                }
            }
        }
    }
}
package com.haman.dearme.ui.screen.challenge

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haman.dearme.data.db.entity.toModel
import com.haman.dearme.domain.usecase.GetAllChallengeUseCase
import com.haman.dearme.ui.model.ChallengeState
import com.haman.dearme.ui.model.MainChallengeModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChallengeViewModel @Inject constructor(
    private val getAllChallengeUseCase: GetAllChallengeUseCase
) : ViewModel() {

    private val _sorted = MutableStateFlow(Sort.BASE)
    val sorted = _sorted.asStateFlow()
    private val _filtered = MutableStateFlow(Filter.BASE)
    val filtered = _filtered.asStateFlow()

    fun setCondition(condition: Condition) {
        when (condition) {
            is Sort -> setSorted(condition)
            is Filter -> setFiltered(condition)
        }
    }

    fun setSorted(sort: Sort) {
        _sorted.value = sort
    }

    fun setFiltered(filter: Filter) {
        _filtered.value = filter
    }

    private val _challenges = getAllChallengeUseCase()
    val challenges = _challenges
        .map { it.map { it.toModel() } }
        .combine(_sorted) { challenge, sort ->
            when (sort) {
                Sort.BASE -> challenge
                Sort.SUCCESS -> challenge.sortedBy { -it.count }
                Sort.CATEGORY -> challenge.sortedBy { it.categoryId }
            }
        }
        .combine(_filtered) { challenge, filter ->
            when (filter) {
                Filter.BASE -> challenge
                Filter.COMPLETE -> challenge.filter { it.state == ChallengeState.SUCCESS || it.state == ChallengeState.FAIL }
                Filter.SUCCESS -> challenge.filter { it.state == ChallengeState.SUCCESS }
                Filter.FAIL -> challenge.filter { it.state == ChallengeState.FAIL }
                Filter.DOING -> challenge.filter { it.state == ChallengeState.DOING }
                Filter.TODO -> challenge.filter { it.state == ChallengeState.TODO }
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = emptyList()
        )
}

interface Condition {
    val title: String
}

enum class Sort(override val title: String) : Condition {
    BASE("기본"),
    SUCCESS("성공률순"),
    CATEGORY("카테고리별")
}

enum class Filter(override val title: String) : Condition {
    BASE("전체"),
    COMPLETE("완료"),
    SUCCESS("성공"),
    FAIL("실패"),
    DOING("진행중"),
    TODO("진행예정")
}
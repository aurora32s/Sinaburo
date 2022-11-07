package com.haman.dearme.ui.screen.montly.schedule

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haman.dearme.data.db.entity.toModel
import com.haman.dearme.domain.usecase.AddScheduleUseCase
import com.haman.dearme.domain.usecase.GetScheduleByIdUseCase
import com.haman.dearme.domain.usecase.RemoveScheduleUseCase
import com.haman.dearme.ui.model.ScheduleModel
import com.haman.dearme.ui.model.ScheduleState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class ScheduleViewModel @Inject constructor(
    private val addScheduleUseCase: AddScheduleUseCase,
    private val getScheduleByIdUseCase: GetScheduleByIdUseCase,
    private val removeScheduleUseCase: RemoveScheduleUseCase
) : ViewModel() {

    private val _scheduleUiState = MutableStateFlow<ScheduleUiState>(ScheduleUiState.UnInitialized)
    val scheduleUiState = _scheduleUiState.asStateFlow()

    var scheduleId: Long? = null
        private set

    private val _isModifyMode = MutableStateFlow(true)
    val isModifyMode = _isModifyMode.asStateFlow()

    fun onModifyMode() {
        _isModifyMode.value = true
    }

    private val _startedAt = MutableStateFlow<LocalDateTime>(LocalDateTime.now())
    val startedAt = _startedAt.asStateFlow()

    fun changeStartedAtDate(date: LocalDate) {
        _startedAt.value = LocalDateTime.of(date, _startedAt.value.toLocalTime())
    }

    fun changeStartedAtTime(time: LocalTime) {
        _startedAt.value = LocalDateTime.of(_startedAt.value.toLocalDate(), time)
    }

    private val _endedAt = MutableStateFlow<LocalDateTime>(LocalDateTime.now())
    val endedAt = _endedAt.asStateFlow()

    fun changeEndedAtDate(date: LocalDate) {
        _endedAt.value = LocalDateTime.of(date, _endedAt.value.toLocalTime())
    }

    fun changeEndedAtTime(time: LocalTime) {
        _endedAt.value = LocalDateTime.of(_endedAt.value.toLocalDate(), time)
    }

    /**
     * schedule id에 해당하는 schedule 정보 요청
     */
    fun init(scheduleId: Long?, date: LocalDate?) {
        date?.let {
            _startedAt.value = LocalDateTime.of(it, LocalTime.now())
            _endedAt.value = LocalDateTime.of(it, LocalTime.now())
        }
        scheduleId ?: return
        getSchedule(scheduleId)
    }

    private fun getSchedule(id: Long) {
        viewModelScope.launch {
            getScheduleByIdUseCase(scheduleId = id)
                .onSuccess {
                    it.toModel().run {
                        _title.value = this.title
                        _startedAt.value = this.startedAt
                        _endedAt.value = this.endedAt
                        _location.value = this.location
                        _content.value = this.content
                        _people.value = this.people
                        _isImportant.value = this.important
                    }
                    scheduleId = id
                    _isModifyMode.value = false
                }
        }
    }

    fun save() {
        if (_title.value.isBlank()) return
        viewModelScope.launch {
            val newSchedule = ScheduleModel(
                id = scheduleId,
                title = _title.value,
                content = _content.value,
                location = _location.value,
                people = _people.value,
                startedAt = _startedAt.value,
                endedAt = _endedAt.value,
                important = _isImportant.value,
                state = ScheduleState.TODO
            )
            addScheduleUseCase(newSchedule)
                .onSuccess {
                    scheduleId = it
                    toggleModifyMode()
                }
                .onFailure {
                    println(it.message)
                }
        }
    }

    fun toggleModifyMode() {
        if (_isModifyMode.value.not()) _isModifyMode.value = true
        else {
            scheduleId?.let { getSchedule(it) }
        }
    }

    private val _title = MutableStateFlow("")
    val title = _title.asStateFlow()

    fun changeTitle(newTitle: String) {
        _title.value = newTitle
    }

    private val _content = MutableStateFlow("")
    val content = _content.asStateFlow()

    fun changeContent(newContent: String) {
        _content.value = newContent
    }

    private val _location = MutableStateFlow("")
    val location = _location.asStateFlow()

    fun changeLocation(newLocation: String) {
        _location.value = newLocation
    }

    private val _people = MutableStateFlow("")
    val people = _people.asStateFlow()

    fun changePeople(newPeople: String) {
        _people.value = newPeople
    }

    private val _isImportant = MutableStateFlow(false)
    val isImportant = _isImportant.asStateFlow()

    fun changeIsImportant() {
        _isImportant.value = _isImportant.value.not()
    }

    fun removeSchedule() {
        scheduleId?.let {
            viewModelScope.launch {
                _scheduleUiState.value = ScheduleUiState.Loading
                removeScheduleUseCase(it).onSuccess {
                    _scheduleUiState.value = ScheduleUiState.Success.REMOVE_SCHEDULE
                }
            }
        }
    }
}

sealed interface ScheduleUiState {
    object UnInitialized : ScheduleUiState
    object Loading : ScheduleUiState
    enum class Success : ScheduleUiState {
        REMOVE_SCHEDULE
    }
}
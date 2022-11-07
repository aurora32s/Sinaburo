package com.haman.dearme.ui

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {
    private val _year = MutableStateFlow(0)
    val year = _year.asStateFlow()

    private fun setYear(newYear: Int) {
        _year.value = newYear
    }

    private val _month = MutableStateFlow(0)
    val month = _month.asStateFlow()

    private fun setMonth(newMonth: Int) {
        _month.value = newMonth
    }

    private val _day = MutableStateFlow(0)
    val day = _day.asStateFlow()

    private fun setDay(newDate: Int) {
        _day.value = newDate
    }

    init {
        val current = LocalDate.now()

        _year.value = current.year
        _month.value = current.month.value
        _day.value = current.dayOfMonth
    }

    fun setDate(year: Int, month: Int, day: Int) {
        setYear(year)
        setMonth(month)
        setDay(day)
    }
}
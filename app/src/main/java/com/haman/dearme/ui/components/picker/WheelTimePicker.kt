package com.haman.dearme.ui.components.picker

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.haman.dearme.ui.theme.ColorPalette
import java.time.LocalTime

@Composable
fun WheelTimePicker(
    modifier: Modifier = Modifier,
    startTime: LocalTime = LocalTime.now(),
    disablePastTime: Boolean = false,
    size: DpSize = DpSize(128.dp, 128.dp),
    textStyle: TextStyle = MaterialTheme.typography.subtitle1,
    textColor: Color = ColorPalette.DARK_BACKGROUND,
    selectorEnabled: Boolean = true,
    selectorShape: Shape = RoundedCornerShape(16.dp),
    selectorColor: Color = ColorPalette.SECOND,
    selectorBorder: BorderStroke? = BorderStroke(1.dp, ColorPalette.SECOND),
    onScrollFinished: (snappedTime: LocalTime) -> Unit = {}
) {
    val hourTexts: List<String> = (0..11).map { it.toString().padStart(2, '0') }
    val selectedHour = remember { mutableStateOf(startTime.hour) }

    val minuteTexts: List<String> = (0..59).map { it.toString().padStart(2, '0') }
    val selectedMinute = remember { mutableStateOf(startTime.minute) }

    val amPm = remember { mutableStateOf(0) }

    LaunchedEffect(key1 = startTime) {
        selectedHour.value = startTime.hour
        selectedMinute.value = startTime.minute
        amPm.value = if (startTime.hour < 13) 0 else 1
    }

    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        if (selectorEnabled) {
            Surface(
                modifier = Modifier.fillMaxWidth().height(size.height / 3),
                shape = selectorShape,
                color = selectorColor,
                border = selectorBorder
            ) {}
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            WheelTextPicker(
                size = DpSize(size.width / 2, size.height),
                texts = listOf("오전", "오후"),
                textColor = textColor,
                startIndex = amPm.value,
                onScrollFinished = {
                    if (amPm.value != it) {
                        onScrollFinished(
                            LocalTime.of(
                                selectedHour.value + 12 * it,
                                selectedMinute.value
                            )
                        )
                    }
                    it
                },
                selectorEnabled = false
            )
            WheelTextPicker(
                size = DpSize(size.width / 2, size.height),
                texts = hourTexts,
                textStyle = textStyle,
                textColor = textColor,
                startIndex = selectedHour.value,
                selectorEnabled = false,
                onScrollFinished = { selectedIndex ->
                    try {
                        val selectedTime = LocalTime.of(selectedIndex, selectedMinute.value)
                        val isTimeBefore = isTimeBefore(selectedTime, startTime)

                        if (disablePastTime) {
                            if (!isTimeBefore) {
                                selectedHour.value = selectedIndex
                            }
                        } else {
                            selectedHour.value = selectedIndex
                        }

                        onScrollFinished(
                            LocalTime.of(
                                selectedHour.value + 12 * amPm.value,
                                selectedMinute.value
                            )
                        )
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

                    return@WheelTextPicker selectedHour.value
                }
            )
            Text(
                text = ":",
                style = textStyle,
                color = textColor
            )
            WheelTextPicker(
                size = DpSize(size.width / 2, size.height),
                texts = minuteTexts,
                textStyle = textStyle,
                textColor = textColor,
                startIndex = startTime.minute,
                selectorEnabled = false,
                onScrollFinished = { selectedIndex ->
                    try {
                        val selectedTime = LocalTime.of(selectedHour.value, selectedIndex)
                        val isTimeBefore = isTimeBefore(selectedTime, startTime)

                        if (disablePastTime) {
                            if (!isTimeBefore) {
                                selectedMinute.value = selectedIndex
                            }
                        } else {
                            selectedMinute.value = selectedIndex
                        }

                        onScrollFinished(
                            LocalTime.of(
                                selectedHour.value + 12 * amPm.value,
                                selectedMinute.value
                            )
                        )
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

                    return@WheelTextPicker selectedMinute.value
                }
            )
        }
//        Box(
//            modifier = Modifier.size(size),
//            contentAlignment = Alignment.Center
//        ) {
//            Text(
//                text = ":",
//                style = textStyle,
//                color = textColor
//            )
//        }
    }
}

private fun isTimeBefore(time: LocalTime, currentTime: LocalTime): Boolean {
    return time.isBefore(currentTime)
}
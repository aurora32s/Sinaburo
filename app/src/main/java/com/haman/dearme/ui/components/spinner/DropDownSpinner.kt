package com.haman.dearme.ui.components.spinner

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import com.haman.dearme.ui.components.image.IconImage
import com.haman.dearme.ui.components.text.Sub1
import com.haman.dearme.ui.model.CategoryModel
import com.haman.dearme.ui.theme.ColorPalette
import com.haman.dearme.R
import com.haman.dearme.ui.components.chip.RectangleChip

@Composable
fun CategorySpinner(
    isModifyMode: Boolean = true,
    options: List<CategoryModel>,
    selectedOptionId: Long,
    onOptionSelected: (Long) -> Unit,
    onAddOption: () -> Unit
) {
    val expanded = remember { mutableStateOf(false) }
    val selectedOption = options.find { it.id == selectedOptionId }
    val dropDownWidth = remember { mutableStateOf(Size.Zero) }
    val rotateDegree = remember { mutableStateOf(0f) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .wrapContentSize(Alignment.TopEnd)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    if (isModifyMode) {
                        rotateDegree.value = 180f
                        expanded.value = true
                    }
                }
                .onGloballyPositioned { coordinates ->
                    // This value is used to assign to
                    // the DropDown the same width
                    dropDownWidth.value = coordinates.size.toSize()
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (selectedOption != null) {
                RectangleChip(
                    text = selectedOption.name,
                    color = Color(selectedOption.color)
                )
                Spacer(modifier = Modifier.width(4.dp))
            }
            Sub1(
                text = selectedOption?.name ?: "선택해주세요.",
                color = if (selectedOption == null) ColorPalette.GREY
                else if (isSystemInDarkTheme()) ColorPalette.White
                else ColorPalette.DARK_BACKGROUND
            )
            Spacer(modifier = Modifier.weight(1f))
            if (isModifyMode) {
                Spacer(modifier = Modifier.width(4.dp))
                IconImage(
                    icon = R.drawable.arrow_down,
                    color = if (isSystemInDarkTheme()) ColorPalette.White else ColorPalette.PRIMARY,
                    modifier = Modifier
                        .size(16.dp)
                        .rotate(rotateDegree.value),
                )
            }
        }

        MaterialTheme(shapes = MaterialTheme.shapes.copy(medium = RoundedCornerShape(14.dp))) {
            DropdownMenu(
                expanded = expanded.value,
                onDismissRequest = {
                    rotateDegree.value = 0f
                    expanded.value = false
                },
                offset = DpOffset(x = 0.dp, y = 8.dp),
                modifier = Modifier
                    .width(with(LocalDensity.current) { dropDownWidth.value.width.toDp() })
                    .border(
                        width = 1.dp,
                        color = if (isSystemInDarkTheme()) ColorPalette.DARK_GREY else ColorPalette.LIGHT_GREY,
                        shape = RoundedCornerShape(14.dp)
                    )
            ) {
                options.forEach {
                    DropdownMenuItem(
                        onClick = {
                            rotateDegree.value = 0f
                            onOptionSelected(it.id!!)
                            expanded.value = false
                        },
                        modifier = Modifier
                            .height(36.dp)
                            .background(Color.Transparent),
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Sub1(text = it.name)
                            RectangleChip(
                                text = it.name,
                                color = Color(it.color),
                                modifier = Modifier.widthIn(min = 10.dp)
                            )
                        }
                    }
                }
                DropdownMenuItem(onClick = {
                    rotateDegree.value = 0f
                    expanded.value = false
                    onAddOption()
                }) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Sub1(text = "추가하기 >")
                    }
                }
            }
        }
    }
}
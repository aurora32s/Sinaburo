package com.haman.dearme.ui.components.input

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.haman.dearme.ui.components.text.Sub1
import com.haman.dearme.ui.screen.diary.DiaryUiState
import com.haman.dearme.ui.theme.ColorPalette

@Composable
fun SavableInput(
    content: String,
    onChangeContent: (String) -> Unit,
    diaryUiState: DiaryUiState
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(170.dp)
            .clip(RoundedCornerShape(topStart = 10.dp, bottomEnd = 10.dp))
            .border(
                color = if (isSystemInDarkTheme() ) ColorPalette.DARK_GREY else ColorPalette.LIGHT_GREY,
                shape = RoundedCornerShape(
                    topStart = 10.dp,
                    bottomEnd = 10.dp
                ),
                width = 1.dp
            )
            .background(
                if (isSystemInDarkTheme()) ColorPalette.Black
                else ColorPalette.White
            )
            .padding(12.dp)
    ) {
        BaseInput(
            value = content,
            enabled = true,
            onValueChange = onChangeContent,
            number = false,
            modifier = Modifier.weight(1f),
            placeholder = "오늘 하루를 기록해보세요."
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            Sub1(
                text = when (diaryUiState) {
                    DiaryUiState.Initialized -> ""
                    DiaryUiState.Loading -> "입력중..."
                    DiaryUiState.Success -> "저장완료"
                }
            )
            Spacer(modifier = Modifier.width(4.dp))
            Sub1(text = "(${content.length} / 250)")
        }
    }
}
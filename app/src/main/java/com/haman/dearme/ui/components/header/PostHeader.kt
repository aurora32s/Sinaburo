package com.haman.dearme.ui.components.header

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.haman.dearme.R
import com.haman.dearme.ui.components.image.IconImage
import com.haman.dearme.ui.components.text.Body1
import com.haman.dearme.ui.components.text.Sub1
import com.haman.dearme.ui.components.text.Sub2
import com.haman.dearme.ui.theme.ColorPalette

@Composable
fun PostAppBar(
    isModifyMode: Boolean,
    title: String,
    onBackPressed: () -> Unit,
    onSave: () -> Unit,
    onModifyMode: () -> Unit,
    onCancel: () -> Unit,
    onDelete: () -> Unit
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconImage(
                icon = R.drawable.side_arrow,
                modifier = Modifier
                    .size(16.dp)
                    .clickable { onBackPressed() },
                color = if (isSystemInDarkTheme()) ColorPalette.White else ColorPalette.DARK_BACKGROUND
            )
            Spacer(modifier = Modifier.width(12.dp))
            Body1(text = title)
            Spacer(modifier = Modifier.weight(1f))
            if (isModifyMode) { // 수정 모드일 때
                Sub1(text = "저장", modifier = Modifier.clickable { onSave() })
                Spacer(modifier = Modifier.width(16.dp))
                Sub1(text = "취소", modifier = Modifier.clickable { onCancel() })
            } else {
                Sub1(text = "삭제", modifier = Modifier.clickable { onDelete() })
                Spacer(modifier = Modifier.width(16.dp))
                Sub1(text = "수정하기", modifier = Modifier.clickable { onModifyMode() })
            }
        }
        Divider()
        Sub2(
            text = "*는 필수 입력 사항입니다.",
            modifier = Modifier
                .align(Alignment.End)
                .padding(vertical = 4.dp, horizontal = 4.dp)
        )
    }
}
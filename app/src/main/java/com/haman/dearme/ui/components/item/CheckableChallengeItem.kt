package com.haman.dearme.ui.components.item

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.haman.dearme.ui.components.chip.RectangleChip
import com.haman.dearme.ui.components.divider.BaseDivider
import com.haman.dearme.ui.components.text.Body2
import com.haman.dearme.ui.components.text.Sub1
import com.haman.dearme.ui.components.text.Sub2
import com.haman.dearme.ui.model.MainChallengeModel
import com.haman.dearme.ui.theme.ColorPalette
import com.haman.dearme.util.ext.fullFormat
import com.skydoves.landscapist.glide.GlideImage
import java.time.LocalDate
import java.time.temporal.ChronoUnit

@Composable
fun CheckableChallengeItem(
    challenge: MainChallengeModel,
    completed: Boolean,
    onClickChallengeItem: (Long) -> Unit,
    onCompleteChallenge: (Long) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp)
            .clip(RoundedCornerShape(topStart = 10.dp, bottomStart = 10.dp))
            .background(
                if (isSystemInDarkTheme()) ColorPalette.Black
                else ColorPalette.White
            )
            .border(
                width = (1.5).dp,
                color = if (isSystemInDarkTheme()) ColorPalette.White.copy(alpha = 0.2f)
                else ColorPalette.GREY,
                shape = RoundedCornerShape(
                    topStart = 10.dp,
                    bottomStart = 10.dp
                )
            )
            .padding(top = 8.dp, bottom = 8.dp, end = 7.dp)
            .clickable { onClickChallengeItem(challenge.id) }
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
        ) {
            RectangleChip(
                modifier = Modifier.align(Alignment.TopEnd),
                text = challenge.categoryName,
                color = challenge.categoryColor
            )
            Row {
                Checkbox(
                    modifier = Modifier.align(Alignment.CenterVertically),
                    checked = completed,
                    onCheckedChange = { onCompleteChallenge(challenge.id) },
                    colors = CheckboxDefaults.colors(
                        checkedColor = challenge.categoryColor
                    )
                )
                if (challenge.image.isNotBlank()) {
                    GlideImage(
                        imageModel = challenge.image,
                        modifier = Modifier
                            .size(64.dp)
                            .clip(RoundedCornerShape(10.dp))
                    )
                }
                Sub1(text = challenge.name, modifier = Modifier.padding(start = 8.dp))
            }
        }
        Sub2(
            modifier = Modifier.align(Alignment.End),
            text = "${challenge.startedAt.fullFormat()} ~ ${challenge.endedAt.fullFormat()}",
            color = ColorPalette.GREY
        )
    }
}

@Composable
fun ChallengeItem(
    outerModifier: Modifier = Modifier,
    innerModifier: Modifier = Modifier,
    challenge: MainChallengeModel,
    toPostScreen: (Long) -> Unit
) {
    val now = LocalDate.now()
    val totalDate = ChronoUnit.DAYS.between(challenge.startedAt, challenge.endedAt) + 1
    val fromDate = ChronoUnit.DAYS.between(challenge.startedAt, now) + 1
    val successCnt = if (now < challenge.startedAt) 0 else challenge.count
    val failCnt =
        if (now < challenge.startedAt) 0 else if (now > challenge.endedAt) totalDate - successCnt else fromDate - successCnt
    val extraCnt =
        if (now < challenge.startedAt) totalDate else if (now > challenge.endedAt) 0 else totalDate - fromDate

    Column(
        modifier = outerModifier
            .background(
                if (isSystemInDarkTheme()) ColorPalette.DARK_BACKGROUND
                else ColorPalette.White
            )
            .border(
                width = 1.dp,
                color = if (isSystemInDarkTheme() ) ColorPalette.DARK_GREY else ColorPalette.LIGHT_GREY,
                shape = RoundedCornerShape(10.dp)
            )
            .clickable { toPostScreen(challenge.id) }
    ) {
        Box(
            modifier = innerModifier
                .fillMaxWidth()
        ) {
            if (challenge.image.isNotBlank()) {
                GlideImage(
                    imageModel = challenge.image,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(10.dp))
                )
            }
            Column(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(12.dp),
                horizontalAlignment = Alignment.End
            ) {
                RectangleChip(
                    text = challenge.state.title,
                    color = challenge.categoryColor
                )
                Spacer(modifier = Modifier.height(2.dp))
                RectangleChip(
                    text = challenge.categoryName,
                    color = challenge.categoryColor
                )
            }
            BaseDivider(modifier = Modifier.align(Alignment.BottomCenter))
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Sub1(text = challenge.name)
            Sub2(
                text = "${challenge.startedAt.fullFormat()} ~ ${challenge.endedAt.fullFormat()}",
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                Box(
                    modifier = Modifier
                        .size(16.dp)
                        .background(ColorPalette.ACCENT)
                )
                Spacer(modifier = Modifier.width(2.dp))
                Sub1(text = "${successCnt}개")
                Spacer(modifier = Modifier.width(2.dp))
                Box(
                    modifier = Modifier
                        .size(16.dp)
                        .background(ColorPalette.SECOND)
                )
                Spacer(modifier = Modifier.width(2.dp))
                Sub1(text = "${failCnt}개")
                Spacer(modifier = Modifier.width(2.dp))
                Box(
                    modifier = Modifier
                        .size(16.dp)
                        .background(ColorPalette.GREY)
                )
                Spacer(modifier = Modifier.width(2.dp))
                Sub1(text = "${extraCnt}개")
            }
            Canvas(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(20.dp)
            ) {
                val canvasWidth = size.width
                val canvasHeight = size.height

                val width = canvasWidth / totalDate
                val minHeight = canvasHeight - 40
                val maxHeight = canvasHeight

                var x = 0f
                val successPlanPath = Path().apply {
                    moveTo(x + 10, minHeight)
                    lineTo(successCnt * width, minHeight)
                    lineTo(successCnt * width - 25, maxHeight)
                    lineTo(x - 15, maxHeight)
                    close()
                }
                x += successCnt * width
                val failPlanPath = Path().apply {
                    moveTo(x + 10, minHeight)
                    lineTo(x + failCnt * width, minHeight)
                    lineTo(x + failCnt * width - 25, maxHeight)
                    lineTo(x - 15, maxHeight)
                    close()
                }
                x += failCnt * width
                val extraPlanPath = Path().apply {
                    moveTo(x + 10, minHeight)
                    lineTo(x + extraCnt * width, minHeight)
                    lineTo(x + extraCnt * width - 25, maxHeight)
                    lineTo(x - 15, maxHeight)
                    close()
                }
                drawIntoCanvas { canvas ->
                    if (successCnt > 0) {
                        canvas.drawOutline(
                            outline = Outline.Generic(successPlanPath),
                            paint = Paint().apply {
                                color = ColorPalette.ACCENT
                                pathEffect = PathEffect.cornerPathEffect(15f)
                            }
                        )
                    }
                    if (failCnt > 0) {
                        canvas.drawOutline(
                            outline = Outline.Generic(failPlanPath),
                            paint = Paint().apply {
                                color = ColorPalette.SECOND
                                pathEffect = PathEffect.cornerPathEffect(15f)
                            }
                        )
                    }
                    if (extraCnt > 0) {
                        canvas.drawOutline(
                            outline = Outline.Generic(extraPlanPath),
                            paint = Paint().apply {
                                color = ColorPalette.GREY
                                pathEffect = PathEffect.cornerPathEffect(15f)
                            }
                        )
                    }
                }
            }
        }
    }
}
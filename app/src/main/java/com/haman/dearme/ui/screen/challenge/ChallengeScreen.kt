package com.haman.dearme.ui.screen.challenge

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.hilt.navigation.compose.hiltViewModel
import com.haman.dearme.R
import com.haman.dearme.ui.components.chip.RectangleChip
import com.haman.dearme.ui.components.common.footer
import com.haman.dearme.ui.components.container.CustomBottomSheet
import com.haman.dearme.ui.components.divider.BaseDivider
import com.haman.dearme.ui.components.header.AddAppBar
import com.haman.dearme.ui.components.image.IconImage
import com.haman.dearme.ui.components.item.ChallengeItem
import com.haman.dearme.ui.components.item.emptyDivider
import com.haman.dearme.ui.components.item.itemWithBaseHeader
import com.haman.dearme.ui.components.item.paddingItems
import com.haman.dearme.ui.components.text.Body2
import com.haman.dearme.ui.components.text.Sub1
import com.haman.dearme.ui.components.text.Sub2
import com.haman.dearme.ui.model.MainChallengeModel
import com.haman.dearme.ui.model.item.ItemWithCountHeader
import com.haman.dearme.ui.model.state
import com.haman.dearme.ui.theme.ColorPalette
import com.haman.dearme.util.ext.formatToDate
import com.haman.dearme.util.ext.fullFormat
import com.skydoves.landscapist.glide.GlideImage
import kotlinx.coroutines.launch
import java.time.Duration
import java.time.LocalDate
import java.time.Period
import java.time.temporal.ChronoUnit
import java.util.concurrent.TimeUnit
import kotlin.time.Duration.Companion.days

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ChallengeScreen(
    toPostScreen: (Long?) -> Unit,
    viewModel: ChallengeViewModel = hiltViewModel()
) {
    val bottomSheetState =
        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val coroutineScope = rememberCoroutineScope()

    val challenges = viewModel.challenges.collectAsState()

    val sorted = viewModel.sorted.collectAsState()
    val filtered = viewModel.filtered.collectAsState()

    val conditions = remember { mutableStateOf<Array<Condition>>(emptyArray()) }

    CustomBottomSheet(
        sheetState = bottomSheetState,
        sheetContent = {
            Column(modifier = Modifier.padding(12.dp)) {
                Body2(text = "선택해주세요!", modifier = Modifier.padding(vertical = 4.dp))
                LazyColumn {
                    items(items = conditions.value) { condition ->
                        Sub1(
                            text = condition.title,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    coroutineScope.launch {
                                        viewModel.setCondition(condition)
                                        bottomSheetState.hide()
                                    }
                                }
                                .padding(12.dp)
                        )
                    }
                }
            }
        }
    ) {
        Scaffold(
            topBar = {
                AddAppBar(onClickAddBtn = { toPostScreen(null) })
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        if (isSystemInDarkTheme()) ColorPalette.DARK_SPACER
                        else ColorPalette.SPACER
                    )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            if (isSystemInDarkTheme()) ColorPalette.DARK_BACKGROUND
                            else ColorPalette.White
                        )
                        .padding(horizontal = 12.dp, vertical = 4.dp)
                ) {
                    ConditionChip(
                        title = sorted.value.title,
                        onClickChip = {
                            coroutineScope.launch {
                                conditions.value = Sort.values().map { it }.toTypedArray()
                                bottomSheetState.show()
                            }
                        }
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    ConditionChip(
                        title = filtered.value.title,
                        onClickChip = {
                            coroutineScope.launch {
                                conditions.value = Filter.values().map { it }.toTypedArray()
                                bottomSheetState.show()
                            }
                        }
                    )
                }
                LazyColumn(
                    modifier = Modifier
                        .background(
                            if (isSystemInDarkTheme()) ColorPalette.DARK_BACKGROUND
                            else ColorPalette.White
                        )
                ) {
                    ItemWithCountHeader(
                        title = "챌린지",
                        emptyMessage = "조건에 맞는 챌린지가 없습니다.",
                        isEmpty = challenges.value.isEmpty(),
                        size = challenges.value.size,
                        header = {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp, horizontal = 12.dp),
                                horizontalArrangement = Arrangement.End
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(16.dp)
                                        .background(ColorPalette.ACCENT)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Sub1(text = "성공")
                                Spacer(modifier = Modifier.width(4.dp))
                                Box(
                                    modifier = Modifier
                                        .size(16.dp)
                                        .background(ColorPalette.SECOND)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Sub1(text = "실패")
                                Spacer(modifier = Modifier.width(4.dp))
                                Box(
                                    modifier = Modifier
                                        .size(16.dp)
                                        .background(ColorPalette.GREY)
                                )
                                Spacer(modifier = Modifier.width(2.dp))
                                Sub1(text = "남은 기간")
                            }
                        }
                    ) {
                        paddingItems(items = challenges.value, key = { item -> item.id }) {
                            ChallengeItem(
                                outerModifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 2.dp),
                                innerModifier = Modifier.height(200.dp),
                                challenge = it,
                                toPostScreen = toPostScreen
                            )
                        }
                    }
                    emptyDivider(64f)
                    footer()
                }
            }
        }
    }
}

@Composable
fun ConditionChip(
    title: String,
    onClickChip: () -> Unit
) {
    Sub1(
        text = title,
        modifier = Modifier
            .widthIn(min = 80.dp)
            .border(
                width = 1.dp,
                color = ColorPalette.GREY,
                shape = RoundedCornerShape(999.dp)
            )
            .padding(horizontal = 16.dp, vertical = 12.dp)
            .clickable { onClickChip() }
    )
}


@file:Suppress("OPT_IN_USAGE_FUTURE_ERROR")

package com.haman.dearme.ui.screen.setting

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.haman.dearme.ui.components.chip.RectangleChip
import com.haman.dearme.ui.components.divider.BaseDivider
import com.haman.dearme.ui.components.image.IconImage
import com.haman.dearme.ui.components.text.Sub1
import com.haman.dearme.R
import com.haman.dearme.ui.components.common.EmptyListItem
import com.haman.dearme.ui.components.common.footer
import com.haman.dearme.ui.components.container.CustomBottomSheet
import com.haman.dearme.ui.components.header.BaseAppBar
import com.haman.dearme.ui.components.header.CountHeader
import com.haman.dearme.ui.components.input.ContentInput
import com.haman.dearme.ui.components.input.InputField
import com.haman.dearme.ui.components.item.*
import com.haman.dearme.ui.components.selector.ColorSelector
import com.haman.dearme.ui.components.slider.StarSlider
import com.haman.dearme.ui.components.text.Body2
import com.haman.dearme.ui.model.*
import com.haman.dearme.ui.theme.ColorPalette
import com.haman.dearme.util.ext.fullFormat
import kotlinx.coroutines.launch
import java.time.LocalDate

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SettingScreen(
    viewModel: SettingViewModel = hiltViewModel()
) {
    val bottomSheetState =
        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val coroutineScope = rememberCoroutineScope()

    val currentTab = rememberSaveable { mutableStateOf(MyTab.DIARY) }
    val categories = viewModel.categories.collectAsState()
    val currentCategory = viewModel.currentCategory.collectAsState()
    val schedules = viewModel.schedules.collectAsState()
    val goods = viewModel.goods.collectAsState()
    val bads = viewModel.bads.collectAsState()
    val diaries = viewModel.diaries.collectAsState()

    LaunchedEffect(key1 = null) {
        viewModel.settingUiState.collect {
            when (it) {
                SettingUiState.Success.ADD_CATEGORY -> {
                    bottomSheetState.hide()
                }
                SettingUiState.Error.LESS_CATEGORY_INFO -> {
                    println("카테고리 이름을 입력해주세요.")
                }
                else -> {}
            }
        }
    }

    // sheetContent 에 아무런 component 도 설정하지 않은 상태에서 실행하면
    // The initial value must have an associated anchor. 이런 에러 발생
    CustomBottomSheet(
        sheetState = bottomSheetState,
        sheetContent = {
            CategoryAddSheet(
                category = currentCategory.value,
                onClose = {
                    coroutineScope.launch { bottomSheetState.hide() }
                },
                addCategory = viewModel::addCategory
            )
        }
    ) {
        Scaffold(
            topBar = {
                BaseAppBar(title = "My Page")
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
                        .horizontalScroll(rememberScrollState())
                ) {
                    MyTab.values().forEach { tab ->
                        Sub1(
                            text = tab.title,
                            color = when {
                                tab == currentTab.value -> ColorPalette.White
                                isSystemInDarkTheme().not() -> ColorPalette.DARK_BACKGROUND
                                else -> ColorPalette.White
                            },
                            align = TextAlign.Center,
                            modifier = Modifier
                                .padding(horizontal = 2.dp)
                                .clip(
                                    RoundedCornerShape(
                                        topStart = 10.dp,
                                        topEnd = 10.dp
                                    )
                                )
                                .background(
                                    if (tab == currentTab.value) ColorPalette.DARK_PRIMARY
                                    else Color.Transparent
                                )
                                .border(
                                    width = 1.dp,
                                    color = ColorPalette.GREY.copy(0.2f),
                                    shape = RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp)
                                )
                                .padding(vertical = 10.dp, horizontal = 16.dp)
                                .clickable { currentTab.value = tab }
                        )
                    }
                }
                BaseDivider(thickness = 1.dp)
                when (currentTab.value) {
                    MyTab.DIARY -> LazyColumn(
                        modifier = Modifier.background(
                            if (isSystemInDarkTheme()) ColorPalette.DARK_BACKGROUND
                            else ColorPalette.White
                        )
                    ) {
                        if (diaries.value.isEmpty()) {
                            item { EmptyListItem(height = 450f, message = "작성한 기록이 없습니다.") }
                        } else {
                            diaries.value.forEach { item ->
                                itemDivider()
                                item(key = item.id) { DiaryItem(diary = item) }
                            }
                            emptyDivider(height = 240f)
                        }
                        footer()
                    }
                    MyTab.GOOD -> {
                        PointList(points = goods.value, onRemoveItem = viewModel::removeGoodPoint)
                    }
                    MyTab.BAD -> {
                        PointList(points = bads.value, onRemoveItem = viewModel::removeBadPoint)
                    }
                    MyTab.SCHEDULE -> LazyColumn(
                        modifier = Modifier.background(
                            if (isSystemInDarkTheme()) ColorPalette.DARK_BACKGROUND
                            else ColorPalette.White
                        )
                    ) {
                        if (schedules.value.isEmpty()) {
                            item { EmptyListItem(height = 450f, message = "작성한 일정이 없습니다.") }
                        } else {
                            emptyDivider(height = 12f)
                            items(
                                items = schedules.value,
                                key = { item -> item.id ?: -1 }) { item ->
                                Surface(modifier = Modifier.padding(horizontal = 8.dp)) {
                                    ScheduleMainItem(
                                        schedule = item,
                                        onClickScheduleItem = {},
                                        onRemoveSchedule = viewModel::removeSchedule
                                    )
                                }
                            }
                            emptyDivider(height = 240f)
                        }
                        footer()
                    }
                    MyTab.CATEGORY -> CategoryList(
                        categories = categories.value,
                        addCategory = {
                            viewModel.setCurrentCategory(null)
                            coroutineScope.launch { bottomSheetState.show() }
                        },
                        removeCategory = viewModel::removeCategory,
                        onItemClick = {
                            viewModel.setCurrentCategory(it)
                            coroutineScope.launch { bottomSheetState.show() }
                        }
                    )
                }
            }
        }
    }
}

/**
 * 카테고리 리스트
 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CategoryList(
    categories: List<CategoryModel>,
    onItemClick: (CategoryModel) -> Unit,
    addCategory: () -> Unit,
    removeCategory: (CategoryModel) -> Unit
) {
    LazyColumn(
        modifier = Modifier.background(
            if (isSystemInDarkTheme()) ColorPalette.DARK_BACKGROUND
            else ColorPalette.White
        )
    ) {
        if (categories.isEmpty()) {
            item { EmptyListItem(height = 250f, message = "등록된 카테고라가 없습니다.") }
        } else {
            emptyDivider(height = 12f)
            items(items = categories, key = { item -> item.id ?: -1 }) { item ->
                val dismissState = rememberDismissState(DismissValue.Default)

                if (dismissState.isDismissed(DismissDirection.EndToStart)) {
                    removeCategory(item)
                }

                SwipeToDismiss(
                    state = dismissState,
                    directions = setOf(DismissDirection.EndToStart),
                    modifier = Modifier.padding(horizontal = 8.dp),
                    dismissThresholds = { direction ->
                        FractionalThreshold(0.25f)
                    },
                    background = {
                        val color by animateColorAsState(
                            targetValue = when (dismissState.targetValue) {
                                DismissValue.Default -> ColorPalette.GREY
                                else -> Color(item.color)
                            }
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(color)
                                .padding(horizontal = 20.dp),
                            contentAlignment = Alignment.CenterEnd
                        ) {
                            IconImage(icon = R.drawable.delete)
                        }
                    }
                ) {
                    CategoryListItem(category = item, onItemClick = onItemClick)
                }
            }
        }
        emptyDivider(height = 6f)
        item {
            BaseClickableItem(
                modifier = Modifier.padding(horizontal = 8.dp),
                message = "카테고리 추가하기",
                onClickItem = addCategory
            )
        }
        emptyDivider(height = 240f)
        footer()
    }
}

/**
 * 카테고리 리스트 아이템
 */
@Composable
fun CategoryListItem(
    category: CategoryModel,
    onItemClick: (CategoryModel) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                if (isSystemInDarkTheme()) ColorPalette.DARK_BACKGROUND
                else ColorPalette.White
            )
            .padding(vertical = 2.dp)
            .border(
                width = (1.5).dp,
                color = Color(category.color),
                shape = RoundedCornerShape(
                    topStart = 10.dp,
                    bottomStart = 10.dp
                )
            )
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { onItemClick(category) }
            .padding(12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Sub1(
            text = category.name,
            color = Color(category.color),
            bold = true
        )
        RectangleChip(
            text = category.name,
            color = Color(category.color)
        )
    }
}

@Composable
fun CategoryAddSheet(
    category: CategoryModel?,
    onClose: () -> Unit,
    addCategory: (CategoryModel) -> Unit
) {
    val categoryName = remember { mutableStateOf("") }
    val categoryColor = remember { mutableStateOf(0L) }

    LaunchedEffect(key1 = category) {
        categoryName.value = category?.name ?: ""
        categoryColor.value =
            category?.let { category.color } ?: ColorPalette.category.first().toLong()
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                if (isSystemInDarkTheme()) ColorPalette.Black
                else ColorPalette.White
            )
            .padding(start = 16.dp, top = 20.dp, bottom = 16.dp, end = 16.dp)
    ) {
        IconImage(
            icon = R.drawable.close,
            modifier = Modifier
                .size(12.dp)
                .align(Alignment.End)
                .clickable { onClose() },
            color = if (isSystemInDarkTheme()) ColorPalette.White
            else ColorPalette.DARK_BACKGROUND
        )
        ContentInput(
            name = "카테고리 이름",
            content = categoryName.value,
            singleLine = true,
            maxLength = 9,
            onValueChange = { categoryName.value = it }
        )
        InputField(name = "색상") {
            ColorSelector(
                colors = ColorPalette.category,
                perLine = 9,
                selectedColor = categoryColor.value,
                onSelectedItem = { categoryColor.value = it }
            )
        }
        Button(
            modifier = Modifier
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = ColorPalette.Primary,
                contentColor = ColorPalette.Primary
            ),
            onClick = {
                addCategory(
                    CategoryModel(
                        id = category?.id,
                        name = categoryName.value,
                        color = categoryColor.value
                    )
                )
            }
        ) {
            Sub1(
                text = "저장",
                color = ColorPalette.Black
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PointList(
    points: Map<LocalDate, List<PointModel>>,
    onRemoveItem: (Long) -> Unit
) {
    LazyColumn(
        modifier = Modifier.background(
            if (isSystemInDarkTheme()) ColorPalette.DARK_BACKGROUND
            else ColorPalette.White
        )
    ) {
        if (points.isEmpty()) {
            item { EmptyListItem(height = 450f, message = "작성한 사항이 없습니다.") }
        } else {
            emptyDivider(height = 12f)
            points.forEach { (date, point) ->
                stickyHeader {
                    CountHeader(title = date.fullFormat(), count = point.size)
                }
                itemsIndexed(items = point, key = { _, item -> item.id ?: -1 }) { index, item ->
                    Surface(
                        modifier = Modifier.padding(horizontal = 12.dp)
                    ) {
                        PointItem(point = item, index = index, onRemovePoint = onRemoveItem)
                    }
                }
            }
            emptyDivider(height = 240f)
        }
        footer()
    }
}

@Composable
fun DiaryItem(
    diary: DiaryRateModel
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                if (isSystemInDarkTheme()) ColorPalette.DARK_BACKGROUND
                else ColorPalette.White
            )
            .padding(12.dp)
    ) {
        Sub1(
            text = diary.date.fullFormat(),
            color = ColorPalette.DARK_BACKGROUND,
            modifier = Modifier
                .clip(RoundedCornerShape(999.dp))
                .background(ColorPalette.SECOND)
                .padding(vertical = 4.dp, horizontal = 12.dp)
        )
        StarSlider(rate = diary.rate, onClickRate = {})
        BaseDivider()
        Spacer(modifier = Modifier.height(8.dp))
        Sub1(text = diary.content, align = TextAlign.Start)
    }
}
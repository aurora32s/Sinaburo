package com.haman.dearme.ui.screen.challenge.post

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.haman.dearme.R
import com.haman.dearme.ui.components.calendar.CalendarContainer
import com.haman.dearme.ui.components.calendar.day.DurationDate
import com.haman.dearme.ui.components.calendar.rememberCalendarState
import com.haman.dearme.ui.components.common.EmptyListItem
import com.haman.dearme.ui.components.container.CustomBottomSheet
import com.haman.dearme.ui.components.divider.BaseDivider
import com.haman.dearme.ui.components.header.PostAppBar
import com.haman.dearme.ui.components.image.IconImage
import com.haman.dearme.ui.components.input.BaseInput
import com.haman.dearme.ui.components.input.ContentInput
import com.haman.dearme.ui.components.input.InputField
import com.haman.dearme.ui.components.item.BaseClickableItem
import com.haman.dearme.ui.components.item.emptyDivider
import com.haman.dearme.ui.components.spinner.CategorySpinner
import com.haman.dearme.ui.components.text.Sub1
import com.haman.dearme.ui.components.text.Sub2
import com.haman.dearme.ui.model.CategoryModel
import com.haman.dearme.ui.model.ChallengeDetailModel
import com.haman.dearme.ui.screen.setting.CategoryAddSheet
import com.haman.dearme.ui.theme.ColorPalette
import com.haman.dearme.util.ext.fullFormat
import com.skydoves.landscapist.glide.GlideImage
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.time.LocalDate

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ChallengePostScreen(
    challengeId: Long? = null,
    onBackPressed: () -> Unit,
    viewModel: ChallengePostViewModel = hiltViewModel()
) {
    val bottomSheetState =
        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(key1 = null) {
        // 해당 챌린지 정보 요청
        if (challengeId != null) viewModel.init(challengeId)

        viewModel.challengeUiState.collect {
            when (it) {
                ChallengeUiState.Success.ADD_CATEGORY -> {
                    coroutineScope.launch { bottomSheetState.hide() }
                }
                ChallengeUiState.Success.REMOVE_CHALLENGE -> {
                    onBackPressed()
                }
                else -> {}
            }
        }
    }
    val isModifyMode = viewModel.isModifyMode.collectAsState()

    val name = viewModel.name.collectAsState()
    val categories = viewModel.categories.collectAsState()
    val selectedCategoryId = viewModel.selectedCategoryId.collectAsState()
    val content = viewModel.content.collectAsState()
    val detail = viewModel.detail
    val selectedImageUri = viewModel.selectedImageUri.collectAsState()

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetMultipleContents(),
        onResult = { viewModel.setSelectedImageUri(it.first()) }
    )

    val selectedDate = viewModel.selectedDate
    val completedChallenge = viewModel.completedChallenge.collectAsState()

    CustomBottomSheet(
        sheetState = bottomSheetState,
        sheetContent = {
            CategoryAddSheet(
                category = null,
                onClose = { coroutineScope.launch { bottomSheetState.hide() } },
                addCategory = viewModel::addCategory
            )
        }
    ) {
        Scaffold(
            topBar = {
                PostAppBar(
                    title = "챌린지 관리하기",
                    isModifyMode = isModifyMode.value,
                    onSave = viewModel::addChallenge,
                    onBackPressed = onBackPressed,
                    onCancel = {
                        if (viewModel.challengeId != null) viewModel.toggleModifyMode()
                        else onBackPressed()
                    },
                    onModifyMode = viewModel::toggleModifyMode,
                    onDelete = viewModel::removeChallenge
                )
            }
        ) {
            PostBody(
                isModifyMode = isModifyMode.value,
                startedAt = selectedDate.minOrNull(),
                endedAt = selectedDate.maxOrNull(),
                challengeName = name.value,
                onNameChanged = viewModel::changeName,
                categories = categories.value,
                selectedCategoryId = selectedCategoryId.value,
                onOptionSelected = viewModel::setSelectedCategoryId,
                content = content.value,
                onContentChanged = viewModel::changeContent,
                details = detail,
                selectedImageUri = selectedImageUri.value,
                onSelectImage = {
                    galleryLauncher.launch("image/*")
                },
                addDetail = viewModel::addDetail,
                removeDetailPlan = viewModel::removeDetailChallenge,
                onCompletedDetailPlan = viewModel::completeChallenge,
                onDetailChange = viewModel::changeDetailChallenge,
                onSelectDate = viewModel::selectDate,
                completedChallengeDate = completedChallenge.value.map { it.date },
                onAddItem = { coroutineScope.launch { bottomSheetState.show() } }
            )
        }
    }
}

@Composable
fun PostBody(
    isModifyMode: Boolean,
    startedAt: LocalDate?,
    endedAt: LocalDate?,
    challengeName: String,
    onNameChanged: (String) -> Unit,
    categories: List<CategoryModel>,
    selectedCategoryId: Long?,
    onOptionSelected: (Long) -> Unit,
    content: String,
    onContentChanged: (String) -> Unit,
    details: List<ChallengeDetailModel>,
    selectedImageUri: Uri?,
    onSelectImage: () -> Unit,
    addDetail: () -> Unit,
    removeDetailPlan: (Int) -> Unit,
    onCompletedDetailPlan: (ChallengeDetailModel) -> Unit,
    onDetailChange: (Int, String) -> Unit,
    onSelectDate: (LocalDate) -> Unit,
    completedChallengeDate: List<LocalDate>,
    onAddItem: () -> Unit
) {
    // 1. 기간
    // 1.1 달력선택
    // 2. 이름
    // 3. 카테고리
    // 4. 이미지
    // 5. 내용
    // 6. 세부일정
    LazyColumn(modifier = Modifier.padding(horizontal = 12.dp)) {
        item { Spacer(modifier = Modifier.height(8.dp)) }
        if (isModifyMode || selectedImageUri != null) {
            item {
                Sub1(text = "\uD83C\uDF7F대표 이미지", bold = true, modifier = Modifier.padding(4.dp))
            }
            item {
                Box(
                    modifier = Modifier
                        .padding(vertical = 4.dp)
                        .fillMaxWidth()
                        .height(200.dp)
                        .border(
                            color = if (isSystemInDarkTheme()) ColorPalette.DARK_GREY else ColorPalette.LIGHT_GREY,
                            shape = RoundedCornerShape(10.dp),
                            width = 1.dp
                        )
                        .clickable {
                            if (isModifyMode)
                                onSelectImage()
                        }
                ) {
                    if (selectedImageUri == null) {
                        IconImage(
                            icon = R.drawable.plus,
                            modifier = Modifier.align(Alignment.Center),
                            color = if (isSystemInDarkTheme()) ColorPalette.White else ColorPalette.DARK_BACKGROUND
                        )
                    } else {
                        GlideImage(
                            imageModel = selectedImageUri,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.clip(RoundedCornerShape(10.dp))
                        )
                    }
                }

            }
        }
        item {
            ContentInput(
                name = "도전과제 이름*",
                content = challengeName,
                enabled = isModifyMode,
                onValueChange = onNameChanged,
                singleLine = true,
                maxLength = 25
            )
        }
        if (isModifyMode || content.isNotBlank()) {
            item {
                ContentInput(
                    name = "내용",
                    content = content,
                    enabled = isModifyMode,
                    onValueChange = onContentChanged
                )
            }
        }
        if (isModifyMode || selectedCategoryId != null) {
            item {
                InputField(name = "카테고리") {
                    CategorySpinner(
                        isModifyMode = isModifyMode,
                        options = categories,
                        selectedOptionId = selectedCategoryId ?: -1,
                        onOptionSelected = onOptionSelected,
                        onAddOption = onAddItem
                    )
                }
            }
            emptyDivider(12f)
        }
        item {
            Sub1(
                text = "세부일정",
                bold = true,
                modifier = Modifier.padding(4.dp)
            )
        }
        if (details.isEmpty() && isModifyMode.not()) {
            item { EmptyListItem(message = "세부일정 정보가 없습니다.") }
        } else {
            emptyDivider(6f)
            itemsIndexed(items = details) { index, item ->
                DetailChallengeListItem(
                    isModifyMode = isModifyMode,
                    detailPlan = item,
                    onValueChange = { onDetailChange(index, it) },
                    onItemClick = onCompletedDetailPlan,
                    onRemoveItem = { removeDetailPlan(index) }
                )
            }
        }
        if (isModifyMode) item {
            BaseClickableItem(
                message = "세부일정 추가하기",
                onClickItem = addDetail
            )
        }
        emptyDivider(12f)
        item {
            Sub1(
                text = "도전 기간*", bold = true,
                modifier = Modifier.padding(4.dp)
            )
        }
        item {
            DurationItem(
                isModifyMode = isModifyMode,
                startedAt = startedAt,
                endedAt = endedAt,
                onSelectDate = onSelectDate,
                completedChallengeDate = completedChallengeDate
            )
        }
        emptyDivider(12f)
        details.filter { it.completed }.groupBy { it.date }.forEach { date, item ->
            item {
                CompletedDetailChallengeLHeader(day = date!!, count = item.size)
            }
            itemsIndexed(items = item) { index, detail ->
                CompletedDetailChallengeListItem(index = index + 1, detailPlan = detail)
            }
        }
        emptyDivider(64f)
    }
}

@Composable
fun DurationItem(
    isModifyMode: Boolean,
    startedAt: LocalDate?,
    endedAt: LocalDate?,
    onSelectDate: (LocalDate) -> Unit,
    completedChallengeDate: List<LocalDate>
) {
    val showCalendarFlag = remember { mutableStateOf(true) }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        backgroundColor = if (isSystemInDarkTheme()) ColorPalette.Black else ColorPalette.White,
        border = BorderStroke(
            color = ColorPalette.LIGHT_GREY.copy(alpha = 0.3f),
            width = 1.dp
        ),
        elevation = 2.dp
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Sub1(
                text = "${startedAt?.fullFormat() ?: ""} ~ ${endedAt?.fullFormat() ?: ""}",
                color = ColorPalette.DARK_BACKGROUND,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(4.dp))
                    .background(ColorPalette.SECOND)
                    .padding(8.dp)
            )
            if (showCalendarFlag.value) {
                Spacer(modifier = Modifier.height(12.dp))
                CalendarItem(
                    isModifyMode = isModifyMode,
                    startedAt = startedAt,
                    endedAt = endedAt,
                    onSelectDate = onSelectDate,
                    completedChallengeDate = completedChallengeDate
                )
            }
            BaseClickableItem(
                message = if (showCalendarFlag.value) "달력접기\uD83D\uDC46\uD83C\uDFFB"
                else "달력보기\uD83D\uDC47\uD83C\uDFFB",
                arrangement = Arrangement.Center,
                onClickItem = {
                    showCalendarFlag.value = showCalendarFlag.value.not()
                })
        }
    }
}

@Composable
fun CalendarItem(
    isModifyMode: Boolean,
    startedAt: LocalDate?,
    endedAt: LocalDate?,
    onSelectDate: (LocalDate) -> Unit,
    completedChallengeDate: List<LocalDate>
) {
    val calendarState = rememberCalendarState()

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconImage(
                icon = R.drawable.left_arrow,
                color = if (isSystemInDarkTheme()) ColorPalette.White else ColorPalette.DARK_BACKGROUND,
                modifier = Modifier
                    .size(16.dp)
                    .clickable {
                        calendarState.monthState.currentMonth =
                            calendarState.monthState.currentMonth.minusMonths(1)
                    })
            Spacer(modifier = Modifier.width(4.dp))
            Sub1(text = calendarState.monthState.currentMonth.fullFormat())
            Spacer(modifier = Modifier.width(4.dp))
            IconImage(
                icon = R.drawable.right_arrow,
                color = if (isSystemInDarkTheme()) ColorPalette.White else ColorPalette.DARK_BACKGROUND,
                modifier = Modifier
                    .size(16.dp)
                    .clickable {
                        calendarState.monthState.currentMonth =
                            calendarState.monthState.currentMonth.plusMonths(1)
                    })
        }
        CalendarContainer(
            calendarState = calendarState,
            dayContent = {
                DurationDate(
                    today = LocalDate.now(),
                    isModifyMode = isModifyMode,
                    doneDate = completedChallengeDate,
                    startedAt = startedAt,
                    endedAt = endedAt,
                    state = it,
                    onSelectDate = onSelectDate
                )
            }
        )
    }
}

@Composable
fun DetailChallengeListItem(
    isModifyMode: Boolean,
    detailPlan: ChallengeDetailModel,
    onValueChange: (String) -> Unit,
    onItemClick: (ChallengeDetailModel) -> Unit,
    onRemoveItem: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp)
            .border(
                width = (1.5).dp,
                color = ColorPalette.GREY,
                shape = RoundedCornerShape(
                    topStart = 10.dp,
                    bottomStart = 10.dp,
                    topEnd = 0.dp,
                    bottomEnd = 0.dp
                )
            )
            .background(Color.Transparent)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { onItemClick(detailPlan) }
            .padding(
                end = 8.dp
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = detailPlan.completed,
            onCheckedChange = { onItemClick(detailPlan) },
            enabled = isModifyMode.not(),
            colors = CheckboxDefaults.colors(
                checkedColor = if (isSystemInDarkTheme()) ColorPalette.LIGHT_PRIMARY else ColorPalette.PRIMARY
            )
        )
        BaseInput(
            modifier = Modifier.weight(1f),
            value = detailPlan.title,
            enabled = isModifyMode,
            onValueChange = { onValueChange(it) },
            number = false,
            singleLine = true
        )
        if (isModifyMode)
            IconImage(
                modifier = Modifier
                    .size(16.dp)
                    .clickable { onRemoveItem() },
                icon = R.drawable.trash,
                color = if (isSystemInDarkTheme()) ColorPalette.SECOND else ColorPalette.DARK_BACKGROUND
            )
    }
}

@Composable
fun CompletedDetailChallengeLHeader(
    day: LocalDate,
    count: Int,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Sub1(text = day.fullFormat())
            Spacer(modifier = Modifier.weight(1f))
            Sub1(text = "${count}개")
        }
    }
}


@Composable
fun CompletedDetailChallengeListItem(
    index: Int,
    detailPlan: ChallengeDetailModel
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp)
            .border(
                width = (1.5).dp,
                color = ColorPalette.GREY,
                shape = RoundedCornerShape(
                    topStart = 10.dp,
                    bottomStart = 10.dp,
                    topEnd = 0.dp,
                    bottomEnd = 0.dp
                )
            )
            .padding(vertical = 12.dp, horizontal = 16.dp)
            .background(Color.Transparent),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Sub1(text = "$index . ${detailPlan.title}")
    }
}
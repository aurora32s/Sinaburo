package com.haman.dearme.ui.screen.post

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.haman.dearme.R
import com.haman.dearme.ui.MainViewModel
import com.haman.dearme.ui.components.common.EmptyListItem
import com.haman.dearme.ui.components.divider.BaseDivider
import com.haman.dearme.ui.components.header.PostAppBar
import com.haman.dearme.ui.components.image.IconImage
import com.haman.dearme.ui.components.input.BaseInput
import com.haman.dearme.ui.components.input.ContentInput
import com.haman.dearme.ui.components.input.InputField
import com.haman.dearme.ui.components.item.BaseClickableItem
import com.haman.dearme.ui.components.item.emptyDivider
import com.haman.dearme.ui.components.spinner.CategorySpinner
import com.haman.dearme.ui.components.text.Body2
import com.haman.dearme.ui.components.text.Sub1
import com.haman.dearme.ui.model.*
import com.haman.dearme.ui.model.plan.PlanState
import com.haman.dearme.ui.screen.setting.CategoryAddSheet
import com.haman.dearme.ui.theme.ColorPalette
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PostScreen(
    postId: Long? = null,
    mainViewModel: MainViewModel,
    viewModel: PostViewModel = hiltViewModel(),
    onBackPressed: () -> Unit
) {
    val scaffoldState = rememberBottomSheetScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    // 새로운 일정을 추가하거나, 기존일정에서 수정하기 버튼을 눌렀을 때
    val isModifyMode = viewModel.isModifyMode.collectAsState()

    LaunchedEffect(key1 = null) {
        viewModel.init(postId)

        viewModel.postUiState.collect {
            when (it) {
                PostUiState.Success.REMOVE_PLAN -> {
                    onBackPressed()
                }
                PostUiState.Success.ADD_CATEGORY -> {
                    coroutineScope.launch { scaffoldState.bottomSheetState.collapse() }
                }
                else -> {}
            }
        }
    }
    LaunchedEffect(key1 = isModifyMode.value) {
        scaffoldState.bottomSheetState.collapse()
    }

    val categoryName = viewModel.categoryName.collectAsState()
    val categories = viewModel.categories.collectAsState()
    val selectedCategoryId = viewModel.selectedCategoryId.collectAsState()
    val content = viewModel.content.collectAsState()
    val detailPlans = viewModel.detailPlans
    val planState = viewModel.planState.collectAsState()
    val timeStamp = rememberSaveable { mutableStateOf<List<TimeModel>>(emptyList()) }

    LaunchedEffect(key1 = viewModel.planId) {
        if (viewModel.planId != null)
            viewModel.timeStamp(viewModel.planId!!).collect {
                timeStamp.value = it
            }
    }

    BottomSheetScaffold(
        sheetShape = RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp),
        scaffoldState = scaffoldState,
        sheetContent = {
            if (isModifyMode.value) {
                CategoryAddSheet(
                    category = null,
                    onClose = { coroutineScope.launch { scaffoldState.bottomSheetState.collapse() } },
                    addCategory = viewModel::addCategory
                )
            } else {
                TimeBottomSheet(
                    planState = planState.value,
                    onRemovePlan = viewModel::removePlan,
                    onStartPlan = viewModel::startPlan,
                    onPausePlan = viewModel::pausePlan,
                    onStopPlan = viewModel::stopPlan
                )
            }
        },
        sheetElevation = 5.dp,
        sheetPeekHeight = if (isModifyMode.value) 0.dp else 50.dp
    ) {
        Scaffold(
            topBar = {
                PostAppBar(
                    title = "일정 관리",
                    isModifyMode = isModifyMode.value,
                    onBackPressed = onBackPressed,
                    onSave = {
                        viewModel.savePlan(
                            mainViewModel.year.value,
                            mainViewModel.month.value,
                            mainViewModel.day.value
                        )
                    },
                    onModifyMode = viewModel::toggleModifyMode,
                    onCancel = {
                        if (viewModel.planId != null) viewModel.toggleModifyMode()
                        else onBackPressed()
                    },
                    onDelete = viewModel::removePlan
                )
            }
        ) {
            PostBody(
                isModifyMode = isModifyMode.value,
                categoryName = categoryName.value,
                onNameChanged = viewModel::setCategoryName,
                categories = categories.value,
                selectedCategoryId = selectedCategoryId.value,
                onOptionSelected = viewModel::setSelectedCategoryId,
                onAddOption = {
                    coroutineScope.launch { scaffoldState.bottomSheetState.expand() }
                },
                content = content.value,
                onContentChanged = viewModel::setContent,
                details = detailPlans,
                addDetailPlan = viewModel::addDetailPlans,
                removeDetailPlan = viewModel::removeDetailPlans,
                onCompletedDetailPlan = viewModel::completeDetailPlan,
                onDetailChange = viewModel::changeDetailPlanContent,
                timeStamp = timeStamp.value
            )
        }
    }
}

@Composable
fun PostBody(
    isModifyMode: Boolean,
    categoryName: String,
    onNameChanged: (String) -> Unit,
    categories: List<CategoryModel>,
    selectedCategoryId: Long?,
    onOptionSelected: (Long) -> Unit,
    onAddOption: () -> Unit,
    content: String,
    onContentChanged: (String) -> Unit,
    details: List<DetailModel>,
    addDetailPlan: () -> Unit,
    removeDetailPlan: (Int) -> Unit,
    onCompletedDetailPlan: (DetailModel) -> Unit,
    onDetailChange: (Int, String) -> Unit,
    timeStamp: List<TimeModel>
) {
    LazyColumn(
        modifier = Modifier.padding(horizontal = 12.dp)
    ) {
        item { Spacer(modifier = Modifier.height(8.dp)) }
        item {
            ContentInput(
                name = "제목*",
                content = categoryName,
                singleLine = true,
                enabled = isModifyMode,
                maxLength = 25,
                onValueChange = onNameChanged
            )
        }
        if (isModifyMode || content.isNotEmpty()) {
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
                        onAddOption = onAddOption
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
                DetailPlanListItem(
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
                onClickItem = addDetailPlan
            )
        }
        if (timeStamp.isEmpty().not()) {
            emptyDivider(12f)
            item {
                Sub1(
                    text = "타임 스탬",
                    bold = true,
                    modifier = Modifier.padding(4.dp)
                )
            }
            emptyDivider(6f)
            items(items = timeStamp) { item -> TimeListItem(time = item) }
            item { BaseDivider(modifier = Modifier.padding(vertical = 4.dp)) }
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(2.dp),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Sub1(text = "총 ${timeStamp.getDuration()}")
                }
            }
            item { Spacer(modifier = Modifier.height(32.dp)) }
        }
        emptyDivider(12f)
        item { BaseDivider() }
    }
}

@Composable
fun DetailPlanListItem(
    isModifyMode: Boolean,
    detailPlan: DetailModel,
    onValueChange: (String) -> Unit,
    onItemClick: (DetailModel) -> Unit,
    onRemoveItem: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp)
            .border(
                width = (1.5).dp,
                color = ColorPalette.LIGHT_GREY,
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
            .padding(end = 8.dp),
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
fun TimeBottomSheet(
    planState: PlanState,
    onRemovePlan: () -> Unit,
    onStartPlan: () -> Unit,
    onPausePlan: () -> Unit,
    onStopPlan: () -> Unit
) {
    Column(
        modifier = Modifier
            .background(ColorPalette.PRIMARY)
            .padding(16.dp)
    ) {
        Body2(text = "일정 플레이어", color = ColorPalette.White)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 40.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            when (planState) {
                PlanState.START -> { // 시작 상태
                    IconImage(
                        icon = R.drawable.trash,
                        color = ColorPalette.White,
                        modifier = Modifier.clickable { onRemovePlan() })
                    IconImage(
                        icon = R.drawable.pause,
                        color = ColorPalette.White,
                        modifier = Modifier.clickable { onPausePlan() })
                    IconImage(
                        icon = R.drawable.stop,
                        color = ColorPalette.White,
                        modifier = Modifier.clickable { onStopPlan() })
                }
                else -> { // 일시정지, 정지
                    IconImage(
                        icon = R.drawable.trash,
                        color = ColorPalette.White,
                        modifier = Modifier.clickable { onRemovePlan() })
                    Sub1(
                        text = when (planState) {
                            PlanState.PAUSE -> "일시 정지중"
                            PlanState.STOP -> "완료된 일정"
                            PlanState.INIT -> "시작 대기중"
                            else -> ""
                        },
                        color = ColorPalette.White
                    )
                    IconImage(
                        icon = R.drawable.play,
                        color = ColorPalette.White,
                        modifier = Modifier.clickable { onStartPlan() })
                }
            }
        }
    }
}

@Composable
fun TimeListItem(
    time: TimeModel
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(ColorPalette.SECOND)
            .padding(12.dp),
        contentAlignment = Alignment.Center,
    ) {
        Sub1(
            text = time.toDuration(),
            color = ColorPalette.DARK_BACKGROUND
        )
    }
}
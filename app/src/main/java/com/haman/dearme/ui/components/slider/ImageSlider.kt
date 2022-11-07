package com.haman.dearme.ui.components.slider

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.haman.dearme.ui.components.text.Sub1
import com.haman.dearme.ui.theme.ColorPalette
import com.skydoves.landscapist.glide.GlideImage

@OptIn(ExperimentalPagerApi::class)
@Composable
fun ImageSlider(
    modifier: Modifier = Modifier,
    images: List<Uri>
) {
    val pageState = rememberPagerState()
    Column(
        modifier = modifier.background(
            if (isSystemInDarkTheme()) ColorPalette.DARK_BACKGROUND
            else ColorPalette.White
        )
    ) {
        Sub1(
            modifier = Modifier.align(Alignment.End),
            text = "${pageState.currentPage + 1}/${images.size}"
        )
        HorizontalPager(
            state = pageState,
            count = images.size,
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .height(300.dp)
                .background(ColorPalette.Black)
        ) { page ->
            GlideImage(
                imageModel = images[page],
                contentScale = ContentScale.FillHeight
            )
        }
    }
}
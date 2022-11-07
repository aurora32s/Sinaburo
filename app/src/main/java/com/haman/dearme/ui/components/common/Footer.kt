package com.haman.dearme.ui.components.common

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.haman.dearme.BuildConfig
import com.haman.dearme.ui.components.text.Sub1
import com.haman.dearme.ui.components.text.Sub2
import com.haman.dearme.ui.theme.ColorPalette

fun LazyListScope.footer(
    height: Float = 250f
) {
    item {
        Column {
            AndroidView(
                modifier = Modifier.fillMaxWidth(),
                factory = {
                    AdView(it).apply {
                        setAdSize(AdSize.SMART_BANNER)
                        adUnitId = BuildConfig.GOOGLD_AD_BANNER
//                        adUnitId = "ca-app-pub-3940256099942544/6300978111"
                        loadAd(AdRequest.Builder().build())
                    }
                })
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(height.dp)
                    .background(
                        if (isSystemInDarkTheme()) ColorPalette.DARK_SPACER
                        else ColorPalette.SPACER
                    )
                    .padding(horizontal = 12.dp, vertical = 20.dp)
            ) {
                Sub1(
                    text = "개발자: 섬섬",
                    align = TextAlign.Start,
                    color = if (isSystemInDarkTheme()) ColorPalette.White else ColorPalette.DARK_BACKGROUND
                )
                Sub1(
                    text = "시나브로: 모르는 사이에 조금씩 조금씩",
                    align = TextAlign.Start,
                    color = if (isSystemInDarkTheme()) ColorPalette.White else ColorPalette.DARK_BACKGROUND
                )
                Spacer(modifier = Modifier.height(12.dp))
                Sub2(
                    text = "이 앱을 사용해주신 모든 분들께 감사 인사 드립니다. \uD83D\uDE00",
                    align = TextAlign.Start,
                    color = if (isSystemInDarkTheme()) ColorPalette.LIGHT_GREY else ColorPalette.DARK_GREY
                )
                Sub2(
                    text = "아직 학습단계에 있어 부족한 점이 많을 수 있는 점 양해 부탁드립니다.",
                    align = TextAlign.Start,
                    color = if (isSystemInDarkTheme()) ColorPalette.LIGHT_GREY else ColorPalette.DARK_GREY
                )
                Sub2(
                    text = "문제가 발생했을 시, 구글 플레이에 댓글을 남겨주세요.",
                    align = TextAlign.Start,
                    color = if (isSystemInDarkTheme()) ColorPalette.LIGHT_GREY else ColorPalette.DARK_GREY
                )
                Sub2(
                    text = "감사합니다. \uD83D\uDE0C",
                    align = TextAlign.Start,
                    color = if (isSystemInDarkTheme()) ColorPalette.LIGHT_GREY else ColorPalette.DARK_GREY
                )
            }
        }
    }
}
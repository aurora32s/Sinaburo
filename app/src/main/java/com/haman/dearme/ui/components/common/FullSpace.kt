package com.haman.dearme.ui.components.common

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun RowScope.FullSpace() {
    Spacer(modifier = Modifier.weight(1f))
}
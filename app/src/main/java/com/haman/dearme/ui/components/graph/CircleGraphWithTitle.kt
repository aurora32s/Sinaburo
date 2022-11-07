package com.haman.dearme.ui.components.graph

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.haman.dearme.ui.components.text.Sub1
import com.haman.dearme.ui.model.BaseCircleGraphModel
import com.haman.dearme.util.ext.fullTimeFormat

@Composable
fun CircleGraphWithTitle(
    modifier: Modifier = Modifier,
    data: List<BaseCircleGraphModel>,
    title: String,
    total: Long
) {
    Box(modifier = modifier.fillMaxWidth()) {
        CircleGraph(
            data = data,
            totalCount = total,
            modifier = Modifier
                .height(240.dp)
                .fillMaxWidth()
                .padding(16.dp)
        )
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Sub1(
                text = title,
                bold = true
            )
        }
    }
}
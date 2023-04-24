package pl.better.foodzilla.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun Table(
    tableData: Map<String, String>,
    label1: String,
    label2: String,
    modifier: Modifier = Modifier,
    column1Weight: Float = .5f,
    column2Weight: Float = .5f
) {
    Column(modifier.fillMaxWidth()) {
        Row(Modifier.background(Color(224, 224, 224))) {
            TableCell(text = label1, weight = column1Weight, bold = true)
            TableCell(text = label2, weight = column2Weight, bold = true)
        }
        tableData.entries.forEach {
            Row(Modifier.fillMaxWidth()) {
                TableCell(text = it.key, weight = column1Weight)
                TableCell(text = it.value, weight = column2Weight)
            }
        }
    }
}
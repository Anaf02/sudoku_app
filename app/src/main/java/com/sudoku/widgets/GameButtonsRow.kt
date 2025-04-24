package com.sudoku.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun GameButtonsRow(
    onSolveClick: () -> Unit,
    onNewGameClick: () -> Unit
) {
    FlowRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        maxItemsInEachRow = 3
    ) {
        GameButton(
            onClick = {},
            icon = Icons.Default.Build,
            contentDescription = "Check Solution",
            text = "Check Solution"
        )
        GameButton(
            onClick = onSolveClick,
            icon = Icons.Default.CheckCircle,
            contentDescription = "Show Solution",
            text = "Show Solution"
        )
        GameButton(
            onClick = onNewGameClick,
            icon = Icons.Default.Refresh,
            contentDescription = "New Game",
            text = "New Game"
        )
    }
}

@Composable
fun GameButton(
    onClick: () -> Unit,
    icon: ImageVector,
    contentDescription: String,
    text: String
) {
    Button(
        onClick = onClick,
        modifier = Modifier.padding(8.dp),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp)
    ) {
        Icon(icon, contentDescription = contentDescription)
        Spacer(modifier = Modifier.width(8.dp))
        Text(text)
    }
}

@Preview(showBackground = true)
@Composable
fun GameButtonsRowPreview() {
    GameButtonsRow(
        onSolveClick = {},
        onNewGameClick = {}
    )
}
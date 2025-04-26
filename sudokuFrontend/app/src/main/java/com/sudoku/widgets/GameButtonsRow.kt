package com.sudoku.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun GameButtonsRow(
    onCheckSolutionClick: () -> Unit,
    onShowSolutionClick: () -> Unit,
    onNewGameClick: () -> Unit,
    onClearAllClick: () -> Unit
) {
    FlowRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        maxItemsInEachRow = 3
    ) {
        GameButton(
            onClick = onCheckSolutionClick,
            icon = Icons.Default.Build,
            contentDescription = "Check Solution",
            text = "Check Solution"
        )
        GameButton(
            onClick = onShowSolutionClick,
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
        GameButton(
            onClick = onClearAllClick,
            icon = Icons.Default.Clear,
            contentDescription = "Clear All",
            text = "Clear All"
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
        modifier = Modifier
            .padding(8.dp)
            .width(140.dp)
            .height(55.dp),
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
        onCheckSolutionClick = {},
        onShowSolutionClick = {},
        onNewGameClick = {},
        onClearAllClick = {}
    )
}
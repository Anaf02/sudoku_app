package com.sudoku.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun GameButtonsRow(
    onSolveClick: () -> Unit,
    onNewGameClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(end = 16.dp),
        horizontalArrangement = Arrangement.End
    ) {
        Button(
            onClick = onSolveClick,
            modifier = Modifier.padding(8.dp),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp)
        ) {
            Icon(Icons.Default.CheckCircle, contentDescription = "Solve Sudoku")
            Spacer(modifier = Modifier.width(8.dp))
            Text("Solve Sudoku")
        }
        Button(
            onClick = onNewGameClick,
            modifier = Modifier.padding(8.dp),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp)
        ) {
            Icon(Icons.Default.Refresh, contentDescription = "New Game")
            Spacer(modifier = Modifier.width(8.dp))
            Text("New Game")
        }
    }
}

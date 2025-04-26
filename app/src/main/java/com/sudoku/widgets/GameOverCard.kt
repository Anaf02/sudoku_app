package com.sudoku.widgets


import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sudoku.viewModels.TimerViewModel

@Composable
fun GameOverCard(
    timerViewModel: TimerViewModel,
    onNewGameClick: () -> Unit,
    onBackToMenuClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Congratulations!",
                style = MaterialTheme.typography.headlineMedium
            )

            Text(
                text = "You've successfully solved the Sudoku!",
                style = MaterialTheme.typography.bodyLarge
            )

            TimerCard(timerViewModel = timerViewModel)

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(onClick = onNewGameClick) {
                    Text(text = "New Game")
                }
                Button(onClick = onBackToMenuClick) {
                    Text(text = "Menu")
                }
            }
        }
    }
}
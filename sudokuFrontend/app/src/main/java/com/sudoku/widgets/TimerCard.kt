package com.sudoku.widgets

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sudoku.viewModels.TimerViewModel
import java.util.Locale

@Composable
fun TimerCard(timerViewModel: TimerViewModel) {
    val seconds by remember { timerViewModel.seconds }

    val minutes = seconds / 60
    val remainingSeconds = seconds % 60
    val formattedTime = String.format(Locale.UK, "%02d:%02d", minutes, remainingSeconds)

    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        modifier = Modifier
            .padding(16.dp)
            .wrapContentSize()
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .padding(5.dp)
                .wrapContentSize()
        ) {
            Text(text = "Elapsed time: $formattedTime")
        }
    }
}
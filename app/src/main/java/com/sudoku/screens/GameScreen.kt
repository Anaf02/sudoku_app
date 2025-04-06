package com.sudoku.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.sudoku.InvalidSudokuBoardException
import com.sudoku.convertStringToSudokuMatrix
import com.sudoku.widgets.SimpleTopBar
import com.sudoku.widgets.SudokuBoard

@Composable
fun GameScreenContent(navController: NavController) {
    val inputBoard =
        "020005789000290005000600300000060000700003090300002070000584000806300200090000007"

    val inputMatrixResult = remember(inputBoard) {
        try {
            Result.success(
                convertStringToSudokuMatrix(inputBoard)
            )
        } catch (e: InvalidSudokuBoardException) {
            Result.failure(e)
        }
    }

    Scaffold(
        topBar = {
            SimpleTopBar(
                title = "Sudoku",
                navController = navController,
                showBackArrow = true
            )
        }

    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(42.dp))

            inputMatrixResult.fold(
                onSuccess = { matrix -> SudokuBoard(matrix) },
                onFailure = { error ->
                    Text(text = error.message ?: "Invalid board input")
                }
            )

            Spacer(modifier = Modifier.height(42.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 16.dp),
                horizontalArrangement = Arrangement.End
            ) {
                Button(onClick = {}, modifier = Modifier.padding(8.dp)) {
                    Text("Solve Sudoku")
                }
                Button(onClick = {}, modifier = Modifier.padding(8.dp)) {
                    Text("Restart Game")
                }
            }
        }
    }
}
package com.sudoku.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.sudoku.viewModels.GameViewModel
import com.sudoku.utils.InvalidSudokuBoardException
import com.sudoku.utils.convertStringToSudokuMatrix
import com.sudoku.widgets.CustomAlertDialog
import com.sudoku.widgets.SimpleTopBar
import com.sudoku.widgets.SudokuBoard

@Composable
fun GameScreenContent(navController: NavController, viewModel: GameViewModel) {
    var showRestartDialog by remember { mutableStateOf(false) }

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

            when {
                viewModel.errorMessage != null -> {
                    Text(text = viewModel.errorMessage ?: "Unknown error")
                }

                viewModel.inputBoard == null -> {
                    Text("Loading puzzle...")
                }

                else -> {
                    val matrixResult = if (viewModel.solutionBoard != null) {
                        try {
                            Result.success(convertStringToSudokuMatrix(viewModel.solutionBoard!!))
                        } catch (e: InvalidSudokuBoardException) {
                            Result.failure(e)
                        }
                    } else {
                        viewModel.inputMatrixResult
                    }

                    matrixResult.fold(
                        onSuccess = { matrix -> SudokuBoard(matrix!!, viewModel.inputBoardIndex) },
                        onFailure = { error -> Text(text = error.message ?: "Invalid board input") }
                    )
                }
            }

            Spacer(modifier = Modifier.height(42.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 16.dp),
                horizontalArrangement = Arrangement.End
            ) {
                Button(onClick = { viewModel.solveSudoku() }, modifier = Modifier.padding(8.dp)) {
                    Text("Solve Sudoku")
                }
                Button(
                    onClick = { showRestartDialog = true },
                    modifier = Modifier.padding(8.dp)
                ) {
                    Text("Restart Game")
                }
                if (showRestartDialog) {
                    CustomAlertDialog(
                        title = "Restart Game",
                        message = "Are you sure you want to restart the game?",
                        onConfirm = {
                            viewModel.restartGame()
                            showRestartDialog = false
                        },
                        onDismiss = { showRestartDialog = false }
                    )
                }

            }
        }
    }
}
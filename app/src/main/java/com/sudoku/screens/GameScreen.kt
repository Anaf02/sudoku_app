package com.sudoku.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
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
import com.sudoku.widgets.NumberInputPad
import com.sudoku.widgets.SimpleTopBar
import com.sudoku.widgets.SudokuBoard

@Composable
fun GameScreenContent(navController: NavController, viewModel: GameViewModel) {
    var showRestartDialog by remember { mutableStateOf(false) }
    var selectedCell by remember { mutableStateOf<Pair<Int, Int>?>(null) }

    val onNumberPadClick: (Int) -> Unit = { newValue ->
        selectedCell?.let { (row, col) ->
            viewModel.updateCell(row, col, newValue)
        }
    }

    Scaffold(
        topBar = {
            SimpleTopBar(
                title = "Sudoku: ${viewModel.inputBoardIndex}",
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
                    val matrixResult = when {
                        viewModel.solutionBoard != null -> {
                            try {
                                Result.success(convertStringToSudokuMatrix(viewModel.solutionBoard!!))
                            } catch (e: InvalidSudokuBoardException) {
                                Result.failure(e)
                            }
                        }

                        else -> {
                            Result.success(viewModel.currentBoard.takeIf { it.isNotEmpty() }
                                ?: convertStringToSudokuMatrix(viewModel.inputBoard ?: ""))
                        }
                    }

                    matrixResult.fold(
                        onSuccess = { matrix ->
                            SudokuBoard(
                                matrix,
                                editableCells = viewModel.editableCells,
                                selectedCell = selectedCell,
                                onCellClick = { row, col -> selectedCell = row to col })
                        },
                        onFailure = { error -> Text(text = error.message ?: "Invalid board input") }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            NumberInputPad(
                onNumberClick = { newValue -> onNumberPadClick(newValue) }
            )

            Spacer(modifier = Modifier.height(32.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 16.dp),
                horizontalArrangement = Arrangement.End
            ) {
                Button(
                    onClick = { viewModel.solveSudoku() },
                    modifier = Modifier.padding(8.dp),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp)
                ) {
                    Icon(Icons.Default.CheckCircle, contentDescription = "Solve Sudoku")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Solve Sudoku")
                }
                Button(
                    onClick = { showRestartDialog = true },
                    modifier = Modifier.padding(8.dp),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp)
                ) {
                    Icon(Icons.Default.Refresh, contentDescription = "New Game")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("New Game")
                }
                if (showRestartDialog) {
                    CustomAlertDialog(
                        title = "New Game",
                        message = "Are you sure you want to start a new the game?",
                        onConfirm = {
                            viewModel.newGame()
                            showRestartDialog = false
                        },
                        onDismiss = { showRestartDialog = false }
                    )
                }
            }
        }
    }
}

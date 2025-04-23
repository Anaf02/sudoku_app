package com.sudoku.utils

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.sudoku.viewModels.GameViewModel
import com.sudoku.widgets.SudokuBoard
import io.ktor.websocket.Frame

@Composable
fun SudokuContent(
    viewModel: GameViewModel,
    selectedCell: Pair<Int, Int>?,
    onCellClick: (Int, Int) -> Unit
) {
    when {
        viewModel.errorMessage != null -> {
            Frame.Text(text = viewModel.errorMessage ?: "Unknown error")
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
                        matrix = matrix,
                        editableCells = viewModel.editableCells,
                        selectedCell = selectedCell,
                        onCellClick = onCellClick
                    )
                },
                onFailure = { error -> Text(text = error.message ?: "Invalid board input") }
            )
        }
    }
}

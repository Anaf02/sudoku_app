package com.sudoku.utils

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.sudoku.viewModels.GameViewModel
import com.sudoku.widgets.SudokuBoard

@Composable
fun SudokuContent(
    viewModel: GameViewModel,
    selectedCell: Pair<Int, Int>?,
    onCellClick: (Int, Int) -> Unit
) {
    val matrixResult = viewModel.matrixResult
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
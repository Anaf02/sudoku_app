package com.sudoku

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class GameViewModel : ViewModel() {
    var inputBoard by mutableStateOf<String?>(null)
        private set

    var inputBoardIndex by mutableStateOf<Int?>(null)
        private set

    var solutionBoard by mutableStateOf<String?>(null)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    val inputMatrixResult: Result<Array<IntArray>?>
        get() = try {
            inputBoard?.let {
                Result.success(convertStringToSudokuMatrix(it))
            } ?: Result.failure(InvalidSudokuBoardException("Empty board"))
        } catch (e: InvalidSudokuBoardException) {
            Result.failure(e)
        }

    init {
        loadSudoku()
    }

    private fun loadSudoku() {
        viewModelScope.launch {
            try {
                val result = fetchRandomSudoku()
                inputBoard = result.puzzle
                inputBoardIndex = result.index
            } catch (e: Exception) {
                errorMessage = "Failed to load puzzle: ${e.localizedMessage}"
            }
        }
    }

    fun restartGame() {
        loadSudoku()
        solutionBoard = null
    }

    private fun loadSudokuSolution() {
        viewModelScope.launch {
            try {
                val result = fetchSudokuSolution(inputBoardIndex)
                solutionBoard = result.solution
            } catch (e: Exception) {
                errorMessage = "Failed to load puzzle: ${e.localizedMessage}"
            }
        }
    }

    fun solveSudoku() {
        loadSudokuSolution()
    }
}

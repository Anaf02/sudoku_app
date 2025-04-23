package com.sudoku.viewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sudoku.utils.InvalidSudokuBoardException
import com.sudoku.utils.convertStringToSudokuMatrix
import com.sudoku.network.fetchRandomSudoku
import com.sudoku.network.fetchSudokuSolution
import com.sudoku.utils.getInitialEditableCells
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

    var editableCells by mutableStateOf<Array<BooleanArray>>(emptyArray())
        private set

    var currentBoard by mutableStateOf<Array<IntArray>>(emptyArray())
        private set

    var selectedCell by  mutableStateOf<Pair<Int, Int>?>(null)


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
                editableCells = getInitialEditableCells(convertStringToSudokuMatrix(result.puzzle))
                currentBoard = convertStringToSudokuMatrix(result.puzzle)
            } catch (e: Exception) {
                errorMessage = "Failed to load puzzle: ${e.localizedMessage}"
            }
        }
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

    fun newGame() {
        loadSudoku()
        solutionBoard = null
        errorMessage = null
        selectedCell = null
    }

    fun solveSudoku() {
        loadSudokuSolution()
        selectedCell = null
    }

    fun updateCell(row: Int, col: Int, newValue: Int) {
        if (editableCells[row][col]) {
            val updatedBoard = currentBoard.map { it.copyOf() }.toTypedArray()
            updatedBoard[row][col] = newValue
            currentBoard = updatedBoard
        }
        selectedCell = null
    }

    fun clearCell(row: Int, col: Int, newValue: Int){
        if (editableCells[row][col]) {
            val updatedBoard = currentBoard.map { it.copyOf() }.toTypedArray()
            updatedBoard[row][col] = 0
            currentBoard = updatedBoard
        }
        selectedCell = null
    }
}
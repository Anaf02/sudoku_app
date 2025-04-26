package com.sudoku.viewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sudoku.utils.InvalidSudokuBoardException
import com.sudoku.utils.convertStringToSudokuMatrix
import com.sudoku.network.fetchRandomSudoku
import com.sudoku.network.fetchSudokuSolution
import com.sudoku.utils.CellCheckResult
import com.sudoku.utils.convertSudokuMatrixToString
import com.sudoku.utils.getInitialEditableCells
import com.sudoku.utils.getInitialFilledCellsNumber
import com.sudoku.utils.getNumberOfCorrectCells
import kotlinx.coroutines.launch

class GameViewModel : ViewModel() {
    var inputBoardIndex by mutableStateOf<Int?>(null)
        private set

    var selectedCell by mutableStateOf<Pair<Int, Int>?>(null)

    var editableCells by mutableStateOf<Array<BooleanArray>>(emptyArray())
        private set

    private var inputBoard by mutableStateOf<String?>(null)

    private var solutionBoard by mutableStateOf<String?>(null)

    private var errorMessage by mutableStateOf<String?>(null)

    private var currentBoard by mutableStateOf<Array<IntArray>>(emptyArray())

    private var solutionFetched by mutableStateOf<String?>(null)


    val matrixResult: Result<Array<IntArray>>
        get() = try {
            when {
                solutionBoard != null -> Result.success(convertStringToSudokuMatrix(solutionBoard!!))
                currentBoard.isNotEmpty() -> Result.success(currentBoard)
                inputBoard != null -> Result.success(convertStringToSudokuMatrix(inputBoard!!))
                else -> Result.failure(InvalidSudokuBoardException("No available board"))
            }
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

                loadSudokuSolution(inputBoardIndex!!)
            } catch (e: Exception) {
                setError(e, "Failed to load input puzzle")
            }
        }
    }

    private fun loadSudokuSolution(index: Int) {
        viewModelScope.launch {
            try {
                val result = fetchSudokuSolution(index)
                solutionFetched = result.solution
            } catch (e: Exception) {
                setError(e, "Failed to load solution puzzle")
            }
        }
    }

    fun newGame() {
        loadSudoku()
        solutionBoard = null
        errorMessage = null
        selectedCell = null
        solutionFetched = null
    }

    fun solveSudoku() {
        if (solutionFetched != null)
            solutionBoard = solutionFetched
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

    fun clearCell(row: Int, col: Int) {
        if (editableCells[row][col]) {
            val updatedBoard = currentBoard.map { it.copyOf() }.toTypedArray()
            updatedBoard[row][col] = 0
            currentBoard = updatedBoard
        }
        selectedCell = null
    }

    fun clearAllCells() {
        currentBoard = convertStringToSudokuMatrix(inputBoard)
        solutionBoard = null
        selectedCell = null
    }

    fun checkSolution(): CellCheckResult {
        return getNumberOfCorrectCells(
            convertSudokuMatrixToString(currentBoard),
            solutionFetched!!,
            getInitialFilledCellsNumber(inputBoard!!)
        )
    }

    private fun setError(e: Exception, message: String) {
        errorMessage = "$message: ${e.localizedMessage}"
    }
}
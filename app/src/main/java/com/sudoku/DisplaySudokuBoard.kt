package com.sudoku

fun convertStringToSudokuMatrix(numbersOnBoard: String): Array<IntArray> {
    if (numbersOnBoard.length != 81) {
        throw InvalidSudokuBoardException("Sudoku 9x9 board must receive an input of exactly 81 characters. Got ${numbersOnBoard.length}")
    }
    val matrix: Array<IntArray> = Array(9) { IntArray(9) }
    numbersOnBoard.forEachIndexed { index, char ->
        val row = index / 9
        val col = index % 9
        matrix[row][col] = char.digitToIntOrNull() ?: 0

    }

    return matrix
}

class InvalidSudokuBoardException(message: String) : Exception(message)

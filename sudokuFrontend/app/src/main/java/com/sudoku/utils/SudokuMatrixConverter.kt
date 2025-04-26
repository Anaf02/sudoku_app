package com.sudoku.utils

fun convertStringToSudokuMatrix(numbersOnBoard: String?): Array<IntArray> {
    if (numbersOnBoard?.length != 81) {
        throw InvalidSudokuBoardException("Sudoku 9x9 board must receive an input of exactly 81 characters. Got ${numbersOnBoard?.length}")
    }
    val matrix: Array<IntArray> = Array(9) { IntArray(9) }
    numbersOnBoard.forEachIndexed { index, char ->
        val row = index / 9
        val col = index % 9
        matrix[row][col] = char.digitToIntOrNull() ?: 0

    }

    return matrix
}

fun convertSudokuMatrixToString(sudokuBoard: Array<IntArray>): String {
    if (sudokuBoard.size != 9 || sudokuBoard.any { it.size != 9 }) {
        throw InvalidSudokuBoardException("Sudoku board must be 9x9.")
    }

    val builder = StringBuilder(81)
    for (row in sudokuBoard) {
        for (cell in row) {
            builder.append(cell.coerceIn(0, 9))
        }
    }

    return builder.toString()
}

class InvalidSudokuBoardException(message: String) : Exception(message)
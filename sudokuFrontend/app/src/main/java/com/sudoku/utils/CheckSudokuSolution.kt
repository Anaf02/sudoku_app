package com.sudoku.utils

data class CellCheckResult(
    val correctCells: Int,
    val incorrectCells: Int
)

fun getNumberOfCorrectCells(
    providedSolution: String,
    correctSolution: String,
    initialFilledCells: Int
): CellCheckResult {
    val matchedCells = providedSolution.zip(correctSolution)
    val correct = matchedCells.count { (providedChar, correctChar) ->
        providedChar == correctChar
    } - initialFilledCells
    val incorrect = matchedCells.count { (providedChar, correctChar) ->
        providedChar != correctChar
    }

    return CellCheckResult(correctCells = correct, incorrectCells = incorrect)
}

fun getInitialFilledCellsNumber(inputBoard: String): Int {
    return inputBoard.count { it != '0' }
}
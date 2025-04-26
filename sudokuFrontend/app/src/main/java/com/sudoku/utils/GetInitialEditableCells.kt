package com.sudoku.utils

import android.util.Log

fun getInitialEditableCells(matrix: Array<IntArray>): Array<BooleanArray> {
    val editableCells = Array(matrix.size) { BooleanArray(matrix[0].size) }

    matrix.forEachIndexed { i, row ->
        row.forEachIndexed { j, element ->
            editableCells[i][j] = element == 0
        }
    }
    editableCells.forEachIndexed { i, row ->
        Log.d("EditableCells", "Row $i: ${row.joinToString(", ")}")
    }
    matrix.forEachIndexed { i, row ->
        Log.d("Matrix", "Row $i: ${row.joinToString(", ")}")
    }
    return editableCells
}
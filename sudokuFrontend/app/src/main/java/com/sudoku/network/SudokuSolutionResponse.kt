package com.sudoku.network

import kotlinx.serialization.Serializable

@Serializable
data class SudokuSolutionResponse(
    val index: Int,
    val solution: String
)
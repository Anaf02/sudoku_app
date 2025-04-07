package com.sudoku.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SudokuBoard(matrix: Array<IntArray>, onCellClick: () -> Unit) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .background(Color.White)
    ) {
        for (row in 0 until 3) {
            Row {
                for (col in 0 until 3) {
                    val cellValue = Array(3) { i ->
                        IntArray(3) { j ->
                            matrix[row * 3 + i][col * 3 + j]
                        }
                    }
                    MiniGrid3x3(cellValue, onCellClick)
                }
            }
        }
    }
}

@Composable
fun MiniGrid3x3(cellValue: Array<IntArray>, onCellClick: () -> Unit) {
    Column(
        modifier = Modifier
            .border(1.7.dp, Color.Black)
    ) {
        for (row in 0 until 3) {
            Row {
                for (col in 0 until 3) {
                    val value = cellValue[row][col]
                    val isCellClickable = value == 0;

                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(36.dp)
                            .border(0.5.dp, Color.Gray)
                            .then(
                                if (!isCellClickable) Modifier.background(Color.LightGray)
                                else Modifier
                            )
                            .clickable(
                                enabled = isCellClickable,
                                onClick = onCellClick
                            )
                    ) {
                        Text(
                            text = if (value == 0) "" else value.toString(),
                            fontSize = 18.sp
                        )
                    }
                }
            }
        }
    }
}
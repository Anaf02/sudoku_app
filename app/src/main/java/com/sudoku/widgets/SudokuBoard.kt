package com.sudoku.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SudokuBoard(
    matrix: Array<IntArray>,
    editableCells: Array<BooleanArray>,
    selectedCell: Pair<Int, Int>?,
    onCellClick: (Int, Int) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .background(Color.White)
    ) {
        for (row in 0 until 3) {
            Row {
                for (col in 0 until 3) {
                    val startRow = row * 3
                    val startCol = col * 3

                    val subgridValues = Array(3) { i ->
                        IntArray(3) { j ->
                            matrix[startRow + i][startCol + j]
                        }
                    }

                    val editableCellBlock = Array(3) { i ->
                        BooleanArray(3) { j ->
                            editableCells[startRow + i][startCol + j]
                        }
                    }
                    MiniGrid3x3(
                        cellValue = subgridValues,
                        editableCells = editableCellBlock,
                        startRow,
                        startCol,
                        selectedCell,
                        onCellClick = { localRow, localCol ->
                            onCellClick(startRow + localRow, startCol + localCol)
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun MiniGrid3x3(
    cellValue: Array<IntArray>,
    editableCells: Array<BooleanArray>,
    startRow: Int,
    startCol: Int,
    selectedCell: Pair<Int, Int>?,
    onCellClick: (Int, Int) -> Unit
) {
    Column(
        modifier = Modifier
            .border(1.7.dp, Color.Black)
    ) {
        for (row in 0 until 3) {
            Row {
                for (col in 0 until 3) {
                    val value = cellValue[row][col]
                    val isCellClickable = editableCells[row][col]
                    val absoluteRow = startRow + row
                    val absoluteCol = startCol + col
                    val isSelected =
                        selectedCell?.first == absoluteRow && selectedCell.second == absoluteCol

                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(36.dp)
                            .border(0.5.dp, Color.Gray)
                            .background(
                                when {
                                    isSelected -> MaterialTheme.colorScheme.inversePrimary
                                    !isCellClickable -> MaterialTheme.colorScheme.surfaceContainerHighest
                                    else -> Color.Transparent
                                }
                            )
                            .clickable(
                                enabled = isCellClickable,
                                onClick = { onCellClick(row, col) }
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
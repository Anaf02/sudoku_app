package com.sudoku.widgets

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun NumberInputPad(
    onNumberClick: (Int) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(9),
        modifier = Modifier
            .padding(horizontal = 4.dp)
            .heightIn(max = 100.dp),
        verticalArrangement = Arrangement.spacedBy(2.dp),
        horizontalArrangement = Arrangement.spacedBy(2.dp),
    ) {
        items((1..9).toList()) { number ->
            OutlinedButton(
                onClick = { onNumberClick(number) },
                contentPadding = PaddingValues(horizontal = 2.dp, vertical = 2.dp),
                shape = RectangleShape,
                colors = ButtonDefaults.filledTonalButtonColors()
            ) {
                Text(text = number.toString(), fontSize = 14.sp)
            }
        }
    }
}

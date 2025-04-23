package com.sudoku.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.sudoku.viewModels.GameViewModel
import com.sudoku.utils.SudokuContent
import com.sudoku.widgets.CellClearIcon
import com.sudoku.widgets.CustomAlertDialog
import com.sudoku.widgets.GameButtonsRow
import com.sudoku.widgets.NumberInputPad
import com.sudoku.widgets.SimpleTopBar

@Composable
fun GameScreenContent(navController: NavController, viewModel: GameViewModel) {
    var showRestartDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            SimpleTopBar(
                title = "Sudoku: ${viewModel.inputBoardIndex}",
                navController = navController,
                showBackArrow = true
            )
        }

    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(42.dp))

            SudokuContent(
                viewModel = viewModel,
                selectedCell = viewModel.selectedCell,
                onCellClick = { row, col -> viewModel.selectedCell = row to col }
            )
            Spacer(modifier = Modifier.height(16.dp))

            NumberInputPad(onNumberClick = { value ->
                viewModel.selectedCell?.let { (row, col) ->
                    viewModel.updateCell(row, col, value)
                }
            })

            CellClearIcon(onIconClick = {
                viewModel.selectedCell?.let { (row, col) ->
                    viewModel.clearCell(row, col)
                }
            })

            Spacer(modifier = Modifier.height(32.dp))

            GameButtonsRow(
                onSolveClick = { viewModel.solveSudoku() },
                onNewGameClick = { showRestartDialog = true }
            )

            if (showRestartDialog) {
                CustomAlertDialog(
                    title = "New Game",
                    message = "Are you sure you want to start a new the game?",
                    onConfirm = {
                        viewModel.newGame()
                        showRestartDialog = false
                    },
                    onDismiss = { showRestartDialog = false }
                )
            }
        }
    }
}

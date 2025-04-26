package com.sudoku.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.sudoku.utils.CellCheckResult
import com.sudoku.viewModels.GameViewModel
import com.sudoku.utils.SudokuContent
import com.sudoku.viewModels.TimerViewModel
import com.sudoku.widgets.CellClearIcon
import com.sudoku.widgets.CustomAlertDialog
import com.sudoku.widgets.GameButtonsRow
import com.sudoku.widgets.GameOverCard
import com.sudoku.widgets.NumberInputPad
import com.sudoku.widgets.SimpleTopBar
import com.sudoku.widgets.TimerCard

@Composable
fun GameScreenContent(navController: NavController, gameViewModel: GameViewModel) {
    var showRestartDialog by remember { mutableStateOf(false) }
    var checkSolutionDialog by remember { mutableStateOf(false) }
    var checkedCells by remember { mutableStateOf<CellCheckResult?>(null) }
    val timerViewModel: TimerViewModel = viewModel()

    Scaffold(
        topBar = {
            SimpleTopBar(
                title = "Sudoku: ${gameViewModel.inputBoardIndex}",
                navController = navController,
                showBackArrow = true
            )
        }

    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        )
        {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {

                SudokuContent(
                    viewModel = gameViewModel,
                    selectedCell = gameViewModel.selectedCell,
                    onCellClick = { row, col -> gameViewModel.selectedCell = row to col }
                )
                Spacer(modifier = Modifier.height(16.dp))

                NumberInputPad(onNumberClick = { value ->
                    gameViewModel.selectedCell?.let { (row, col) ->
                        gameViewModel.updateCell(row, col, value)
                    }
                })

                CellClearIcon(onIconClick = {
                    gameViewModel.selectedCell?.let { (row, col) ->
                        gameViewModel.clearCell(row, col)
                    }
                })

                TimerCard(timerViewModel = timerViewModel)

                GameButtonsRow(
                    onCheckSolutionClick = {
                        if (!gameViewModel.solutionShowed) {
                            checkedCells = gameViewModel.checkSolution()
                            checkSolutionDialog = true
                        }
                    },
                    onShowSolutionClick = {
                        gameViewModel.solveSudoku()
                        timerViewModel.stopTimer()
                    },
                    onNewGameClick = { showRestartDialog = true },
                    onClearAllClick = { gameViewModel.clearAllCells() }
                )
            }
            if (showRestartDialog) {
                CustomAlertDialog(
                    title = "New Game",
                    message = "Are you sure you want to start a new the game?",
                    onConfirm = {
                        gameViewModel.newGame()
                        timerViewModel.resetTimer()
                        showRestartDialog = false
                    },
                    onDismiss = { showRestartDialog = false },
                    confirmButtonText = "Yes",
                    dismissButtonText = "No"
                )
            }
            if (checkSolutionDialog) {
                if (checkedCells!!.incorrectCells == 0) {
                    timerViewModel.stopTimer()
                    GameOverCard(
                        timerViewModel = timerViewModel,
                        onNewGameClick = {
                            gameViewModel.newGame()
                            timerViewModel.resetTimer()
                            checkSolutionDialog = false
                        },
                        onBackToMenuClick = {
                            navController.popBackStack()
                            timerViewModel.resetTimer()
                            checkSolutionDialog = false
                        }
                    )
                } else
                    CustomAlertDialog(
                        title = "Correct Cells",
                        message = "Correct cells: ${checkedCells!!.correctCells}\n" +
                                "Incorrect cells: ${checkedCells!!.incorrectCells}",
                        onConfirm = { checkSolutionDialog = false },
                        onDismiss = {
                            checkSolutionDialog = false
                            gameViewModel.solveSudoku()
                            timerViewModel.stopTimer()
                        },
                        confirmButtonText = "Continue solving",
                        dismissButtonText = "Give up"
                    )
            }
        }
    }
}

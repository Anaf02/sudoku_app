package com.sudoku

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sudoku.screens.HomeScreenContent
import com.sudoku.screens.GameScreenContent
import com.sudoku.viewModels.GameViewModel
import kotlinx.serialization.Serializable

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = HomeScreen
    ) {

        composable<HomeScreen> {
            HomeScreenContent(navController, onNavigateToGameScreen = {
                navController.navigate(GameScreen)
            })
        }
        composable<GameScreen> { backStackEntry ->
            val gameViewModel: GameViewModel = viewModel(backStackEntry)
            GameScreenContent(navController, gameViewModel)
        }
    }
}

@Serializable
object HomeScreen

@Serializable
object GameScreen
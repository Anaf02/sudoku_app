package com.sudoku

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sudoku.screens.HomeScreenContent
import com.sudoku.screens.GameScreenContent
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
        composable<GameScreen> {
            GameScreenContent(navController)
        }
    }
}

@Serializable
object HomeScreen

@Serializable
object GameScreen
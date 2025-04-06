package com.sudoku.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.sudoku.widgets.SimpleTopBar

@Composable
fun HomeScreenContent(navController: NavController, onNavigateToGameScreen: () -> Unit) {
    Scaffold(
        topBar = {
            SimpleTopBar(
                title = "Home",
                navController = navController,
                showBackArrow = false
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(onClick = {
                onNavigateToGameScreen()
            }) {
                Text("New Game")
            }
        }
    }
}
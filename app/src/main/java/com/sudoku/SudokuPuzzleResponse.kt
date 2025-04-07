package com.sudoku

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class SudokuPuzzleResponse(
    val index: Int,
    val puzzle: String
)

@Serializable
data class SudokuSolutionResponse(
    val index: Int,
    val solution: String
)

val httpClient = HttpClient(OkHttp) {
    install(ContentNegotiation) {
        json(Json {
            this.ignoreUnknownKeys = true
        })
    }
}

suspend fun fetchRandomSudoku(): SudokuPuzzleResponse {
    val randomIndex = (0..900799).random()
    val response: SudokuPuzzleResponse = httpClient
        .get("http://10.0.2.2:5134/sudoku/$randomIndex")
        .body()

    return response
}

suspend fun fetchSudokuSolution(index: Int?): SudokuSolutionResponse {
    val response: SudokuSolutionResponse = httpClient
        .get("http://10.0.2.2:5134/sudoku_solved/$index")
        .body()

    return response
}
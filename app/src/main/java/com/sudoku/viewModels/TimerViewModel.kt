package com.sudoku.viewModels

import androidx.compose.runtime.mutableIntStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

open class TimerViewModel : ViewModel() {
    var seconds = mutableIntStateOf(0)
        private set

    private var timerJob: Job? = null

    init {
        startTimer()
    }

    private fun startTimer() {
        timerJob = viewModelScope.launch {
            while (true) {
                delay(1000)
                seconds.intValue++
            }
        }
    }

    fun resetTimer() {
        stopTimer()
        seconds.intValue = 0
        startTimer()
    }

    fun stopTimer() {
        timerJob?.cancel()
        timerJob = null
    }
}
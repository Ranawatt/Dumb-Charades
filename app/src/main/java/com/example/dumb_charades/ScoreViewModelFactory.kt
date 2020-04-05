package com.example.dumb_charades

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.dumb_charades.screens.score.ScoreViewModel

class ScoreViewModelFactory(private val finalScore: Int) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ScoreViewModel::class.java)) {
            return ScoreViewModel(finalScore) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}
package com.example.dumb_charades.screens.score

import android.util.Log
import androidx.lifecycle.ViewModel

class ScoreViewModel(val finalScore : Int) : ViewModel() {
    // TODO: Implement the ViewModel
    // The final score
    var score = finalScore
    init {
        Log.i("ScoreViewModel", "Final score is $finalScore")
    }
}

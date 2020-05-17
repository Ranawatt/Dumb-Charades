package com.example.dumb_charades.screens.score

import android.content.Intent
import android.util.Log
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dumb_charades.R

class ScoreViewModel(finalScore : Int) : ViewModel() {
    // TODO: Implement the ViewModel
    private val  _score = MutableLiveData<Int>()
    val score: LiveData<Int>
    get() = _score

    private val _eventPlayAgain = MutableLiveData<Boolean>()
    val eventPlayAgain: LiveData<Boolean>
    get() = _eventPlayAgain
    init {
        // The final score
        _score.value = finalScore
        Log.i("ScoreViewModel", "Final score is $finalScore")
    }

    fun onPlayAgain(){
        _eventPlayAgain.value = true
    }

    fun onPlayAgainComplete(){
        _eventPlayAgain.value = false
    }

    fun onShare(){
        _score.value
    }

}

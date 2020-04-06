package com.example.dumb_charades.screens.score

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ScoreViewModel(val finalScore : Int) : ViewModel() {
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

    override fun onCleared() {
        super.onCleared()
    }
}

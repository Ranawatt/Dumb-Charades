package com.example.dumb_charades.screens.game

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment

import com.example.dumb_charades.R
import com.example.dumb_charades.databinding.GameFragmentBinding

class GameFragment : Fragment() {

    private lateinit var binding: GameFragmentBinding
    private lateinit var viewModel: GameViewModel

    companion object {
        fun newInstance() = GameFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate view and obtain an instance of the binding class
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.game_fragment,
            container,
            false
        )
        Log.i("GameFragment", "Called ViewModelProviders.of")
        viewModel = ViewModelProviders.of(this).get(GameViewModel::class.java)

        binding.correctButton.setOnClickListener { onCorrect() }
        binding.skipButton.setOnClickListener { onSkip() }
        binding.endGameButton.setOnClickListener { onEndGame() }
        updateScoreText()
        updateWordText()
        return binding.root

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.i("GameFragment", "Called ViewModelProviders.of")

        // TODO: Use the ViewModel
    }

    /** Methods for buttons presses **/

    private fun onSkip() {
        viewModel.onSkip()
        updateScoreText()
        updateWordText()
    }

    private fun onCorrect() {
        viewModel.onCorrect()
        updateScoreText()
        updateWordText()
    }


    /** Methods for updating the UI **/

    private fun updateWordText() {
        binding.wordText.text = viewModel.word
    }

    private fun updateScoreText() {
        binding.scoreText.text = viewModel.score.toString()
    }

    private fun onEndGame(){
        gameFinished()
    }

    private fun gameFinished() {
        Toast.makeText(activity, "Game has just finished", Toast.LENGTH_SHORT).show()
        val action = GameFragmentDirections.actionGameToScore()
        action.score = viewModel.score
        NavHostFragment.findNavController(this).navigate(action)
    }

}

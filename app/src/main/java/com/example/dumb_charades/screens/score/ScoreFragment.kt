package com.example.dumb_charades.screens.score

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController

import com.example.dumb_charades.R
import com.example.dumb_charades.ScoreViewModelFactory
import com.example.dumb_charades.databinding.ScoreFragmentBinding

class ScoreFragment() : Fragment() {

    companion object {
        fun newInstance() = ScoreFragment()
    }
    private lateinit var viewModel: ScoreViewModel
    private lateinit var viewModelFactory: ScoreViewModelFactory

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate view and obtain an instance of the binding class.
        val binding: ScoreFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.score_fragment,
            container, false
        )
        viewModelFactory =  ScoreViewModelFactory(ScoreFragmentArgs.fromBundle(arguments!!).score)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ScoreViewModel::class.java)
        binding.scoreViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        // Add observer for score
//        viewModel.score.observe(viewLifecycleOwner, Observer { newScore ->
//            binding.scoreText.text = newScore.toString()
//        })
//        binding.scoreText.text = viewModel.score.toString()

        // Navigates back to game when button is pressed
        viewModel.eventPlayAgain.observe(viewLifecycleOwner, Observer { playAgain ->
            if(playAgain)
                findNavController().navigate(ScoreFragmentDirections.actionRestart())
//                viewModel.onPlayAgainComplete()
        })
//        binding.playAgainButton.setOnClickListener{  viewModel.onPlayAgain()  }
        return binding.root
    }
}

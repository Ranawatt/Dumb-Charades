package com.example.dumb_charades

import android.os.Bundle
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.dumb_charades.databinding.MovieDestinationBinding

class MovieFragment : Fragment() {

    private lateinit var binding : MovieDestinationBinding
    private  var isLargeLayout = false
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = DataBindingUtil.inflate(inflater,R.layout.movie_destination, container,false)

        val constraintSet1 = ConstraintSet()
        val constraintSet2 = ConstraintSet()

        constraintSet2.clone(context,R.layout.movie_destination_large)
        val constraintLayout = binding.constraintLayout
        constraintSet1.clone(constraintLayout)
        val posterImageView = binding.imageViewPoster
        posterImageView.setOnClickListener {
            TransitionManager.beginDelayedTransition(constraintLayout)
            if (isLargeLayout) {
                constraintSet1.applyTo(constraintLayout)
            } else {
                constraintSet2.applyTo(constraintLayout)
            }
            isLargeLayout = !isLargeLayout
        }
        return binding.root
    }
}

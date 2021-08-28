 package com.example.dumb_charades

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintSet
import androidx.fragment.app.Fragment
import androidx.transition.TransitionManager
import kotlinx.android.synthetic.main.booking_ticket.*

 class TicketFragment : Fragment() {

    private lateinit var coverImage: View
    private lateinit var menuButton: AppCompatImageButton
    private lateinit var movieStatus: AppCompatTextView
    private lateinit var movieTitle: AppCompatTextView
    private lateinit var movieDescription: AppCompatTextView
    private lateinit var movieRating: AppCompatTextView
    private lateinit var descriptionButton: AppCompatImageButton



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.booking_ticket, container, false)
        addConstraintSetAnimation()
        return view
    }
    private fun addConstraintSetAnimation() {

        coverImage = this.cover
        menuButton = this.menu_button
        movieStatus = this.status
        movieTitle = this.movie_title
        movieDescription = this.desc
        movieRating = this.rating
        descriptionButton = this.description_button
        var isCoverView = false
        var isDescriptionView = false

        val initialConstraint = ConstraintSet()
        initialConstraint.clone(root)

        val coverConstraint = ConstraintSet()
        coverConstraint.clone(context, R.layout.cover_view)

        val descriptionConstraint = ConstraintSet()
        descriptionConstraint.clone(context, R.layout.description_view)

        val mapOfDays: Map<AppCompatTextView, AppCompatTextView> = mapOf(
            day_1 to date_1,
            day_2 to date_2,
            day_3 to date_3,
            day_4 to date_4,
            day_5 to date_5,
            day_6 to date_6,
            day_7 to date_7
        )

        val days: List<AppCompatTextView> = listOf(day_1, day_2, day_3, day_4, day_5, day_6, day_7)

        for (day in days) {
            day.setOnClickListener { selectDate(it as AppCompatTextView, descriptionConstraint) }
        }

        for (day in mapOfDays) {
            day.value.setOnClickListener { selectDate(day.key, descriptionConstraint) }
        }

        coverImage.setOnClickListener {
            if (!isCoverView) {
                TransitionManager.beginDelayedTransition(root)
                coverConstraint.applyTo(root)

                val anim = ValueAnimator()
                anim.setIntValues(Color.BLACK, Color.WHITE)
                anim.setEvaluator(ArgbEvaluator())
                anim.addUpdateListener {
                    menuButton.setColorFilter(it.animatedValue as Int)
                    movieStatus.setTextColor(it.animatedValue as Int)
                    movieTitle.setTextColor(it.animatedValue as Int)
                    movieDescription.setTextColor(it.animatedValue as Int)
                    movieRating.setTextColor(it.animatedValue as Int)
                    descriptionButton.setColorFilter(it.animatedValue as Int)
                }

                anim.duration = 300
                anim.start()
                isCoverView = true
                isDescriptionView = false
            }

        }

        menuButton.setOnClickListener {
            if (isCoverView) {
                TransitionManager.beginDelayedTransition(root)
                initialConstraint.applyTo(root)

                val anim = ValueAnimator()
                anim.setIntValues(Color.WHITE, Color.BLACK)
                anim.setEvaluator(ArgbEvaluator())
                anim.addUpdateListener {
                    menuButton.setColorFilter(it.animatedValue as Int)
                    movieStatus.setTextColor(it.animatedValue as Int)
                    movieTitle.setTextColor(it.animatedValue as Int)
                    movieDescription.setTextColor(it.animatedValue as Int)
                    movieRating.setTextColor(it.animatedValue as Int)
                    descriptionButton.setColorFilter(it.animatedValue as Int)
                }

                anim.duration = 300
                anim.start()
                isCoverView = false
                isDescriptionView = false
            } else if (isDescriptionView) {
                TransitionManager.beginDelayedTransition(root)
                initialConstraint.applyTo(root)

                isCoverView = false
                isDescriptionView = false
            }

        }

        descriptionButton.setOnClickListener {
            if (!isDescriptionView) {
                TransitionManager.beginDelayedTransition(root)
                descriptionConstraint.applyTo(root)

                if (isCoverView) {
                    val anim = ValueAnimator()
                    anim.setIntValues(Color.WHITE, Color.BLACK)
                    anim.setEvaluator(ArgbEvaluator())
                    anim.addUpdateListener {
                        menuButton.setColorFilter(it.animatedValue as Int)
                        movieStatus.setTextColor(it.animatedValue as Int)
                        movieTitle.setTextColor(it.animatedValue as Int)
                        movieDescription.setTextColor(it.animatedValue as Int)
                        movieRating.setTextColor(it.animatedValue as Int)
                        descriptionButton.setColorFilter(it.animatedValue as Int)
                    }

                    anim.duration = 300
                    anim.start()
                    isCoverView = false
                }


                isDescriptionView = true
            }
        }
    }

    private fun selectDate(day: AppCompatTextView, destinationConstraint: ConstraintSet) {
        destinationConstraint.connect(
            date_selector.id,
            ConstraintSet.START,
            day.id,
            ConstraintSet.START
        )
        destinationConstraint.connect(
            date_selector.id,
            ConstraintSet.END,
            day.id,
            ConstraintSet.END
        )
        TransitionManager.beginDelayedTransition(root)
        destinationConstraint.applyTo(root)
    }
}
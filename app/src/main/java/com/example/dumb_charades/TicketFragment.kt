package com.example.dumb_charades

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintSet
import androidx.transition.TransitionManager
import kotlinx.android.synthetic.main.booking_ticket.*
import kotlinx.android.synthetic.main.cover_view.*
import kotlinx.android.synthetic.main.description_view.*


class TicketFragment : Fragment() {

    var isCoverView = false
    var isDescriptionView = false

    private val initialConstraint = ConstraintSet()
    private val coverConstraint = ConstraintSet()
    private val descriptionConstraint = ConstraintSet()
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
        val initialConstraint = ConstraintSet()
        val coverConstraint = ConstraintSet()
        val descriptionConstraint = ConstraintSet()
        initialConstraint.clone(root)
        coverConstraint.clone(context, R.layout.cover_view)
        descriptionConstraint.clone(context, R.layout.description_view)

        val mapOfDays: Map<AppCompatTextView, AppCompatTextView> = mapOf(
            day_1 to date_1, day_2 to date_2,
            day_3 to date_3, day_4 to date_4,
            day_5 to date_5, day_6 to date_6,
            day_7 to date_7
        )

        val days: List<AppCompatTextView> = listOf(day_1, day_2,
            day_3, day_4, day_5, day_6, day_7)

        for (day in days) {
            day.setOnClickListener { selectDate(it as AppCompatTextView, descriptionConstraint) }
        }

        for (day in mapOfDays) {
            day.value.setOnClickListener { selectDate(day.key, descriptionConstraint) }
        }

        cover.setOnClickListener {
            if (!isCoverView) {
                TransitionManager.beginDelayedTransition(root)
                coverConstraint.applyTo(root)

                val anim = ValueAnimator()
                anim.setIntValues(Color.BLACK, Color.WHITE)
                anim.setEvaluator(ArgbEvaluator())
                anim.addUpdateListener {
                    menu_button.setColorFilter(it.animatedValue as Int)
                    status.setTextColor(it.animatedValue as Int)
                    movie_title.setTextColor(it.animatedValue as Int)
                    desc.setTextColor(it.animatedValue as Int)
                    rating.setTextColor(it.animatedValue as Int)
                    description_button.setColorFilter(it.animatedValue as Int)
                }

                anim.duration = 300
                anim.start()
                isCoverView = true
                isDescriptionView = false
            }

        }

        menu_button.setOnClickListener {
            if (isCoverView) {
                TransitionManager.beginDelayedTransition(root)
                initialConstraint.applyTo(cover_root)

                val anim = ValueAnimator()
                anim.setIntValues(Color.WHITE, Color.BLACK)
                anim.setEvaluator(ArgbEvaluator())
                anim.addUpdateListener {
                    menu_button.setColorFilter(it.animatedValue as Int)
                    status.setTextColor(it.animatedValue as Int)
                    movie_title.setTextColor(it.animatedValue as Int)
                    desc.setTextColor(it.animatedValue as Int)
                    rating.setTextColor(it.animatedValue as Int)
                    description_button.setColorFilter(it.animatedValue as Int)
                }

                anim.duration = 300
                anim.start()
                isCoverView = false
                isDescriptionView = false
            } else if (isDescriptionView) {
                TransitionManager.beginDelayedTransition(root)
                initialConstraint.applyTo(cover_root)

                isCoverView = false
                isDescriptionView = false
            }

        }

        description_button.setOnClickListener {
            if (!isDescriptionView) {
                TransitionManager.beginDelayedTransition(root)
                descriptionConstraint.applyTo(root)

                if (isCoverView) {
                    val anim = ValueAnimator()
                    anim.setIntValues(Color.WHITE, Color.BLACK)
                    anim.setEvaluator(ArgbEvaluator())
                    anim.addUpdateListener {
                        menu_button.setColorFilter(it.animatedValue as Int)
                        status.setTextColor(it.animatedValue as Int)
                        movie_title.setTextColor(it.animatedValue as Int)
                        desc.setTextColor(it.animatedValue as Int)
                        rating.setTextColor(it.animatedValue as Int)
                        description_button.setColorFilter(it.animatedValue as Int)
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
        destinationConstraint.applyTo(cover_root)
    }


}
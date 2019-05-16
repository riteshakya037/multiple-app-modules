package com.riteshakya.ui.helpers

import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.view.animation.DecelerateInterpolator

fun animateChange(
        startColor: Int, endColor: Int, animateChanges: Boolean = true, body: (value: Int) -> Unit
) {
    if (animateChanges) {
        val animator = ObjectAnimator.ofInt(startColor, endColor)
        animator.duration = 1300L
        animator.setEvaluator(ArgbEvaluator())
        animator.interpolator = DecelerateInterpolator(2f)
        animator.addUpdateListener { animation ->
            val animatedValue = animation.animatedValue as Int
            body(animatedValue)
        }
        animator.start()
    } else {
        body(endColor)
    }
}

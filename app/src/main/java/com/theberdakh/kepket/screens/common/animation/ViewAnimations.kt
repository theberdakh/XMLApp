package com.theberdakh.kepket.screens.common.animation

import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.BounceInterpolator
import android.view.animation.RotateAnimation
import android.view.animation.ScaleAnimation
import android.view.animation.TranslateAnimation
import android.widget.ImageView

object ViewAnimations {

    fun View.withFadeIn(duration: Int = 300) {
        this.visibility = View.VISIBLE
        val fadeIn = AlphaAnimation(0.0f, 1.0f).apply {
            this.duration = duration.toLong()
        }
        this.startAnimation(fadeIn)
    }

    fun View.withFadeOut(duration: Int = 300) {
        val fadeOut = AlphaAnimation(1.0f, 0.0f).apply {
            this.duration = duration.toLong()
        }
        this.startAnimation(fadeOut)
        this.visibility = View.GONE
    }

    fun View.withZoomIn(duration: Int = 300) {
        val scaleAnimation = ScaleAnimation(
            0.0f, 1.0f, // Start and end values for the X axis scaling
            0.0f, 1.0f, // Start and end values for the Y axis scaling
            Animation.RELATIVE_TO_SELF, 0.5f, // Pivot point of X scaling
            Animation.RELATIVE_TO_SELF, 0.5f  // Pivot point of Y scaling
        ).apply {
            this.duration = duration.toLong()
        }
        this.startAnimation(scaleAnimation)
    }

    fun View.withZoomOut(duration: Int = 300) {
        val scaleAnimation = ScaleAnimation(
            1.0f, 0.0f,
            1.0f, 0.0f,
            Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f
        ).apply {
            this.duration = duration.toLong()
        }
        this.startAnimation(scaleAnimation)
    }


    fun View.withSlideUp(duration: Int = 300) {
        val slideUp = TranslateAnimation(
            0.0f, 0.0f,  // Start and end values for the X axis (no movement)
            this.height.toFloat(), 0.0f // Start and end values for the Y axis
        ).apply {
            this.duration = duration.toLong()
        }
        this.startAnimation(slideUp)
        this.visibility = View.VISIBLE
    }

    fun View.withSlideDown(duration: Int = 300) {
        val slideDown = TranslateAnimation(
            0.0f, 0.0f,  // Start and end values for the X axis (no movement)
            0.0f, this.height.toFloat() // Start and end values for the Y axis
        ).apply {
            this.duration = duration.toLong()
        }
        this.startAnimation(slideDown)
        this.visibility = View.GONE
    }

    fun View.withRotate(duration: Int = 300, fromDegrees: Float = 0.0f, toDegrees: Float = 360.0f) {
        val rotateAnimation = RotateAnimation(
            fromDegrees, toDegrees, // Start and end values for the rotation angle
            Animation.RELATIVE_TO_SELF, 0.5f, // Pivot point of X (center)
            Animation.RELATIVE_TO_SELF, 0.5f  // Pivot point of Y (center)
        ).apply {
            this.duration = duration.toLong()
            this.fillAfter = true // Keeps the view in the rotated position
        }
        this.startAnimation(rotateAnimation)
    }

    fun View.withBounce(duration: Int = 300) {
        val bounceAnimation = ScaleAnimation(
            1.0f, 1.2f, // Start and end values for the X axis scaling
            1.0f, 1.2f, // Start and end values for the Y axis scaling
            Animation.RELATIVE_TO_SELF, 0.5f, // Pivot point of X scaling
            Animation.RELATIVE_TO_SELF, 0.5f  // Pivot point of Y scaling
        ).apply {
            this.duration = duration.toLong()
            this.interpolator = BounceInterpolator()
            this.repeatMode = Animation.REVERSE
            this.repeatCount = 1
        }
        this.startAnimation(bounceAnimation)
    }

    fun ImageView.withCrossFadeIconChange(newIconResId: Int, duration: Int = 300) {
        this.animate().alpha(0f).setDuration(duration.toLong()).withEndAction{
            this.setImageResource(newIconResId)
            this.animate().alpha(1f).setDuration(duration.toLong()).start()
        }.start()
    }
}

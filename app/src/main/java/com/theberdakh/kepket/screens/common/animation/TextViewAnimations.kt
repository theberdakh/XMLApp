package com.theberdakh.kepket.screens.common.animation

import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.view.animation.BounceInterpolator
import android.view.animation.RotateAnimation
import android.view.animation.ScaleAnimation
import android.view.animation.TranslateAnimation
import android.widget.TextView

object TextViewAnimations {

    fun TextView.withScaleAnimation(newText: String) {
        this.text = newText
        val scaleAnimation = ScaleAnimation(
            1.0f, 1.2f,
            1.0f, 1.2f,
            Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f
        )
        scaleAnimation.duration = 300
        scaleAnimation.repeatCount = 1
        scaleAnimation.repeatMode =  Animation.REVERSE
        this.startAnimation(scaleAnimation)
    }

    fun TextView.withFadeIn(newText: String) {
        this.text = newText
        val fadeIn = AlphaAnimation(0.0f, 1.0f)
        fadeIn.duration = 500
        this.startAnimation(fadeIn)
    }

    fun TextView.withFadeOut(newText: String) {
        this.text = newText
        val fadeOut = AlphaAnimation(1.0f, 0.0f)
        fadeOut.duration = 500
        this.startAnimation(fadeOut)
    }

    fun TextView.withScaleUp(newText: String) {
        this.text = newText
        val scaleUp = ScaleAnimation(
            1.0f, 1.5f, 1.0f, 1.5f,
            Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f
        )
        scaleUp.duration = 300
        this.startAnimation(scaleUp)
    }

    fun TextView.withScaleDown(newText: String) {
        this.text = newText
        val scaleDown = ScaleAnimation(
            1.5f, 1.0f, 1.5f, 1.0f,
            Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f
        )
        scaleDown.duration = 300
        this.startAnimation(scaleDown)
    }

    fun TextView.withTranslate(newText: String) {
        this.text = newText
        val translate = TranslateAnimation(
            Animation.RELATIVE_TO_SELF, 0.0f,
            Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.0f,
            Animation.RELATIVE_TO_SELF, 0.0f
        )
        translate.duration = 300
        this.startAnimation(translate)
    }


    fun TextView.withRotate(newText: String) {
        this.text = newText
        val rotate = RotateAnimation(
            0.0f, 360.0f,
            Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f
        )
        rotate.duration = 500
        this.startAnimation(rotate)
    }

    fun TextView.withBounce(newText: String) {
        this.text = newText
        val bounce = ScaleAnimation(
            1.0f, 1.2f,
            1.0f, 1.2f,
            Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f
        )
        bounce.duration = 500
        bounce.interpolator = BounceInterpolator()
        this.startAnimation(bounce)
    }

    fun TextView.withBlink(newText: String) {
        this.text = newText
        val blink = AlphaAnimation(0.0f, 1.0f)
        blink.duration = 300 // time for each blink
        blink.repeatCount = 5 // number of times the animation will repeat
        blink.repeatMode = Animation.REVERSE
        this.startAnimation(blink)
    }


    fun TextView.withCombinedAnimation(newText: String) {
        this.text = newText
        val animationSet = AnimationSet(true)
        val fadeIn = AlphaAnimation(0.0f, 1.0f)
        fadeIn.duration = 500
        val scaleUp = ScaleAnimation(
            1.0f, 1.5f, 1.0f, 1.5f,
            Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f
        )
        scaleUp.duration = 300

        animationSet.addAnimation(fadeIn)
        animationSet.addAnimation(scaleUp)

        this.startAnimation(animationSet)
    }

    fun TextView.withSlideUp(newText: String) {
        this.text = newText
        val slideUp = TranslateAnimation(
            0f, 0f, this.height.toFloat(), 0f
        )
        slideUp.duration = 500
        this.startAnimation(slideUp)
    }

    fun TextView.withSlideDown(newText: String) {
        this.text = newText
        val slideDown = TranslateAnimation(
            0f, 0f, 0f, this.height.toFloat()
        )
        slideDown.duration = 500
        this.startAnimation(slideDown)
    }

    fun TextView.withFadeAndScale(newText: String) {
        val fadeOut = AlphaAnimation(1.0f, 0.0f)
        fadeOut.duration = 100

        val scaleUp = ScaleAnimation(
            1.0f, 1.2f,
            1.0f, 1.2f,
            Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f
        )
        scaleUp.duration = 100

        val fadeIn = AlphaAnimation(0.0f, 1.0f)
        fadeIn.duration = 100

        val animationSet = AnimationSet(true)
        animationSet.addAnimation(fadeOut)
        animationSet.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {}
            override fun onAnimationEnd(animation: Animation) {
                this@withFadeAndScale.text = newText
                this@withFadeAndScale.startAnimation(fadeIn)
            }
            override fun onAnimationRepeat(animation: Animation) {}
        })
        this.startAnimation(animationSet)
    }

}

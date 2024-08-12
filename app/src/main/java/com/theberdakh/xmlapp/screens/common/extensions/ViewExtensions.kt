package com.theberdakh.xmlapp.screens.common.extensions

import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorInt
import com.theberdakh.xmlapp.BuildConfig


object ViewExtensions {
    private const val STROKE_WIDTH_DEFAULT = 2

    /**
     * Set [View.GONE]*/
    fun View.gone()  {
        this.visibility = View.GONE
    }

    /**
     * Set [View.VISIBLE]*/
    fun View.visible() {
        this.visibility = View.VISIBLE
    }

    /**
     * Set [View.INVISIBLE]*/
    fun View.invisible() {
        this.visibility = View.INVISIBLE
    }


    fun View.showBorder(color: Int) {
        this.showBorder(STROKE_WIDTH_DEFAULT, color)
    }

    fun View.showBorder(width: Int, color: Int) {
        val background: GradientDrawable = when (this.background) {
            null -> {
                GradientDrawable()
            }
            is GradientDrawable -> {
                this.background as GradientDrawable
            }
            else -> {
                val msg = "setBackgroundBorder() called on View that has background other than GradientDrawable"
                if (BuildConfig.DEBUG) {
                    throw RuntimeException(msg)
                } else {
                    //Log here
                }
                return
            }
        }
        background.setStroke(width, color)
        this.background = background
    }

    fun TextView.strikethrough() {
        this.paintFlags = this.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
    }

    fun ImageView.colorize(@ColorInt color: Int) {
        val porterDuffColorFilter = PorterDuffColorFilter(
            color,
            PorterDuff.Mode.SRC_IN
        )
        this.colorFilter = porterDuffColorFilter
    }

    fun Drawable.colorize(@ColorInt color: Int) {
        val porterDuffColorFilter = PorterDuffColorFilter(
            color,
            PorterDuff.Mode.SRC_IN
        )
        this.colorFilter = porterDuffColorFilter
    }

    fun EditText.getString() = this.text.toString()
    fun EditText.isEmpty() = this.getString().trim().isEmpty()


}

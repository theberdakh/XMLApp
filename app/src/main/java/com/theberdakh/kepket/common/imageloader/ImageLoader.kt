package com.theberdakh.kepket.common.imageloader

import android.widget.ImageView

interface ImageLoader {

    interface OnCompletionListener {
        fun onLoadCompleted()
        fun onLoadFailed()
    }

    fun loadImage(uri: String, target: ImageView)
    fun loadImage(uri: String, target: ImageView, inProgressDrawableId: Int, errorDrawableId: Int)
    fun loadImage(uri: String, target: ImageView, listener: OnCompletionListener)
    fun loadImageCircular(
        uri: String,
        target: ImageView,
        inProgressDrawableId: Int,
        errorDrawableId: Int
    )
}


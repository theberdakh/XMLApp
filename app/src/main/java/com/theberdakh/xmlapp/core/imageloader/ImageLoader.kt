package com.theberdakh.xmlapp.core.imageloader

import android.app.Activity
import android.graphics.drawable.Drawable
import android.net.Uri
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.theberdakh.xmlapp.core.logs.Logger

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


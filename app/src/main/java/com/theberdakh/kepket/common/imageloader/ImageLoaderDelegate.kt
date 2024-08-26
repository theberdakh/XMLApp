package com.theberdakh.kepket.common.imageloader

import android.app.Activity

class ImageLoaderDelegate(private val activity: Activity): ImageLoader by ImageLoaderImpl(activity)

package com.theberdakh.xmlapp.core.imageloader

import android.app.Activity

class ImageLoaderDelegate(private val activity: Activity): ImageLoader by ImageLoaderImpl(activity)

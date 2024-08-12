package com.theberdakh.xmlapp.core.imageloader

import android.content.ContentResolver
import android.content.res.Resources
import android.net.Uri
import androidx.annotation.AnyRes

class ResourcesToUriConverter(private val resources: Resources) {

    fun getUriForResource(@AnyRes resId: Int): Uri {
        return (Uri.Builder())
            .scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
            .authority(resources.getResourcePackageName(resId))
            .appendPath(resources.getResourceTypeName(resId))
            .appendPath(resources.getResourceEntryName(resId))
            .build()
    }

}

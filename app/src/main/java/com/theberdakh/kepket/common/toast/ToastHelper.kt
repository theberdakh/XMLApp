package com.theberdakh.kepket.common.toast

import android.content.Context
import android.widget.Toast

interface ToastHelper {
    fun showToast(text: String, duration: Int = Toast.LENGTH_SHORT)
}

class ToastHelperImpl(private val context: Context): ToastHelper {
    override fun showToast(text: String, duration: Int) {
        Toast.makeText(context, text, duration).show()
    }
}

class ToastHelperDelegate(context: Context): ToastHelper by ToastHelperImpl(context)

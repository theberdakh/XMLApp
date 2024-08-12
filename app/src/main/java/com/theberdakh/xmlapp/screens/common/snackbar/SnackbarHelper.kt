package com.theberdakh.xmlapp.screens.common.snackbar

import android.view.View
import com.google.android.material.snackbar.Snackbar

interface SnackbarHelper {

    fun showSnackbar(text: String, duration: Int = Snackbar.LENGTH_SHORT)
}

class SnackbarHelperImpl(private val view: View): SnackbarHelper {
    override fun showSnackbar(text: String, duration: Int){
        Snackbar.make(view, text, duration).show()
    }
}

class SnackbarHelperDelegate(private val view: View): SnackbarHelper by SnackbarHelperImpl(view)

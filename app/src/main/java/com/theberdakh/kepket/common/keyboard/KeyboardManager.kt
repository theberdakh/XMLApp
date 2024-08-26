package com.theberdakh.kepket.common.keyboard

import android.content.Context
import android.view.View
import android.view.ViewTreeObserver
import android.view.inputmethod.InputMethodManager


object KeyboardManager {

    /**
     * Sometimes the keyboard does not go away if the window loses focus or is backgrounded.
     * Solution from this [article](https://developer.squareup.com/blog/showing-the-android-keyboard-reliably/)
     * */
    fun focusAndShowKeyboard(view: View) {
        /**
         * This is to be called when the window already has focus.
         */
        fun View.showTheKeyboardNow() {
            if (this.isFocused) {
                post {
                    // We still post the call, just in case we are being notified of the windows focus
                    // but InputMethodManager didn't get properly setup yet.
                    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
                }
            }
        }

        view.requestFocus()
        if (view.hasWindowFocus()) {
            // No need to wait for the window to get focus.
            view.showTheKeyboardNow()
        } else {
            // We need to wait until the window gets focus.
            view.viewTreeObserver.addOnWindowFocusChangeListener(
                object : ViewTreeObserver.OnWindowFocusChangeListener {
                    override fun onWindowFocusChanged(hasFocus: Boolean) {
                        // This notification will arrive just before the InputMethodManager gets set up.
                        if (hasFocus) {
                            view.showTheKeyboardNow()
                            // It’s very important to remove this listener once we are done.
                            view.viewTreeObserver.removeOnWindowFocusChangeListener(this)
                        }
                    }
                })
        }
    }

    fun showKeyboard(view: View): Boolean {
        try {
            val inputManager = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            return inputManager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
        } catch (e: Exception) {
            //show exception
        }
        return false
    }

    fun hideKeyboard(view: View) {
        try {
            val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            if (!imm.isActive) {
                return
            }
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        } catch (e: java.lang.Exception) {
           //show exception
        }
    }
}

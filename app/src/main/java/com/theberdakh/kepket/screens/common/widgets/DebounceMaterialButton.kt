package com.theberdakh.kepket.screens.common.widgets

import android.content.Context
import android.util.AttributeSet
import com.google.android.material.button.MaterialButton

class DebounceMaterialButton: MaterialButton {

    constructor(context: Context): super(context){}
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {}
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {}

    private var lastPerformedClickMillis = 0L

    /**
     * Set enabled
     *
     * @param enabled
     */
    override fun setEnabled(enabled: Boolean) {
        alpha = if (enabled) 1f else ALPHA_DISABLED
        super.setEnabled(enabled)
    }

    override fun performClick(): Boolean {
        return if (System.currentTimeMillis() > lastPerformedClickMillis + DEBOUNCE_TIMEOUT_MS) {
            val clickResult = super.performClick()
            lastPerformedClickMillis = System.currentTimeMillis()
            clickResult
        } else {
            false
        }
    }

    companion object {
        private const val DEBOUNCE_TIMEOUT_MS = 200L
        private const val ALPHA_DISABLED = 0.6f
    }

}

package com.theberdakh.kepket.common.dialog

import androidx.fragment.app.FragmentActivity

class MaterialDialogDelegate(private val fragmentActivity: FragmentActivity) :
    MaterialDialogHelper by MaterialDialogHelperImpl(fragmentActivity)

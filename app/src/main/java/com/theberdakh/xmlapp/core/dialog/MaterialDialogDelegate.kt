package com.theberdakh.xmlapp.core.dialog

import androidx.fragment.app.FragmentActivity

class MaterialDialogDelegate(private val fragmentActivity: FragmentActivity) :
    MaterialDialogHelper by MaterialDialogHelperImpl(fragmentActivity)

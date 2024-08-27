package com.theberdakh.kepket.common.dialog

import android.content.DialogInterface

interface MaterialDialogHelper {
    fun openDataPickerDialog(positiveButtonClick: (String) -> Unit)
    fun openTimePickerDialog(positiveButtonClick: (Int, Int) -> Unit)
    fun openAlertDialog(title: String, message: String, positiveButtonText: String, positiveButtonClick: (DialogInterface) -> Unit,
                        negativeButtonText: String, negativeButtonClick: (DialogInterface) -> Unit)
}


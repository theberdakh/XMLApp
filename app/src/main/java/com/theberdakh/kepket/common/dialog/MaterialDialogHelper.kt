package com.theberdakh.kepket.common.dialog

interface MaterialDialogHelper {
    fun openDataPickerDialog(positiveButtonClick: (String) -> Unit)
    fun openTimePickerDialog(positiveButtonClick: (Int, Int) -> Unit)
}


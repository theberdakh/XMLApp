package com.theberdakh.xmlapp.core.dialog

import android.app.Activity
import androidx.fragment.app.FragmentActivity
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone

interface MaterialDialogHelper {
    fun openDataPickerDialog(positiveButtonClick: (String) -> Unit)
    fun openTimePickerDialog(positiveButtonClick: (Int, Int) -> Unit)
}


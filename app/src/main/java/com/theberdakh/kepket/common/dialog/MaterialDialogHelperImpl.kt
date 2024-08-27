package com.theberdakh.kepket.common.dialog

import android.content.DialogInterface
import androidx.fragment.app.FragmentActivity
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone

class MaterialDialogHelperImpl(private val fragmentActivity: FragmentActivity): MaterialDialogHelper {

    override fun openAlertDialog(title: String, message: String, positiveButtonText: String, positiveButtonClick: (DialogInterface) -> Unit,
                        negativeButtonText: String, negativeButtonClick: (DialogInterface) -> Unit) {
        val dialog = MaterialAlertDialogBuilder(fragmentActivity)
        dialog.setMessage(message)
        dialog.setTitle(title)
        dialog.setPositiveButton(positiveButtonText) { dialogInterface, which ->
            positiveButtonClick.invoke(dialogInterface)
        }
        dialog.setNegativeButton(negativeButtonText){dialogInterface, which ->
            negativeButtonClick.invoke(dialogInterface)
        }
        dialog.show()
    }

    override fun openDataPickerDialog(positiveButtonClick: (String) -> Unit){
        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Select date")
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
            .setInputMode(MaterialDatePicker.INPUT_MODE_CALENDAR)
            .build()

        datePicker.show(fragmentActivity.supportFragmentManager, "tag")

        datePicker.addOnPositiveButtonClickListener { selection ->
            positiveButtonClick.invoke(
                convertTimeToDate(selection)
            )
        }
    }

    override fun openTimePickerDialog(positiveButtonClick: (Int, Int) -> Unit){
        val timePicker = MaterialTimePicker.Builder()
            .setTimeFormat(TimeFormat.CLOCK_24H)
            .setTitleText("Select time")
            .setInputMode(MaterialTimePicker.INPUT_MODE_KEYBOARD)
            .build()

        timePicker.show(fragmentActivity.supportFragmentManager, "tag")

        timePicker.addOnPositiveButtonClickListener {
            positiveButtonClick.invoke(timePicker.hour, timePicker.minute)
        }

    }

    private fun convertTimeToDate(time: Long): String {
        val utc = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
        utc.timeInMillis = time
        val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return format.format(utc.time)
    }
}

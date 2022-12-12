package com.example.superjet.ui.presentation.booking

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.*

class DateTimeViewModel() : ViewModel() {

    private val _time = MutableLiveData("")
    var time: LiveData<String> = _time

    private val _timeMS = MutableLiveData("")
    var timeMS: LiveData<String> = _timeMS


    fun selectDateTime(context: Context) {
        var time = ""
        val currentDateTime = Calendar.getInstance()
        val startYear = currentDateTime.get(Calendar.YEAR)
        val startMonth = currentDateTime.get(Calendar.MONTH)
        val startDay = currentDateTime.get(Calendar.DAY_OF_MONTH)

        DatePickerDialog(context, { _, year, month, day ->
            val pickedDateTime = Calendar.getInstance()
            pickedDateTime.set(year, month, day)
            updateDateTimeMS(pickedDateTime.timeInMillis.toString())
            val monthStr: String
            if ((month + 1).toString().length == 1) {
                monthStr = "0${month + 1}"
            } else {
                monthStr = month.toString()
            }
            time = "$day - $monthStr - $year "
            updateDateTime(time)
        }, startYear, startMonth, startDay).show()
    }

    private fun updateDateTime(dateTime: String) {
        _time.value = dateTime
    }

    private fun updateDateTimeMS(dateTimeMS: String) {
        _timeMS.value = dateTimeMS
    }

}
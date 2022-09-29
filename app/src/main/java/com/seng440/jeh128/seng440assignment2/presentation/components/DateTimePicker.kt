package com.seng440.jeh128.seng440assignment2.presentation.components

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.icu.util.Calendar
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.seng440.jeh128.seng440assignment2.R
import java.time.LocalDateTime
import java.util.*

@Composable
fun DateTimePicker (
    onDateSelected: (LocalDateTime) -> Unit
) {
    val context = LocalContext.current

    val year: Int
    val month: Int
    val day: Int

    val hour: Int
    val minute: Int

    val calendar = Calendar.getInstance()

    year = calendar.get(Calendar.YEAR)
    month = calendar.get(Calendar.MONTH)
    day = calendar.get(Calendar.DAY_OF_MONTH)

    hour = calendar.get(Calendar.HOUR_OF_DAY)
    minute = calendar.get(Calendar.MINUTE)

    calendar.time = Date()

    val datePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, newyear: Int, newMonth: Int, dayOfMonth: Int ->
            onDateSelected(LocalDateTime.of(newyear, newMonth, dayOfMonth, hour, minute, 0))
        },
        year,
        month,
        day
    )

    val timePickerDialog = TimePickerDialog(
        context,
        { _: TimePicker, newHour, newMinute ->
            onDateSelected(LocalDateTime.of(year, month, day, newHour, newMinute, 0))
        }, hour, minute, false
    )

    Row {
        Button(
            onClick = {
                datePickerDialog.show()
            },
        ) {
            Text(text = stringResource(id = R.string.select_date))
        }
        Button(
            onClick = {
                timePickerDialog.show()
            },
        ) {
            Text(text = stringResource(id = R.string.select_time))
        }
    }
}
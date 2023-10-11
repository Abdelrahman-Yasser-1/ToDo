package com.example.to_do.Dialogs;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;

import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MyDateTimePicker {

    public static void showDatePickerDialog(Activity activity, TextInputLayout textInputLayout_date) {
        // create calender
        Calendar calendar = Calendar.getInstance();
        // create date picker dialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                activity,
                (view1, year, month, dayOfMonth) -> {
                    // set selected date on text view
                    textInputLayout_date.getEditText().setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);

        // show dialog
        datePickerDialog.show();
    }

    public static void showTimePickerDialog(Activity activit, TextInputLayout textInputLayout_time) {
        // create time picker dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(
                activit,
                (view1, hourOfDay, minute) -> {
                    // initialize calender
                    Calendar calendar = Calendar.getInstance();
                    // set hour and minute
                    calendar.set(0, 0, 0, hourOfDay, minute);
                    // set selected time on text view
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm a");
                    textInputLayout_time.getEditText().setText(simpleDateFormat.format(calendar.getTime()));

                }, 12, 0, false
        );
        // display previous selected time
        timePickerDialog.updateTime(12, 0);

        // show dialog
        timePickerDialog.show();
    }
}

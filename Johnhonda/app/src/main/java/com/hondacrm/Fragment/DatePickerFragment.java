package com.hondacrm.Fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.DatePicker;

import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment implements
        DatePickerDialog.OnDateSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog;
        datePickerDialog = new DatePickerDialog(getActivity(), this, year, month, day);
        // datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        return datePickerDialog;
    }

    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        // Do something with the date chosen by the user

        if ((monthOfYear + 1) > 9) {
            if ((dayOfMonth) > 9) {
                FilterVehicle.selectFromDate.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
            } else {
                FilterVehicle.selectFromDate.setText(year + "-" + (monthOfYear + 1) + "-" + "0" + dayOfMonth);
            }
        } else {
            if ((dayOfMonth) > 9) {
                FilterVehicle.selectFromDate.setText(year + "-" + "0" + (monthOfYear + 1) + "-" + dayOfMonth);
            } else {
                FilterVehicle.selectFromDate.setText(year + "-" + "0" + (monthOfYear + 1) + "-" + "0" + dayOfMonth);
            }
        }

    }

}


package com.hondacrm.Fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.DatePicker;

import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class ToDatePickerFragment extends DialogFragment implements
        DatePickerDialog.OnDateSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker

        String getfromdate = FilterVehicle.selectFromDate.getText().toString().trim();
        String getfrom[] = getfromdate.split("-");
        int year, month, day;
        year = Integer.parseInt(getfrom[0]);
        month = Integer.parseInt(getfrom[1]);
        day = Integer.parseInt(getfrom[2]);
        final Calendar c = Calendar.getInstance();
        c.set(year, month - 1, day + 1);
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), this, year, month - 1, day);
        datePickerDialog.getDatePicker().setMinDate(c.getTimeInMillis());
        return datePickerDialog;

    }

    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        // Do something with the date chosen by the user
        if ((monthOfYear + 1) > 9) {
            if ((dayOfMonth) > 9) {
                FilterVehicle.selectToDate.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
            } else {
                FilterVehicle.selectToDate.setText(year + "-" + (monthOfYear + 1) + "-" + "0" + dayOfMonth);
            }
        } else {
            if ((dayOfMonth) > 9) {
                FilterVehicle.selectToDate.setText(year + "-" + "0" + (monthOfYear + 1) + "-" + dayOfMonth);
            } else {
                FilterVehicle.selectToDate.setText(year + "-" + "0" + (monthOfYear + 1) + "-" + "0" + dayOfMonth);
            }
        }

    }

}
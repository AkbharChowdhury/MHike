package com.mhike.m_hike.classes;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.mhike.m_hike.AddHikeActivity;
import com.mhike.m_hike.AddObservationActivity;

import java.time.LocalDate;


public class DatePickerFragment extends DialogFragment implements
        DatePickerDialog.OnDateSetListener {

    private final boolean disablePastDates;
    private boolean isHikeForm;


    public DatePickerFragment(boolean disablePastDates, boolean isHikeForm) {
        this.disablePastDates = disablePastDates;
        this.isHikeForm = isHikeForm;

    }


    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {

        LocalDate date = LocalDate.of(year, ++month, day);

        if (isHikeForm) {
            ((AddHikeActivity) getActivity()).updateDate(date);
            return;
        }
        ((AddObservationActivity) getActivity()).updateDate(date);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        LocalDate d = LocalDate.now();
        int year = d.getYear();
        int month = d.getMonthValue();
        int day = d.getDayOfMonth();


        DatePickerDialog datePicker = new DatePickerDialog(getActivity(), this, year, --month, day);
        if (!disablePastDates) {
            return datePicker;
        }
        // disable past dates
        Long currentTimeMillis = System.currentTimeMillis() - 1000;
        datePicker.getDatePicker().setMinDate(currentTimeMillis);
        return datePicker;

    }

}
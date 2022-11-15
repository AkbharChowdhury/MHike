package com.mhike.m_hike.utilities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.mhike.m_hike.activities.AddHikeActivity;
import com.mhike.m_hike.activities.AddObservationActivity;
import com.mhike.m_hike.activities.EditHikeActivity;
import com.mhike.m_hike.classes.enums.ActivityForm;
import com.mhike.m_hike.utilities.Helper;

import java.time.LocalDate;
import java.util.Calendar;


public class DatePickerFragment extends DialogFragment implements
        DatePickerDialog.OnDateSetListener {

    private final boolean disablePastDates;
    private final ActivityForm ACTIVITY_FORM;
    private long minHikeDate = 0;




    public DatePickerFragment(boolean disablePastDates, ActivityForm activityForm) {
        this.disablePastDates = disablePastDates;
        ACTIVITY_FORM = activityForm;
    }

    public DatePickerFragment(boolean disablePastDates, ActivityForm activityForm, long minHikeDate) {
        this.disablePastDates = disablePastDates;
        ACTIVITY_FORM = activityForm;
        this.minHikeDate = minHikeDate;

    }


    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {

        LocalDate date = LocalDate.of(year, ++month, day);
        updateDateOnForm(date);


    }

    private void updateDateOnForm(LocalDate date) {
        switch (ACTIVITY_FORM) {
            case ADD_HIKE:
                ((AddHikeActivity) getActivity()).updateDate(date);
                break;
            case EDIT_HIKE:
                ((EditHikeActivity) getActivity()).updateDate(date);
                break;

            case ADD_OBSERVATION:
                ((AddObservationActivity) getActivity()).updateDate(date);
                break;
            default:
                Helper.showActivityFormErrorMessage();

        }
    }



    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        long now = System.currentTimeMillis() - 1000;
        LocalDate d = LocalDate.now();
        int year = d.getYear();
        int month = d.getMonthValue();
        int day = d.getDayOfMonth();


        DatePickerDialog datePicker = new DatePickerDialog(getActivity(), this, year, --month, day);
        if (disablePastDates) {
            // disable past dates
            datePicker.getDatePicker().setMinDate(now);
            return datePicker;

        }
        // disable future dates and set min observation date to hike date
        if (minHikeDate != 0){
         // set min date

        }


        datePicker.getDatePicker().setMaxDate(now);

        return datePicker;

    }

}
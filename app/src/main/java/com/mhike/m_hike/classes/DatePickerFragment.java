package com.mhike.m_hike.classes;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.mhike.m_hike.AddHikeActivity;
import com.mhike.m_hike.AddObservationActivity;
import com.mhike.m_hike.EditHikeActivity;
import com.mhike.m_hike.classes.enums.ActivityForm;

import java.time.LocalDate;


public class DatePickerFragment extends DialogFragment implements
        DatePickerDialog.OnDateSetListener {

    private final boolean disablePastDates;
    private ActivityForm activityForm;


    public DatePickerFragment(boolean disablePastDates, ActivityForm activityForm) {
        this.disablePastDates = disablePastDates;
        this.activityForm = activityForm;

    }


    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {

        LocalDate date = LocalDate.of(year, ++month, day);
        updateDateOnForm(date);


    }

    private void updateDateOnForm(LocalDate date) {
        switch (activityForm) {
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
                showActivityFormErrorMessage();


        }
    }

    private void showActivityFormErrorMessage() {
        // display error and show valid enum Activity forms
        ActivityForm activityForms[] = ActivityForm.values();
        Log.d("invalidActivityForm", "You must enter a valid Activity form, from the following list");
        for (ActivityForm form : activityForms) {
            Log.d("validActivityForm", form.toString());
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        LocalDate d = LocalDate.now();
        int year = d.getYear();
        int month = d.getMonthValue();
        int day = d.getDayOfMonth();


        DatePickerDialog datePicker = new DatePickerDialog(getActivity(), this, year, --month, day);
        if (disablePastDates) {
            // disable past dates
            Long currentTimeMillis = System.currentTimeMillis() - 1000;
            datePicker.getDatePicker().setMinDate(currentTimeMillis);
            return datePicker;

        }
        // disable future dates
        Long currentTimeMillis = System.currentTimeMillis() - 1000;
        datePicker.getDatePicker().setMaxDate(currentTimeMillis);

        return datePicker;

    }

}
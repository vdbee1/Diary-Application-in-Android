package com.s4plabs.logbook.utils;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.widget.DatePicker;

import com.s4plabs.logbook.ui.SearchViewer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {

        Date date = new Date();

        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");

        try {
            //Below i have done month+1 because dont know why datepicker is returning value that is one month less.
            date = format.parse(Integer.toString(day) + "-" + Integer.toString(month + 1) + "-" + Integer.toString(year));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String formattedDate = format.format(date);

        //Pass this formatted Date to search function

        Intent intent = new Intent(getActivity().getApplicationContext(), SearchViewer.class);
        intent.putExtra("formatted_date", formattedDate);
        startActivity(intent);
    }
}
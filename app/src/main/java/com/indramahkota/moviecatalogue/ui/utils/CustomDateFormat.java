package com.indramahkota.moviecatalogue.ui.utils;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CustomDateFormat {
    public static String formatDateFromString(String inputDate) {
        Date parsed;
        String outputDate = "";

        SimpleDateFormat df_input = new SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault());
        SimpleDateFormat df_output = new SimpleDateFormat("dd, MMM yyyy", java.util.Locale.getDefault());

        try {
            parsed = df_input.parse(inputDate);
            if (parsed != null) {
                outputDate = df_output.format(parsed);
            }
        } catch (ParseException e) {
            Log.d("Date Error", "ParseException - dateFormat");
        }
        return outputDate;
    }
}

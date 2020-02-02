package com.alan.asm.vnexpressdemo.utils;

import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateTimeUtil {
    private static final String TAG = "DateTimeUtil";
    public static final String DATE_TEXT_1 = "E, dd MMM yyyy HH:mm:ss zzzz";
    public static final String DATE_TEXT_2 = "HH:mm dd/MM/yyyy";

    public static String formatDate(Date date, String format) {
        if (date == null) return null;
        DateFormat formatter = new SimpleDateFormat(format, Locale.US);
        return formatter.format(date);
    }

    public static Date toDate(String time, String format) {
        if (time == null) return null;
        try {
            DateFormat formatter = new SimpleDateFormat(format, Locale.US);
            return formatter.parse(time);
        } catch (ParseException e) {
            Log.e(TAG, "Parse date error!");
        }
        return null;
    }

}
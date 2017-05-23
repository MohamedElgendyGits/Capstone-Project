package com.instanews.utils;

import org.ocpsoft.prettytime.PrettyTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Mohamed Elgendy on 23/5/2017.
 */

public class DateUtils {

    public static String convertToPrettyDate(String date) {

        //Log.d("publishedAt Date",publishedAt);
        //date : 2015-02-10T14:00:01.000Z
        date = date.substring(0,date.indexOf('T'));

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateInString = date;
        Date newDate = null;
        try {
            newDate = formatter.parse(dateInString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        PrettyTime p = new PrettyTime();
        String prettyTime = p.format(newDate);

        return  prettyTime;
    }

}

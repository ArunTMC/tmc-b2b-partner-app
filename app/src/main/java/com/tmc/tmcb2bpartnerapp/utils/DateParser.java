package com.tmc.tmcb2bpartnerapp.utils;

import android.text.format.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import static com.tmc.tmcb2bpartnerapp.utils.Constants.*;

public class DateParser {
    static String CurrentTime ="", CurrentDate_time ="", FormattedTime="",formattedDate="";


    public static String getDate_and_time_newFormat()
    {
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat(newDate_Time_Format,Locale.ENGLISH);
        df.setTimeZone(TimeZone.getTimeZone(timeZone_Format));
        CurrentDate_time = df.format(c);
        return CurrentDate_time;
    }


    public static String getDate_newFormat()
    {
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat(newDate_Format,Locale.ENGLISH);
        df.setTimeZone(TimeZone.getTimeZone(timeZone_Format));
        CurrentDate_time = df.format(c);
        return CurrentDate_time;
    }
    public static String getDate_and_time_OLDFormat()
    {
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat(oldDayDate_Time_Format,Locale.ENGLISH);
        df.setTimeZone(TimeZone.getTimeZone(timeZone_Format));
        CurrentDate_time = df.format(c);
        return CurrentDate_time;
    }



    public static String checkForTimeDifference(String currentTime , String OldTime)
    {
        if(currentTime == ""){
            currentTime = getDate_and_time_newFormat();
        }
        SimpleDateFormat sdf = new SimpleDateFormat(newDate_Time_Format);
        CharSequence ago = null;
        try {
            long currenttimelong = sdf.parse(currentTime).getTime();
            long oldtimelong = sdf.parse(OldTime).getTime();
          //  long now = System.currentTimeMillis();
             ago =
                    DateUtils.getRelativeTimeSpanString(oldtimelong, currenttimelong, DateUtils.MINUTE_IN_MILLIS);


        } catch (ParseException e) {
            e.printStackTrace();
        }




        return String.valueOf((ago));
    }

    public static String getLongValuefortheDate(String time) {
        String longvalue = "";
        try {

            SimpleDateFormat sdf = new SimpleDateFormat(Constants.newDate_Time_Format,Locale.ENGLISH);
            sdf.setTimeZone(TimeZone.getTimeZone(timeZone_Format));

            Date date = sdf.parse(time);
            long time1long = date.getTime() / 1000;
            longvalue = String.valueOf(time1long);

        } catch (Exception ex) {
            ex.printStackTrace();
            try {
                //     Log.d(TAG, "time1long  "+orderplacedtime);

                SimpleDateFormat sdf = new SimpleDateFormat(oldDayDate_Time_Format,Locale.ENGLISH);

                sdf.setTimeZone(TimeZone.getTimeZone(timeZone_Format));
                Date date = sdf.parse(time);
                long time1long = date.getTime() / 1000;
                longvalue = String.valueOf(time1long);


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return longvalue;
    }



    public static String getDateTextFor_OldDays(int no_of_daysBefore){
        SimpleDateFormat dateFormat = new SimpleDateFormat(newDate_Format,Locale.ENGLISH);
        dateFormat.setTimeZone(TimeZone.getTimeZone(timeZone_Format));

        Date date = null;
        try {
            date = dateFormat.parse(getDate_newFormat());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        calendar.add(Calendar.DATE, -no_of_daysBefore);


        Date c1 = calendar.getTime();


        SimpleDateFormat df1 = new SimpleDateFormat(newDate_Format,Locale.ENGLISH);
        df1.setTimeZone(TimeZone.getTimeZone(timeZone_Format));

        String  PreviousdayDate = df1.format(c1);

        return PreviousdayDate;
    }

    public static String convertTime_to_DisplayingFormat(String time) {
        String CurrentDate1 = "";

        try {
            SimpleDateFormat sdf = new SimpleDateFormat(newDate_Time_Format,Locale.ENGLISH);
            sdf.setTimeZone(TimeZone.getTimeZone(timeZone_Format));

            try {
                Date date = sdf.parse(time);

                SimpleDateFormat day = new SimpleDateFormat(oldDayDate_Time_Format,Locale.ENGLISH);
                day.setTimeZone(TimeZone.getTimeZone(timeZone_Format));


                CurrentDate1 = day.format(date);


            } catch (ParseException e) {
                e.printStackTrace();
            }


        } catch (Exception e) {

            try {
                SimpleDateFormat sdff = new SimpleDateFormat(newDate_Time_Format,Locale.ENGLISH);
                sdff.setTimeZone(TimeZone.getTimeZone(timeZone_Format));


                try {
                    Date date = sdff.parse(time);

                    SimpleDateFormat day = new SimpleDateFormat(oldDayDate_Time_Format,Locale.ENGLISH);
                    day.setTimeZone(TimeZone.getTimeZone(timeZone_Format));


                    CurrentDate1 = day.format(date);

                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            } catch (Exception e2) {

                try {
                    SimpleDateFormat sdff = new SimpleDateFormat(newDate_Format,Locale.ENGLISH);
                    sdff.setTimeZone(TimeZone.getTimeZone(timeZone_Format));

                    try {
                        Date date = sdff.parse(time);

                        SimpleDateFormat day = new SimpleDateFormat(oldDayDate_Time_Format,Locale.ENGLISH);
                        day.setTimeZone(TimeZone.getTimeZone(timeZone_Format));

                        CurrentDate1 = day.format(date);

                    } catch (Exception e3) {
                        e3.printStackTrace();
                    }
                } catch (Exception e4) {
                    CurrentDate1 = time;

                    e4.printStackTrace();
                }
                e2.printStackTrace();
            }
            e.printStackTrace();
        }

        return CurrentDate1;

    }



    public static String convertOldFormatDateintoNewFormat(String todaysdate) {


        Date date = null;
        String CurrentDate = "";
        SimpleDateFormat formatGMT = new SimpleDateFormat(oldDayDate_Format, Locale.ENGLISH);

        formatGMT.setTimeZone(TimeZone.getTimeZone(timeZone_Format));

        try
        {
            date  = formatGMT.parse(todaysdate);
        }
        catch (ParseException e)
        {
            //log(Log.ERROR, "DB Insertion error", e.getMessage().toString());
            //logException(e);
            e.printStackTrace();
        }

        try{

            SimpleDateFormat day = new SimpleDateFormat(newDate_Format,Locale.ENGLISH);
            day.setTimeZone(TimeZone.getTimeZone(timeZone_Format));


            CurrentDate = day.format(date);


        }
        catch (Exception e){
            e.printStackTrace();
        }


        return CurrentDate;

    }

}



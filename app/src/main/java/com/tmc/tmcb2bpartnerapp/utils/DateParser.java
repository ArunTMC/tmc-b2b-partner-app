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


    public static String getDate_oldFormat()
    {
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat(oldDayDate_Format,Locale.ENGLISH);
        df.setTimeZone(TimeZone.getTimeZone(timeZone_Format));
        CurrentDate_time = df.format(c);
        return CurrentDate_time;
    }


    public static String getTime_newFormat()
    {
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat(time_Format,Locale.ENGLISH);
        df.setTimeZone(TimeZone.getTimeZone(timeZone_Format));
        CurrentDate_time = df.format(c);
        return CurrentDate_time;
    }




    public static String getTime_newunderscoreFormat()
    {
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat(time_with_underscore_Format,Locale.ENGLISH);
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

    public static String convertDateTime_to_DisplayingDateOnlyFormat(String time) {
        String CurrentDate1 = "";

        try {
            SimpleDateFormat sdf = new SimpleDateFormat(newDate_Time_Format,Locale.ENGLISH);
            sdf.setTimeZone(TimeZone.getTimeZone(timeZone_Format));

            try {
                Date date = sdf.parse(time);

                SimpleDateFormat day = new SimpleDateFormat(oldDayDate_Format,Locale.ENGLISH);
                day.setTimeZone(TimeZone.getTimeZone(timeZone_Format));


                CurrentDate1 = day.format(date);


            } catch (ParseException e) {
                try {
                    SimpleDateFormat sdff = new SimpleDateFormat(oldDayDate_Time_Format,Locale.ENGLISH);
                    sdff.setTimeZone(TimeZone.getTimeZone(timeZone_Format));


                    try {
                        Date date = sdff.parse(time);

                        SimpleDateFormat day = new SimpleDateFormat(oldDayDate_Format,Locale.ENGLISH);
                        day.setTimeZone(TimeZone.getTimeZone(timeZone_Format));


                        CurrentDate1 = day.format(date);

                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
                catch (Exception e2) {

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


        } catch (Exception e) {

            try {
                SimpleDateFormat sdff = new SimpleDateFormat(oldDayDate_Time_Format,Locale.ENGLISH);
                sdff.setTimeZone(TimeZone.getTimeZone(timeZone_Format));


                try {
                    Date date = sdff.parse(time);

                    SimpleDateFormat day = new SimpleDateFormat(oldDayDate_Format,Locale.ENGLISH);
                    day.setTimeZone(TimeZone.getTimeZone(timeZone_Format));


                    CurrentDate1 = day.format(date);

                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
            catch (Exception e2) {

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


    public static String getDateTextFor_OldDaysfrom_given(int no_of_daysBefore , String time){
        SimpleDateFormat dateFormat = new SimpleDateFormat(newDate_Format,Locale.ENGLISH);
        dateFormat.setTimeZone(TimeZone.getTimeZone(timeZone_Format));
        Date date = null;
        try {
            date = dateFormat.parse(time);
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



    public static String convertOldFormatDateTimeintoNewFormatDateTime(String todaysdate) {


        Date date = null;
        String CurrentDate = "";
        SimpleDateFormat formatGMT = new SimpleDateFormat(oldDayDate_Time_Format, Locale.ENGLISH);

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

            SimpleDateFormat day = new SimpleDateFormat(newDate_Time_Format,Locale.ENGLISH);
            day.setTimeZone(TimeZone.getTimeZone(timeZone_Format));


            CurrentDate = day.format(date);


        }
        catch (Exception e){
            e.printStackTrace();
        }


        return CurrentDate;

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


    public static String convertDateTimeFormatWithoutSeconds(String todaysdate) {


        Date date = null;
        String CurrentDate = "";
        SimpleDateFormat formatGMT = new SimpleDateFormat(oldDayDate_Time_Format, Locale.ENGLISH);

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

            SimpleDateFormat day = new SimpleDateFormat(oldDayDate_Time_FormatWithoutSeconds,Locale.ENGLISH);
            day.setTimeZone(TimeZone.getTimeZone(timeZone_Format));


            CurrentDate = day.format(date);


        }
        catch (Exception e){
            e.printStackTrace();
        }


        return CurrentDate;

    }


    public static  String getDatewithNameoftheseventhDayFromSelectedStartDate(String sDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, d MMM yyyy",Locale.ENGLISH);
        dateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
        Date date = null;
        try {
            date = dateFormat.parse(sDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //Log.d(Constants.TAG, "getOrderDetailsUsingApi sDate: " + sDate);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        //Log.d(Constants.TAG, "getOrderDetailsUsingApi date: " + date);

        calendar.add(Calendar.DATE, 6);




        Date c1 = calendar.getTime();

        SimpleDateFormat previousday = new SimpleDateFormat("EEE",Locale.ENGLISH);
        previousday.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));

        String PreviousdayDay = previousday.format(c1);



        SimpleDateFormat df1 = new SimpleDateFormat("d MMM yyyy",Locale.ENGLISH);
        df1.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));



        String  PreviousdayDate = df1.format(c1);
        String yesterdayAsString = PreviousdayDay+", "+PreviousdayDate;
        //Log.d(Constants.TAG, "getOrderDetailsUsingApi yesterdayAsString: " + PreviousdayDate);

        return yesterdayAsString;
    }



    public static long getMillisecondsFromDate(String dateString) {
        Calendar calendarr = Calendar.getInstance();



        calendarr.add(Calendar.DATE,-1);



        long milliseconds = calendarr.getTimeInMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM yyyy",Locale.ENGLISH);
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));

        try{
            //formatting the dateString to convert it into a Date
            Date date = sdf.parse(dateString);
            System.out.println("Given Time in milliseconds : "+date.getTime());

            Calendar calendar = Calendar.getInstance();
            //Setting the Calendar date and time to the given date and time
            calendar.setTime(date);
            System.out.println("Given Time in milliseconds : "+calendar.getTimeInMillis());
            milliseconds = calendar.getTimeInMillis();
        }catch(ParseException e){
            e.printStackTrace();
        }
        return  milliseconds;
    }


    public static String getDatewithNameofthePreviousDayfromSelectedDayForDatePicker(String sDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy",Locale.ENGLISH);
        dateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
        String yesterdayAsString ="";
        try {
            Date date = null;
            try {
                date = dateFormat.parse(sDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            //Log.d(Constants.TAG, "getOrderDetailsUsingApi sDate: " + sDate);

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            //Log.d(Constants.TAG, "getOrderDetailsUsingApi date: " + date);

            calendar.add(Calendar.DATE, -1);


            Date c1 = calendar.getTime();

            SimpleDateFormat previousday = new SimpleDateFormat("EEE",Locale.ENGLISH);
            previousday.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));



            String PreviousdayDay = previousday.format(c1);


            SimpleDateFormat df1 = new SimpleDateFormat("d MMM yyyy",Locale.ENGLISH);
            df1.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));

            String PreviousdayDate = df1.format(c1);
            yesterdayAsString = PreviousdayDay + ", " + PreviousdayDate;
            //Log.d(Constants.TAG, "getOrderDetailsUsingApi yesterdayAsString: " + PreviousdayDate);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return yesterdayAsString;
    }


    public static  String getDayString(int value) {
        if (value == 1) {
            return "Sun";
        }  else if (value == 2) {
            return "Mon";
        } else if (value == 3) {
            return "Tue";
        } else if (value == 4) {
            return "Wed";
        } else if (value == 5) {
            return "Thu";
        } else if (value == 6) {
            return "Fri";
        }
        else if (value == 7) {
            return "Sat";
        }
        return "";
    }


    public static String getMonthString(int value) {

            if (value == 0) {
                return "Jan";
            } else if (value == 1) {
                return "Feb";
            } else if (value ==2) {
                return "Mar";
            } else if (value ==3) {
                return "Apr";
            } else if (value ==4) {
                return "May";
            } else if (value ==5) {
                return "Jun";
            } else if (value ==6) {
                return "Jul";
            } else if (value ==7) {
                return "Aug";
            } else if (value ==8) {
                return "Sep";
            } else if (value ==9) {
                return "Oct";
            } else if (value ==10) {
                return "Nov";
            } else if (value ==11) {
                return "Dec";
            }
            return "";
        }


}



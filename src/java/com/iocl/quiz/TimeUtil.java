package com.iocl.quiz;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 *
 */
public class TimeUtil {

    static final Map<String, String> dateMapper = new HashMap<String, String>();

    static {
        dateMapper.put("January", "01");
        dateMapper.put("February", "02");
        dateMapper.put("March", "03");
        dateMapper.put("April", "04");
        dateMapper.put("May", "05");
        dateMapper.put("June", "06");
        dateMapper.put("July", "07");
        dateMapper.put("August", "08");
        dateMapper.put("September", "09");
        dateMapper.put("October", "10");
        dateMapper.put("November", "11");
        dateMapper.put("December", "12");
    }

    public static Date convertToDate(String dateTime) {
        String dt = dateMapper.get(dateTime.split(" ")[0]) + "/" + dateTime.split(" ")[1].replace(",", "") + "/" + dateTime.split(" ")[2] + " " + dateTime.split(" ")[3];
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        //08/01/2018 11:21:05
        Date date2 = null;
        try {
            date2 = format.parse(dt);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date2;
    }

    public static long getDateDiff(String dateTime, TimeUnit timeUnit) {
        Date date1 = new Date();
        String dt = dateMapper.get(dateTime.split(" ")[0]) + "/" + dateTime.split(" ")[1].replace(",", "") + "/" + dateTime.split(" ")[2] + " " + dateTime.split(" ")[3];
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        Date date2 = null;
        try {
            date2 = format.parse(dt);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long diffInMillies = date1.getTime() - date2.getTime();
        return timeUnit.convert(diffInMillies, TimeUnit.MILLISECONDS);
    }

    public static long getTimeDiff(String startDateTime, String endDateTime, TimeUnit timeUnit) {
        String dt1 = dateMapper.get(startDateTime.split(" ")[0]) + "/" + startDateTime.split(" ")[1].replace(",", "") + "/" + startDateTime.split(" ")[2] + " " + startDateTime.split(" ")[3];
        String dt2 = dateMapper.get(endDateTime.split(" ")[0]) + "/" + endDateTime.split(" ")[1].replace(",", "") + "/" + endDateTime.split(" ")[2] + " " + endDateTime.split(" ")[3];
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        Date date1 = null;
        Date date2 = null;
        try {
            date1 = format.parse(dt1);
            date2 = format.parse(dt2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long diffInMillies = date2.getTime() - date1.getTime();
        return timeUnit.convert(diffInMillies, TimeUnit.MILLISECONDS);
    }

    public static String convertToJSDate(String timeStamp) {
        String time = timeStamp.split(" ")[1];
        String date = timeStamp.split(" ")[0];
        date = getKeyByValue(dateMapper, date.split("/")[0]) + " " + date.split("/")[1] + ", " + date.split("/")[2];

        return date + " " + time;
    }

    public static long getTimeDiffS(String dateTime, TimeUnit timeUnit) {
        Date date1 = new Date();
        String dt = dateMapper.get(dateTime.split(" ")[0]) + "/" + dateTime.split(" ")[1].replace(",", "") + "/" + dateTime.split(" ")[2] + " " + dateTime.split(" ")[3];
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        Date date2 = null;
        try {
            date2 = format.parse(dt);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long diffInMillies = date1.getTime() - date2.getTime();
        return (diffInMillies / 1000 );
    }
    public static long getTimeDiffS2(String dateTime, TimeUnit timeUnit) {
        Date date1 = new Date();
        System.out.println("datetime"+dateTime);
        String dt = dateTime.split(" ")[0] + "/" + dateTime.split(" ")[1].replace(",", "") + "/" + dateTime.split(" ")[2] + " " + dateTime.split(" ")[3];
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        Date date2 = null;
        try {
            date2 = format.parse(dt);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long diffInMillies = date1.getTime() - date2.getTime();
        return (diffInMillies / 1000 );
    }

    public static <T, E> T getKeyByValue(Map<T, E> map, E value) {
        for (Map.Entry<T, E> entry : map.entrySet()) {
            if (value.equals(entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }
}

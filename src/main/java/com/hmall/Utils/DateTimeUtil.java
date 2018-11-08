package com.hmall.Utils;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Date;

public class DateTimeUtil {

    private static final String STANDARD_FORMAT="yyyy-MM-DD HH:mm:ss";

    public static String dateTimeToStr(Date dateTime,String format){
        if(dateTime==null){
            return StringUtils.EMPTY;
        }
        DateTime dateTime1=new DateTime(dateTime);
        return dateTime1.toString(format);


    }

    public static Date strToDataTime(String str, String format){
        DateTimeFormatter dateTimeFormatter=DateTimeFormat.forPattern(format);
        DateTime dateTime=dateTimeFormatter.parseDateTime(str);
        return  dateTime.toDate();
    }

    public static String dateTimeToStr(Date dateTime){
        if(dateTime==null){
            return StringUtils.EMPTY;
        }
        DateTime dateTime1=new DateTime(dateTime);
        return dateTime1.toString(STANDARD_FORMAT);


    }

    public static Date strToDataTime(String str){
        DateTimeFormatter dateTimeFormatter=DateTimeFormat.forPattern(STANDARD_FORMAT);
        DateTime dateTime=dateTimeFormatter.parseDateTime(str);
        return  dateTime.toDate();
    }
}

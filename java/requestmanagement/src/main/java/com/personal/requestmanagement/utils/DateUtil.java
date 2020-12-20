package com.personal.requestmanagement.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtil {

	public static final String FORMAT_DATE_DEFAULT = "yyyyMMdd";
    public static final String FORMAT_DATE_VN_DEFAULT = "dd-MM-yyyy";
    public static final String FORMAT_YMDH = "yyyyMMddHH";
    public static final String FORMAT_YMDHM = "yyyyMMddHHmm";
    public static final String DATE_FORMAT_NOW = "yyyyMMddHHmmss";
    public static final String FORMAT_DATE_BIRTHDATE = "yyyy/MM/dd";
    public static final String FORMAT_DATE_BIRTHDATE_JP = "yyyy-MM-dd";
    public static final String FORMAT_DATE_YYYY_MM_DD = "yyyy-MM-dd";
    public static final String FORMAT_DATE_YYYY_MM_DD_HH_mm_ss = "yyyy-MM-dd HH:mm:ss";
    public static final String FORMAT_DAYOFWEEK = "EEE";
    public static final String FORMAT_DAYOFWEEK_FULL = "EEEE";
    public static final String DATE_DEFAULT = "1900/01/01";
    public static final String TIME_DEFAULT = "HH:mm:ss";
    public static final String TIME_GPS = "ddMMyyHHmmss.SSS";
    public static final String TIME_DATA_MAP = "dd-MM-yyyy,HH:mm";
    public static final String TIME_DATA_MAP_24 = "dd-MM-yyyy,HH:mm:ss.SSS";
    public static final String TIME_DATA_POSITION = "HH:mm - dd/MM/yyyy";
    public static final String TIME_PICKUP = "HH:mm";
    public static final String TIME_CALENDAR_EVENT = "dd-MM-yyyy,HH:mm";
    public static final String FORMAT_MAP_HISTORY= "dd/MM/yyyy";
    public static final String FORMAT_MMDDYYYY_hhmmss_aa = "MM/dd/yyyy hh:mm aa";
    public static final String FORMAT_DDMMYYYY_HHMMSS= "dd/MM/yyyy HH:mm:ss";
    public static final String FORMAT_SIMPLE_DATE= "dd/MM";
    public static final String FORMAT_DDMMYYYY_HHMM= "dd/MM/yyyy, HH:mm";

	public static String getDateByFormat(String dateFormat) {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		return sdf.format(cal.getTime());
	}
	
	public static String convertDatetoString(Date input, String format){
        try {
            SimpleDateFormat formater = new SimpleDateFormat(format);
            String date = formater.format(input);
            return date;
        } catch (Exception e){
            return null;
        }
    }

	public static Date now() {
		Calendar cal = Calendar.getInstance();
		return cal.getTime();
	}

	public static long getCurrentTimeStamp() {
		Calendar cal = Calendar.getInstance();
		return cal.getTimeInMillis();
	}

	public static long getMillisFromDate(String dateString, String dateFormat) {
		Calendar cal = Calendar.getInstance();

		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		try {
			Date date = sdf.parse(dateString);
			cal.setTime(date);
		} catch (Exception ex) {
			return getCurrentTimeStamp();
		}
		return cal.getTimeInMillis();
	}

	public static String getDateByFormat(long time, String dateFormat) {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		try {
			Date date = new Date(ConvertTimeStamp(time).getTime());
			cal.setTime(date);
		} catch (Exception ex) {

		}
		return sdf.format(cal.getTime());
	}

	public static String getDateByFormat(Date date, String dateFormat) {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		try {
			cal.setTime(date);
		} catch (Exception ex) {

		}
		return sdf.format(cal.getTime());
	}

	public static long CurrentTimeStamp() {
		return Calendar.getInstance().getTimeInMillis();
	}

	public static Timestamp ConvertTimeStamp(long timevalue) {
		Timestamp time = new Timestamp(timevalue);
		return time;
	}

	public static Date LocalDateToDate(LocalDate localDate) {
		return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
	}

	public static LocalDate DateToLocalDate(Date date) {
		return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
	}
	
	public static Date stringToDate(String date, String format) {
		try {
			return new SimpleDateFormat(format, Locale.getDefault()).parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}

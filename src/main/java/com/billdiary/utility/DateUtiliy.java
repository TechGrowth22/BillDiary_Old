package com.billdiary.utility;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.TimeZone;

public class DateUtiliy {

	
	public static String getDateStringFromLocalDate(LocalDate localDate, String format) {
		DateFormat df = new SimpleDateFormat(format);
		//default time zone
		ZoneId defaultZoneId = ZoneId.systemDefault();
		 //local date + atStartOfDay() + default time zone + toInstant() = Date
        Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
        
        String strdate = df.format(date);
        
        return strdate;
	}
	
	public static String getDirectoryByDate(Date date) {
		DateFormat dateFormat = new SimpleDateFormat(Constants.DATE_FORMAT_MM_DD_YYYY);
		TimeZone tz = TimeZone.getTimeZone("UTC");
		dateFormat.setTimeZone(tz);
		return dateFormat.format(date);
	}
	
	public static String getBarcodeFileNameByDateAndTime(Date date) {
		String fileName = new SimpleDateFormat("yyyy-MM-dd-HH-mm'.pdf'").format(date);
		StringBuffer buffer =new StringBuffer();
		buffer.append(fileName);
		return buffer.toString();
	}
}

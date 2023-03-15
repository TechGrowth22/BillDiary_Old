package com.billdiary.utility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataTypeConverter {

	final static Logger LOGGER = LoggerFactory.getLogger(DataTypeConverter.class); 
	
	public static Double getDoubleFromString(String value) {
		Double dubValue = 0.0;
		try {
			dubValue = Double.parseDouble(value);
			return dubValue;
		}catch(Exception e) {
			LOGGER.error(e.getMessage(),e);
			return dubValue;
		}
	}
	
	public static String getStringFromDouble(Double value) {
		String strValue = "";
		strValue= String.valueOf(value);
		return strValue;
	}
	
	public static Long getLongFromString(String value) {
		Long longValue = 0L;
		try {
			longValue= Long.parseLong(value);
			return longValue;
		}catch(Exception e) {
			LOGGER.error(e.getMessage(),e);
			return longValue;
		}
	}

	public static Integer getIntegerFromString(String value) {
		Integer intValue = 0;
		try {
			intValue= Integer.parseInt(value);
			return intValue;
		}catch(Exception e) {
			LOGGER.error(e.getMessage(),e);
			return intValue;
		}
	}

}
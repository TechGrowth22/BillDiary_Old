package com.billdiary.utility;

import java.text.DecimalFormat;
import java.text.ParseException;
import org.springframework.stereotype.Component;

import com.billdiary.exception.BusinessException;
import com.billdiary.model.Product;

@Component
public class Calculate {

	static StringBuffer buffer = new StringBuffer();
	static DecimalFormat df = new DecimalFormat("0.00");

	public Double getProductTotalPrice(Product product) {
		Double price = 0.0;
		
		price = getRetailWithGST(product.getSellPrice(),product.getSellPriceGSTPercentage()) * product.getQuantity();
		if (product.getDiscount() > 0) {
			Double dis = getRetailWithGST(product.getSellPrice(),product.getSellPriceGSTPercentage()) * product.getQuantity() * (product.getDiscount() / 100);
			price = price - dis;
		}

		return price;
	}

	public static double getWholeSalePrice(double price, String percentageString) {
		double buyPrice = 0.0;
		if (null != percentageString) {
			percentageString = percentageString.replace('%', ' ');
			percentageString = percentageString.trim();
			if (!("0".equals(percentageString))) {
				double percentage = Double.parseDouble(percentageString);
				price = price * 100.00;
				percentage = percentage + 100.00;
				buyPrice = price / percentage;
			} else {
				buyPrice = price;
			}

		} else {
			buyPrice = price;
		}

		String formate = df.format(buyPrice);
		try {
			buyPrice = Double.valueOf(df.parse(formate).doubleValue());

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return buyPrice;
	}

	public static double getRetailPrice(double price, String percentageString,double discount) {
		double retailPrice = 0.0;
		if (null != percentageString) {
			double percentage=getPercentage(percentageString);
			//percentage=percentage+discount;
			if (!(percentage==0.00)) {
				price = price * 100.00;
				percentage = percentage + 100.00;
				retailPrice = price / percentage;
			} else {
				retailPrice = price;
			}

		} else {
			retailPrice = price;
		}

		String formate = df.format(retailPrice);
		try {
			retailPrice = Double.valueOf(df.parse(formate).doubleValue());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return retailPrice;
	}

	public static double getPercentage(String percentageString)
	{
		double percentage=0.00;
		percentageString = percentageString.replace('%', ' ');
		percentageString = percentageString.trim();
		percentage=Double.parseDouble(percentageString);
		return percentage;
		
	}
	public static String trimPercentage(String percentage) {

		percentage = percentage.replace('%', ' ');
		percentage = percentage.trim();
		return percentage;

	}

	public static double getWholeSaleWithGST(double price, double percentage) {
		try {
			percentage = percentage + 100;
			price = price * percentage;
			price = price / 100;
			price=getFormatedDoubleValue(price);
		}catch(Exception e) {
			
		}
		return price;
	}

	public static double getRetailWithGST(double price, double percentage) {
		try {
			percentage = percentage + 100;
			price = price * percentage;
			price = price / 100;
			price=getFormatedDoubleValue(price);
		}catch(Exception e) {
			
		}
		return price;
	}
	
	public static double getFormatedDoubleValue(double value){
		String formate = df.format(value);
		try {
			value=Double.valueOf(df.parse(formate).doubleValue());
		} catch (ParseException e) {
			//throw new BusinessException("ERR001","Double value Cannot be formatted");
		}
		return value;
	}
	
	public static String getFormatedDoubleToStringValue(double value){
		String formate = df.format(value);
		try {
			value=Double.valueOf(df.parse(formate).doubleValue());
		} catch (ParseException e) {
			//throw new BusinessException("ERR001","Double value Cannot be formatted");
		}
		return Double.toString(value);
	}
	
	
	public static double getNonEmptyDoubleValue(String value) {
		double doubleValue=0.00;
		try {
		
			if(value.isEmpty()) {
				doubleValue=0.00;
			}else if(value.matches("^\\d*\\.?\\d*$")){
				doubleValue=Double.parseDouble(value);
			}else {
				doubleValue=0.00;
			}
			doubleValue=getFormatedDoubleValue(doubleValue);
		}catch(Exception e) {
			
		}
		return doubleValue;	
	}
	public static int getNonEmptyIntegerValue(String value) {
		int intValue=0;
		if(value.isEmpty()) {
			intValue=0;
		}else if(value.matches("^\\d*\\.?\\d*$")){
			intValue=Integer.parseInt(value);
		}else {
			intValue=0;
		}
		return intValue;	
	}
}

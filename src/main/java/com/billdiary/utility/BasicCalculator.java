package com.billdiary.utility;

import org.springframework.stereotype.Component;

@Component
public class BasicCalculator {
	private double value;
	private double percentage;
	private double result;
	
	/**
	 * This method will return the total percentage value + value
	 * if value is 100 and percentage is 5% the the return value will be 105
	 * @param percentage
	 * @param value
	 * @return
	 */
	public double getPercentageOfValue(double value,double percentage) {
		if(percentage!=0) {
			percentage=percentage+100;
			value=value*percentage;
			result=value/100;
		}else {
			result=value;
		}
		return result;
	}
	/**
	 * This method will return original value of percetage value
	 * if the value is 105 and percentage is 5 then it will return 100
	 * @param percentage
	 * @param value
	 * @return
	 */
	public double getValueOfPercentage(double value,double percentage) {
		if(percentage!=0 && percentage > 0  && percentage <100) {
			value = value * 100.00;
			percentage = percentage + 100.00;
			result = value / percentage;
		}else {
			result = value;
		}
		
		return result;
	}
	/**
	 * this method will return the sum of discount and original value
	 * if discont is 5% and value is 100 then is will return the value 95
	 * @param discount
	 * @param value
	 * @return
	 */
	public double getDiscountSubtractedValue(double value,double discount) {
		if(discount!=0) {
			discount=100.00-discount;
			value=value*discount;
			result=value/100;
		}else {
			result=value;
		}
		
		return result;
	}
	/**
	 * getDiscountaddedValue this method is used for returining the original price on which discount has been counted
	 * if value=105 and discount=5 then it will return 100
	 * @param discount
	 * @param value
	 * @return
	 */
	public double getDiscountaddedValue(double value,double discount) {
		if(discount!=0) {
			discount=100.00-discount;
			value=value*100;
			result=value/discount;
		}else {
			result=value;
		}
		return result;
	}
}

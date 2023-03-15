package com.billdiary.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.billdiary.service.GSTService;
import com.billdiary.utility.BasicCalculator;

@Repository
public class GSTServiceImpl implements GSTService {

	@Autowired
	BasicCalculator calculator;
	
	private double gstPercentage;
	private double gstAmount;
	private double gstIncludedPrice;
	private double gstExcludedPrice;
	private double amount;
	public double getGSTAmount(double amount,double percentage) {
		if(percentage!=0) {
			
		}
		return gstAmount;
	}
	public double getGSTPercentage(double oldamount,double newAmount) {
		amount=newAmount-oldamount;
		amount=amount*100;
		gstPercentage=amount/oldamount;
		return gstPercentage;
	}
	
	public double getGSTIncludedPrice(double price,double percentage) {
		gstIncludedPrice=calculator.getPercentageOfValue(price,percentage);
		return gstIncludedPrice;
	}
	public double getGSTExcludedPrice(double price,double percentage) {
		gstExcludedPrice=calculator.getValueOfPercentage(price,percentage);
		return gstExcludedPrice;
	}
}

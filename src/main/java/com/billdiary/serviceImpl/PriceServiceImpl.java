package com.billdiary.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.billdiary.service.GSTService;
import com.billdiary.service.PriceService;
import com.billdiary.utility.BasicCalculator;
import com.billdiary.utility.Calculate;

/**
 * @author Gajanan Gaikwad
 * This class is used for calculation of all Retail and WholeSalePrice. 
 */
@Repository
public class PriceServiceImpl implements PriceService {
	
	
	@Autowired
	BasicCalculator calculator;
	@Autowired
	GSTService gstService;
	
	double retailPrice;
	double wholeSalePrice;
	double productSellingPrice;
	double productRatePrice;
	
	
	@Override
	public double getRetailPrice(double price, double gstPercentage, double discount) {
		//price=calculator.getDiscountSubtractedValue(price,discount);
		retailPrice=gstService.getGSTExcludedPrice(price,gstPercentage);
		return Calculate.getFormatedDoubleValue(retailPrice);
	}

	@Override
	public double getWholeSalePrice(double price, double gstPercentage, double discount) {
		//price=calculator.getDiscountSubtractedValue(price,discount);
		wholeSalePrice=gstService.getGSTExcludedPrice(price,gstPercentage);
		return Calculate.getFormatedDoubleValue(wholeSalePrice);
	}

	@Override
	public double getRetailGSTPrice(double price, double gstPercentage, double discount) {
		price=gstService.getGSTIncludedPrice(price, gstPercentage);
		retailPrice=calculator.getDiscountaddedValue(price,discount);	
		return Calculate.getFormatedDoubleValue(retailPrice);
	}

	@Override
	public double getWholeSaleGSTPrice(double price, double gstPercentage, double discount) {
		wholeSalePrice=gstService.getGSTIncludedPrice(price, gstPercentage);
		//wholeSalePrice=calculator.getDiscountaddedValue(price,discount);
		return Calculate.getFormatedDoubleValue(wholeSalePrice);
	}

	

	@Override
	public double getProductTotalPrice(double rate, double gstPercentage, double quantity,double discount) {
		rate=quantity==0?rate:(rate*quantity);
		double discountSubtracted=calculator.getDiscountSubtractedValue(rate,discount);
		productSellingPrice=gstService.getGSTIncludedPrice(discountSubtracted, gstPercentage);
		return Calculate.getFormatedDoubleValue(productSellingPrice);
	}

	@Override
	public double getProductRatePrice(double retailPrice, double gstPercentage,double quantity) {
		productRatePrice=gstService.getGSTExcludedPrice(retailPrice, gstPercentage);
		productRatePrice=quantity==0?productRatePrice:(productRatePrice*quantity);
		return Calculate.getFormatedDoubleValue(productRatePrice);
	}

	
}

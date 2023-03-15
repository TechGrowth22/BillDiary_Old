package com.billdiary.service;

import org.springframework.stereotype.Service;

@Service
public interface PriceService {

	public double getRetailPrice(double price,double gstPercentage,double discount);
	public double getWholeSalePrice(double price,double gstPercentage,double discount);
	public double getRetailGSTPrice(double price,double gstPercentage,double discount);
	public double getWholeSaleGSTPrice(double price,double gstPercentage,double discount);
	public double getProductTotalPrice(double retailPrice,double gstPercentage,double quantity,double discount);
	public double getProductRatePrice(double retailPrice,double gstPercentage,double quantity);
}

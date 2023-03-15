package com.billdiary.service;

import org.springframework.stereotype.Service;

@Service
public interface GSTService {

	public double getGSTAmount(double amount,double percentage);
	public double getGSTPercentage(double oldamount,double newAmount);
	public double getGSTIncludedPrice(double price,double percentage);
	public double getGSTExcludedPrice(double price,double percentage);
}

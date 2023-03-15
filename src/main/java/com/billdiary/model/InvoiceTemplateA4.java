package com.billdiary.model;

import java.time.LocalDate;
import java.util.List;

import javafx.scene.image.ImageView;

public class InvoiceTemplateA4 {

	private String invoiceNO;
	private String companyName;
	private ImageView Logo;
	private String customerName;
	private LocalDate today;
	private String customerAddress;
	private String mobileNo;
	private List<Product> products;
	private String totalAmount;
	private String finalAmount;
	private String amountDue;
	private String paidAmount;
	private String discount;
	
	
	public String getInvoiceNO() {
		return invoiceNO;
	}
	public void setInvoiceNO(String invoiceNO) {
		this.invoiceNO = invoiceNO;
	}
	public String getFinalAmount() {
		return finalAmount;
	}
	public void setFinalAmount(String finalAmount) {
		this.finalAmount = finalAmount;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public ImageView getLogo() {
		return Logo;
	}
	public void setLogo(ImageView logo) {
		Logo = logo;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public LocalDate getToday() {
		return today;
	}
	public void setToday(LocalDate today) {
		this.today = today;
	}
	public String getCustomerAddress() {
		return customerAddress;
	}
	public void setCustomerAddress(String customerAddress) {
		this.customerAddress = customerAddress;
	}
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	public List<Product> getProducts() {
		return products;
	}
	public void setProducts(List<Product> products) {
		this.products = products;
	}
	public String getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}
	public String getAmountDue() {
		return amountDue;
	}
	public void setAmountDue(String amountDue) {
		this.amountDue = amountDue;
	}
	public String getPaidAmount() {
		return paidAmount;
	}
	public void setPaidAmount(String paidAmount) {
		this.paidAmount = paidAmount;
	}
	public String getDiscount() {
		return discount;
	}
	public void setDiscount(String discount) {
		this.discount = discount;
	}
	
}

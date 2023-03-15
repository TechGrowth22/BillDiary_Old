package com.billdiary.model;

import java.io.Serializable;

import com.opencsv.bean.CsvBindByName;

public class ProductColumns extends CsvBean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@CsvBindByName(column = "product_id")
    public String product_id;
 
	@CsvBindByName(column = "productCode")
	String productCode;
	
	@CsvBindByName(column = "productName")
	String productName; 
	
	@CsvBindByName(column="productCategory")
	String productCategory;
	
	@CsvBindByName(column="productHSNCode")
	String productHSNCode;

	@CsvBindByName(column = "description")
	String description;
	
	@CsvBindByName(column = "buyPrice")
	String buyPrice;
	
	@CsvBindByName(column = "buyPriceGST")
	String buyPriceGST;
	
	@CsvBindByName(column = "buyPriceGSTPercentage")
	String buyPriceGSTPercentage;
	
	@CsvBindByName(column = "sellPrice")
	String sellPrice;
	
	@CsvBindByName(column = "sellPriceGST")
	String sellPriceGST;
	
	@CsvBindByName(column = "sellPriceGSTPercentage")
	String sellPriceGSTPercentage;
	
	@CsvBindByName(column = "discount")
	String discount; 
	
	@CsvBindByName(column = "stock")
	String stock;
	
	@CsvBindByName(column="lowStockThershold")
	String lowStockThershold;
	
	@CsvBindByName(column="unitEntity")
	String unitEntity;
	


	@Override
	public String toString() {
		return "ProductColumns [product_id=" + product_id + ", productCode=" + productCode + ", productName="
				+ productName + ", productCategory=" + productCategory + ", productHSNCode=" + productHSNCode
				+ ", description=" + description + ", buyPrice=" + buyPrice + ", buyPriceGST=" + buyPriceGST
				+ ", buyPriceGSTPercentage=" + buyPriceGSTPercentage + ", sellPrice=" + sellPrice + ", sellPriceGST="
				+ sellPriceGST + ", sellPriceGSTPercentage=" + sellPriceGSTPercentage + ", discount=" + discount
				+ ", stock=" + stock + ", lowStockThershold=" + lowStockThershold + ", unitEntity=" + unitEntity + "]";
	}

	public String getProduct_id() {
		return product_id;
	}

	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}



	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(String productCategory) {
		this.productCategory = productCategory;
	}

	public String getProductHSNCode() {
		return productHSNCode;
	}

	public void setProductHSNCode(String productHSNCode) {
		this.productHSNCode = productHSNCode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getBuyPrice() {
		return buyPrice;
	}

	public void setBuyPrice(String buyPrice) {
		this.buyPrice = buyPrice;
	}


	public String getBuyPriceGST() {
		return buyPriceGST;
	}

	public void setBuyPriceGST(String buyPriceGST) {
		this.buyPriceGST = buyPriceGST;
	}

	public String getDiscount() {
		return discount;
	}

	public void setDiscount(String discount) {
		this.discount = discount;
	}

	public String getStock() {
		return stock;
	}

	public void setStock(String stock) {
		this.stock = stock;
	}
	
	public String getLowStockThershold() {
		return lowStockThershold;
	}

	public void setLowStockThershold(String lowStockThershold) {
		this.lowStockThershold = lowStockThershold;
	}

	public String getUnitEntity() {
		return unitEntity;
	}

	public void setUnitEntity(String unitEntity) {
		this.unitEntity = unitEntity;
	}

	public String getBuyPriceGSTPercentage() {
		return buyPriceGSTPercentage;
	}

	public void setBuyPriceGSTPercentage(String buyPriceGSTPercentage) {
		this.buyPriceGSTPercentage = buyPriceGSTPercentage;
	}

	public String getSellPrice() {
		return sellPrice;
	}

	public void setSellPrice(String sellPrice) {
		this.sellPrice = sellPrice;
	}

	public String getSellPriceGST() {
		return sellPriceGST;
	}

	public void setSellPriceGST(String sellPriceGST) {
		this.sellPriceGST = sellPriceGST;
	}

	public String getSellPriceGSTPercentage() {
		return sellPriceGSTPercentage;
	}

	public void setSellPriceGSTPercentage(String sellPriceGSTPercentage) {
		this.sellPriceGSTPercentage = sellPriceGSTPercentage;
	}

	
}

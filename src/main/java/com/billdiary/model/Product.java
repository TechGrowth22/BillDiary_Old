package com.billdiary.model;

import org.springframework.stereotype.Component;

import com.billdiary.utility.IconGallery;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.HBox;


@Component
public class Product implements Cloneable{
	
	private SimpleIntegerProperty serialNumber;
	
	private SimpleDoubleProperty totalPrice;
	
	private SimpleIntegerProperty productId;
	
	private SimpleLongProperty productCode;
	
	private SimpleStringProperty name;
	
	private SimpleStringProperty productCategory;
	
	private SimpleStringProperty productHSNCode;
		
	private SimpleDoubleProperty buyPrice;
	
	private SimpleDoubleProperty sellPrice;
	
	private SimpleStringProperty description;
	
	private SimpleDoubleProperty stock;
	
	private SimpleDoubleProperty discount;
	
	private SimpleDoubleProperty quantity;
	
	private SimpleStringProperty buyPriceGST;
	
	private SimpleDoubleProperty buyPriceGSTPercentage;
	
	private SimpleStringProperty sellPriceGST;
	
	private SimpleDoubleProperty sellPriceGSTPercentage;
	
	private SimpleDoubleProperty mrpPrice;
	
	private SimpleDoubleProperty valuePrice;
	
	private SimpleDoubleProperty lowStockThershold;
	
	private Unit unit;
	
	private HBox action;
	
	private Hyperlink delete;
	
	private Hyperlink edit;
	
	
	IconGallery iconGallery=new IconGallery();
	
	public Product()
	{
	
	}
	public Object clone()
	{
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new Product();
	}
	public Product(int prodId, String nameOfProduct,
			double buyPrice, double sellPrice, String descriptionOfProduct,
			int stockOfProduct, double Discount, Hyperlink delete) {
		
		this.productId = new SimpleIntegerProperty(prodId);
		this.name = new SimpleStringProperty(nameOfProduct);
		
		this.buyPrice =new SimpleDoubleProperty( buyPrice);
		this.sellPrice = new SimpleDoubleProperty(sellPrice);
		this.description = new SimpleStringProperty(descriptionOfProduct);
		this.stock = new SimpleDoubleProperty(stockOfProduct);
		this.discount =new SimpleDoubleProperty (Discount);
		this.delete = new Hyperlink();
		this.delete.setGraphic(iconGallery.getDeleteIcon());
		this.delete.setStyle("-fx-text-fill: #606060;");
		this.edit=new Hyperlink();
		this.edit.setGraphic(iconGallery.getSaveIcon());
		this.edit.setStyle("-fx-text-fill: #606060;");
		this.action=new HBox(delete,edit);
	}
	public int getProductId() {
		return productId.get();
	}
	public void setProductId(SimpleIntegerProperty prodId) {
		this.productId=prodId;
	}
	public String getName() {
		return name.get();
	}
	public void setName(SimpleStringProperty nameOfProduct) {
		this.name=nameOfProduct;
	}
	public double getBuyPrice() {
		return buyPrice.get();
	}
	public void setBuyPrice(SimpleDoubleProperty wholesale_Price) {
		this.buyPrice=wholesale_Price;
	}
	public double getSellPrice() {
		return sellPrice.get();
	}
	public void setSellPrice(SimpleDoubleProperty sellPrice) {
		this.sellPrice=sellPrice;
	}
	public String getDescription() {
		return description.get();
	}
	public void setDescription(SimpleStringProperty descriptionOfProduct) {
		this.description=descriptionOfProduct;
	}

	public Double getStock() {
		return null == stock? 0:stock.get();
	}
	public void setStock(SimpleDoubleProperty stock) {
		this.stock = stock;
	}
	public double getDiscount() {
		return discount.get();
	}
	public void setDiscount(SimpleDoubleProperty Discount) {
		this.discount=Discount;
	}
	
	public Hyperlink getDelete() {
		if(delete==null)
		{
		 delete=new Hyperlink();
		 delete.setGraphic(iconGallery.getDeleteIcon());
		 delete.setTooltip(iconGallery.getDeleteToolTip());
		 this.delete.setStyle("-fx-text-fill: #606060;");
		}
		return delete;
	}
	public void setDelete(Hyperlink delete) {
		this.delete = delete;
	}
	public Hyperlink getEdit() {
		if(edit==null)
		{
			edit=new Hyperlink();
			edit.setGraphic(iconGallery.getSaveIcon());
			edit.setTooltip(iconGallery.getEditToolTip());
			
			this.edit.setStyle("-fx-text-fill: #606060;");
		}
		
		return edit;
	}
	public void setEdit(Hyperlink edit) {
		this.edit = edit;
	}
	
	public HBox getAction() {
		if(action==null)
		{
			delete=getDelete();
			edit=getEdit();
			action=new HBox(delete,edit);
			
		}
		return action;
	}
	public void setAction(HBox action) {
		this.action = action;
	}


	public Double getQuantity() {
		return quantity.get();
	}
	public void setQuantity(SimpleDoubleProperty quantity) {
		this.quantity = quantity;
	}
	public int getSerialNumber() {
		return serialNumber.get();
	}
	public void setSerialNumber(SimpleIntegerProperty serialNumber) {
		this.serialNumber = serialNumber;
	}
	public Double getTotalPrice() {
		return totalPrice.get();
	}
	public void setTotalPrice(SimpleDoubleProperty totalPrice) {
		this.totalPrice = totalPrice;
	}
	public String getProductCategory() {
		return productCategory.get();
	}
	public void setProductCategory(SimpleStringProperty productCategory) {
		this.productCategory = productCategory;
	}
	
	public String getBuyPriceGST() {
		return buyPriceGST.get();
	}
	public void setBuyPriceGST(SimpleStringProperty buyPriceGST) {
		this.buyPriceGST = buyPriceGST;
	}
	public Double getBuyPriceGSTPercentage() {
		return buyPriceGSTPercentage.get();
	}
	public void setBuyPriceGSTPercentage(SimpleDoubleProperty buyPriceGSTPercentage) {
		this.buyPriceGSTPercentage = buyPriceGSTPercentage;
	}
	public String getRetailGST() {
		return sellPriceGST.get();
	}
	public void setSellPriceGST(SimpleStringProperty sellPriceGST) {
		this.sellPriceGST = sellPriceGST;
	}
	public Double getSellPriceGSTPercentage() {
		return sellPriceGSTPercentage.get();
	}
	public void setSellPriceGSTPercentage(SimpleDoubleProperty sellPriceGSTPercentage) {
		this.sellPriceGSTPercentage = sellPriceGSTPercentage;
	}
	public String getProductHSNCode() {
		return null==productHSNCode?"":productHSNCode.get();
	}
	public void setProductHSNCode(SimpleStringProperty productHSNCode) {
		this.productHSNCode = productHSNCode;
	}
	public Unit getUnit() {
		return unit;
	}
	public void setUnit(Unit unit) {
		this.unit = unit;
	}
	public Double getMrpPrice() {
		return mrpPrice.get();
	}
	public void setMrpPrice(SimpleDoubleProperty mrpPrice) {
		this.mrpPrice = mrpPrice;
	}

	public Double getValuePrice() {
		return valuePrice.get();
	}
	public void setValuePrice(SimpleDoubleProperty valuePrice) {
		this.valuePrice = valuePrice;
	}
	public Long getProductCode() {
		return productCode.get();
	}
	public void setProductCode(SimpleLongProperty productCode) {
		this.productCode = productCode;
	}	

	public Double getLowStockThershold() {
		return lowStockThershold.get();
	}
	public void setLowStockThershold(SimpleDoubleProperty lowStockThershold) {
		this.lowStockThershold = lowStockThershold;
	}
	
	
}

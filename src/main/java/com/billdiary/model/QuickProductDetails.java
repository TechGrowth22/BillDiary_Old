package com.billdiary.model;


import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;

public class QuickProductDetails {

	private SimpleStringProperty nameofproduct;
	private SimpleIntegerProperty quantity;
	private SimpleIntegerProperty amtperquantity;
	private SimpleIntegerProperty totalamt;
	private Hyperlink Delete;
	
	public Hyperlink getDelete() {
		return Delete;
	}
	public QuickProductDetails(String nameofproduct, int quantity,
			int amtperquantity,int totalamt,Hyperlink Delete1) {
		
		this.nameofproduct =new SimpleStringProperty(nameofproduct);
		this.quantity = new SimpleIntegerProperty(quantity);
		this.amtperquantity = new SimpleIntegerProperty(amtperquantity);
		this.totalamt=new SimpleIntegerProperty(totalamt);
		this.Delete=new Hyperlink("Delete");
	}
	public int getTotalamt() {
		return totalamt.get();
	}

	public String getNameofproduct() {
		return nameofproduct.get();
	}
	
	public void setNameofproduct(String productname) {
		//this.nameofproduct = nameofproduct;
		nameofproduct.set(productname);
	}

	public int getQuantity() {
		return quantity.get();
	}
	
	public int getAmtperquantity() {
		return amtperquantity.get();
	}
	
	
}

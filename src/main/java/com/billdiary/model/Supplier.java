package com.billdiary.model;

import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.billdiary.service.ProductService;
import com.billdiary.utility.IconGallery;

import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.HBox;

public class Supplier {
	
	final static Logger LOGGER = LoggerFactory.getLogger(Supplier.class); 

	private SimpleLongProperty supplierID;
	private SimpleStringProperty supplierName;
	private SimpleStringProperty supplierCompany;
	
	private SimpleStringProperty supplierGovID;
	private SimpleStringProperty supplierEmailID;
	private SimpleStringProperty  supplierPhoneNo;
	private SimpleStringProperty  supplierMobileNo;
	private SimpleStringProperty supplierFaxNo;
	private SimpleStringProperty supplierWebsite;
	private SimpleDoubleProperty supplierUnpaidBalance;
	private DatePicker supplierAsOfDate;
	private SimpleStringProperty supplierAccountNo;
	private SimpleStringProperty supplierGstNo;
	private SimpleDoubleProperty  supplierBillingRate;
	private SimpleStringProperty supplierOther;
	private ReadOnlyStringProperty asOfDateStr;
	private Address address;
	
	

	private  HBox actionbox;
	private Hyperlink deleteHyperlink;
	private Hyperlink saveHyperlink;
	IconGallery iconGallery=new IconGallery();
	
	
	
	public Hyperlink getDeleteHyperlink() {
		if(deleteHyperlink==null)
		{
			deleteHyperlink=new Hyperlink();
			deleteHyperlink.setGraphic(iconGallery.getDeleteIcon());
			deleteHyperlink.setTooltip(iconGallery.getDeleteToolTip());
		}
		return deleteHyperlink;
	}
	public void setDeleteHyperlink(Hyperlink deleteHyperlink) {
		this.deleteHyperlink = deleteHyperlink;
	}
	public Hyperlink getSaveHyperlink() {
		if(saveHyperlink==null)
		{
			saveHyperlink=new Hyperlink();
			try {
			saveHyperlink.setGraphic(iconGallery.getSaveIcon());
			saveHyperlink.setTooltip(iconGallery.getEditToolTip());	
			}
			catch(Exception e)
			{
				LOGGER.error(e.getMessage(),e);
			}
		}
		return saveHyperlink;
		
	}
	public void setSaveHyperlink(Hyperlink saveHyperlink) {
		this.saveHyperlink = saveHyperlink;
	}
	public HBox getActionbox() {
		if(actionbox==null) {
			deleteHyperlink=getDeleteHyperlink();
			saveHyperlink=getSaveHyperlink();
			actionbox=new HBox(deleteHyperlink,saveHyperlink);
		}
		return actionbox;
	}
	public void setActionbox(HBox actionbox) {
		this.actionbox = actionbox;
	}
	
	public Long getSupplierID() {
		return supplierID.get();
	}
	public void setSupplierID(SimpleLongProperty supplierID) {
		this.supplierID = supplierID;
	}
	public String getSupplierName() {
		return supplierName.get();
	}
	public void setSupplierName(SimpleStringProperty supplierName) {
		this.supplierName = supplierName;
	}
	public String getSupplierCompany() {
		return supplierCompany.get();
	}
	public void setSupplierCompany(SimpleStringProperty supplierCompany) {
		this.supplierCompany = supplierCompany;
	}
	
	public String getSupplierGovID() {
		return supplierGovID.get();
	}
	public void setSupplierGovID(SimpleStringProperty supplierGovID) {
		this.supplierGovID = supplierGovID;
	}
	public String getSupplierEmailID() {
		return supplierEmailID.get();
	}
	public void setSupplierEmailID(SimpleStringProperty supplierEmailID) {
		this.supplierEmailID = supplierEmailID;
	}
	public String getSupplierPhoneNo() {
		return supplierPhoneNo.get();
	}
	public void setSupplierPhoneNo(SimpleStringProperty supplierPhoneNo) {
		this.supplierPhoneNo = supplierPhoneNo;
	}
	public String getSupplierMobileNo() {
		return supplierMobileNo.get();
	}
	public void setSupplierMobileNo(SimpleStringProperty supplierMobileNo) {
		this.supplierMobileNo = supplierMobileNo;
	}
	public String getSupplierFaxNo() {
		return supplierFaxNo.get();
	}
	public void setSupplierFaxNo(SimpleStringProperty supplierFaxNo) {
		this.supplierFaxNo = supplierFaxNo;
	}
	public String getSupplierWebsite() {
		return supplierWebsite.get();
	}
	public void setSupplierWebsite(SimpleStringProperty supplierWebsite) {
		this.supplierWebsite = supplierWebsite;
	}
	public double getSupplierUnpaidBalance() {
		return supplierUnpaidBalance.get();
	}
	public void setSupplierUnpaidBalance(SimpleDoubleProperty supplierUnpaidBalance) {
		this.supplierUnpaidBalance = supplierUnpaidBalance;
	}
	public String getSupplierAsOfDate() {
		if(null==supplierAsOfDate ||supplierAsOfDate.getValue()==null ) {
			return "";
		}
		return supplierAsOfDate.getValue().toString();
	}
	public void setSupplierAsOfDate(LocalDate supplierAsOfDate) {
		if(this.supplierAsOfDate==null) {
			this.supplierAsOfDate=new DatePicker();
		}
		this.supplierAsOfDate.setValue(supplierAsOfDate);
		
	}
	public String getSupplierAccountNo() {
		return supplierAccountNo.get();
	}
	public void setSupplierAccountNo(SimpleStringProperty supplierAccountNo) {
		this.supplierAccountNo = supplierAccountNo;
	}
	
	public String getSupplierGstNo() {
		return supplierGstNo.get();
	}
	public void setSupplierGstNo(SimpleStringProperty supplierGstNo) {
		this.supplierGstNo = supplierGstNo;
	}
	public double getSupplierBillingRate() {
		return supplierBillingRate.get();
	}
	public void setSupplierBillingRate(SimpleDoubleProperty supplierBillingRate) {
		this.supplierBillingRate = supplierBillingRate;
	}
	public String getSupplierOther() {
		return supplierOther.get();
	}
	public void setSupplierOther(SimpleStringProperty supplierOther) {
		this.supplierOther = supplierOther;
	}

	public String getAsOfDateStr() {
		if(supplierAsOfDate==null) {
			asOfDateStr=new SimpleStringProperty("");
		}else {
			asOfDateStr=new SimpleStringProperty(supplierAsOfDate.getValue().toString());
		}
		return asOfDateStr.get();
	}
	
	
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
}

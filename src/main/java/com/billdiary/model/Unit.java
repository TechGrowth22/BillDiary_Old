package com.billdiary.model;

import com.billdiary.entities.UnitEntity;
import com.billdiary.utility.IconGallery;


import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.HBox;

public class Unit {

	private SimpleLongProperty unitId;
	private SimpleStringProperty unitName;
	private Hyperlink delete;
	private HBox action;
	IconGallery iconGallery=new IconGallery();
	
	
	public Unit(UnitEntity unitEntity ) {
		super();
		this.unitId = new SimpleLongProperty(unitEntity.getUnitId());
		this.unitName = new SimpleStringProperty(unitEntity.getName());
		//this.delete=getDelete();
		//this.action=getAction();
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

	public Long getUnitId() {
		return unitId.get();
	}

	public void setUnitId(SimpleLongProperty unitId) {
		this.unitId = unitId;
	}

	public String getUnitName() {
		return unitName.get();
	}

	public void setUnitName(SimpleStringProperty unitName) {
		this.unitName = unitName;
	}

	public HBox getAction() {
		if(action==null)
		{
			delete=getDelete();
			action=new HBox(delete);
			
		}
		return action;
	}

	public void setAction(HBox action) {
		this.action = action;
	}
	
	
	
}

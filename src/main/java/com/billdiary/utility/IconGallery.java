package com.billdiary.utility;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.scene.control.Tooltip;


@Component
@Scope("prototype")
public class IconGallery {
	
	/*public  FontAwesomeIconView fontAwesomeSaveIconView= new FontAwesomeIconView(FontAwesomeIcon.EDIT);
	public  FontAwesomeIconView fontAwesomeTrashIconView= new FontAwesomeIconView(FontAwesomeIcon.TRASH);*/
	public FontAwesomeIconView fontAwesomeSaveIconView;
	public  FontAwesomeIconView fontAwesomeTrashIconView;
	
	public Tooltip saveToolTip;
	public Tooltip deleteToolTip;
	
	public Tooltip getEditToolTip() {
		/*if(null==saveToolTip) {
			saveToolTip=new Tooltip("Save");
		    saveToolTip.setStyle("-fx-text-fill: black;-fx-background-color:linear-gradient(#61a2b1, #2A5058);");
		}*/
		saveToolTip=new Tooltip("Edit");
	    saveToolTip.setStyle("-fx-text-fill: black;-fx-background-color:linear-gradient(#61a2b1, #2A5058);");
		return saveToolTip;
	}
	public void setSaveToolTip(Tooltip saveToolTip) {
		this.saveToolTip = saveToolTip;
	}
	public Tooltip getDeleteToolTip() {
		/*if(null==deleteToolTip) {
			deleteToolTip=new Tooltip("Delete");
			deleteToolTip.setStyle("-fx-text-fill: black;-fx-background-color:linear-gradient(#61a2b1, #2A5058);");	
		}*/
		deleteToolTip=new Tooltip("Delete");
		deleteToolTip.setStyle("-fx-text-fill: black;-fx-background-color:linear-gradient(#61a2b1, #2A5058);");	
		return deleteToolTip;
	}
	public void setDeleteToolTip(Tooltip deleteToolTip) {
		this.deleteToolTip = deleteToolTip;
	}
	public  FontAwesomeIconView getSaveIcon() {
		getFontAwesomeSaveIconView().setSize("1.5em");
		return fontAwesomeSaveIconView;
	}
	public FontAwesomeIconView getDeleteIcon()
	{
		 getFontAwesomeTrashIconView().setSize("1.5em");
		return fontAwesomeTrashIconView;
	}
	public FontAwesomeIconView getFontAwesomeSaveIconView() {
		fontAwesomeSaveIconView=new FontAwesomeIconView(FontAwesomeIcon.EDIT);
		return fontAwesomeSaveIconView;
	}
	public FontAwesomeIconView getFontAwesomeTrashIconView() {
		fontAwesomeTrashIconView= new FontAwesomeIconView(FontAwesomeIcon.TRASH);
		return fontAwesomeTrashIconView;
	}

}

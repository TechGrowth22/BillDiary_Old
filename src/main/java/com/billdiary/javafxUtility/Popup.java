package com.billdiary.javafxUtility;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Popup {
	

	public static void showAlert(String title,String contentText,AlertType alertType) {
		Alert alert = new Alert(alertType);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(contentText);
		alert.showAndWait();
	}
	public static void showErrorAlert(String title,String contentText,AlertType alertType) {
		Alert alert = new Alert(alertType);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(contentText);
		alert.showAndWait();
	}
}

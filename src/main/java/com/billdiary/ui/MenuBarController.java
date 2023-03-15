package com.billdiary.ui;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.billdiary.config.SpringFxmlLoader;
import com.billdiary.utility.Constants;
import com.billdiary.utility.URLS;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Menu;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

@Controller("MenuBarController")
public class MenuBarController {
	//ResourceBundle bundle = ResourceBundle.getBundle("resources.UIResources");
	@Autowired
	private SpringFxmlLoader loader;
	@Autowired
	public LoginController loginController;
	@Autowired
	public HomeController homeController;
	@Autowired
	public LayoutController layoutController;
	@Autowired
	public ShopController shopController;
	
	@FXML Menu rightMenu;
	public MenuBarController() {
		// TODO Auto-generated constructor stub
	}
	@FXML private void addShopDetails()
	{
		AnchorPane addShop=(AnchorPane) loader.load(URLS.ADD_SHOP_DETAILS_PAGE);
		BorderPane root = new BorderPane();
		root.setCenter(addShop);
		layoutController.loadWindow(root,"Add Shop Details",Constants.POPUP_WINDOW_WIDTH,Constants.POPUP_WINDOW_HEIGHT);
		 /*
		Scene scene = new Scene(root,800,600);
			     Stage stage=new Stage();
		 //stage.setMaximized(true);
	     stage.setTitle("Add Shop Details");
	     stage.setMinHeight(600);
	     stage.setMinWidth(800);
	       stage.setScene(scene);
	        stage.show();*/
		shopController.getShopDetails();
		
	        
	}
	/**
	 * Barcode handler 
	 */
	@FXML public void showBarcodeHandler() {
		
	}
	
	/*@FXML private void addProductDetails()
	{
		
		SpringFxmlLoader loader=SpringFxmlLoader.getInstance();
		homeController.showProduct();
		AnchorPane addProduct=(AnchorPane) loader.load(URLS.ADD_PRODUCT_PAGE);
		BorderPane root = new BorderPane();
		root.setCenter(addProduct);
		layoutController.loadWindow(root,"Add Product Details",Constants.POPUP_WINDOW_WIDTH,Constants.POPUP_WINDOW_HEIGHT);
		Scene scene = new Scene(root,800,600);
			     Stage stage=new Stage();
		 //stage.setMaximized(true);
	     stage.setTitle("Add Product Details");
	     stage.setMinHeight(600);
	     stage.setMinWidth(800);
	       stage.setScene(scene);
	        stage.show();
	        
		 
	}*/
	@FXML private void showGSTHandler()
	{
		
		 
	}
	
	@FXML
	public void initialize() {
		// TODO Auto-generated method stub
		/*
		
		id.setCellValueFactory(new PropertyValueFactory<Product,Integer>("id"));
		
		name.setCellValueFactory(new PropertyValueFactory<Product,String>("name"));
		price.setCellValueFactory(new PropertyValueFactory<Product,Float>("price"));
		//balance.setCellValueFactory(new PropertyValueFactory<BillInfo,Float>("balance"));
		tbid.setItems(data);
		*/
	}

	@FXML public void setHindiLanguage(ActionEvent t) {
		
		Locale locale = new Locale("hi");
		//SpringFxmlLoader.setLoc(locale);
		
	}
	
	@FXML public void setEnglishLanguage(ActionEvent t) {
		
		Locale locale = new Locale("en");
		//SpringFxmlLoader.setLoc(locale);
		
	}
	@FXML public void OpenQuickPay()
	{
		AnchorPane quickPay=(AnchorPane) loader.load(URLS.QUICK_PAY_PAGE);
		BorderPane root = new BorderPane();
		root.setCenter(quickPay);
		layoutController.loadWindow(root,"Quick pay",Constants.POPUP_WINDOW_WIDTH,Constants.POPUP_WINDOW_HEIGHT);
		
	}

}

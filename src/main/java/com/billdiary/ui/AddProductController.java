package com.billdiary.ui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.controlsfx.control.textfield.TextFields;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;

import com.billdiary.config.SpringFxmlLoader;
import com.billdiary.javafxUtility.Popup;
import com.billdiary.model.Product;
import com.billdiary.model.Unit;
import com.billdiary.service.GSTService;
import com.billdiary.service.PriceService;
import com.billdiary.service.ProductService;

import com.billdiary.utility.Calculate;
import com.billdiary.utility.Constants;
import com.billdiary.utility.DataTypeConverter;
import com.billdiary.utility.URLS;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;


@Controller("AddProductController")
public class AddProductController  implements Initializable {

	@Autowired
	private ProductService productService;
	
	@Autowired
	SpringFxmlLoader loader;
	
	@Autowired
	GSTService gstService; 
	
	@Autowired 
	private PriceService priceService;
	
	@Autowired
	private LayoutController layoutController;
	
	Product prodModel;
	@FXML Text productCodeLabel;
	@FXML TextField add_productName;
	@FXML TextField productCode;
	@FXML TextField add_prodDesc;
	@FXML TextField productCategory;
	@FXML TextField add_PrdHSNCodes;
	@FXML TextField add_sellPrice;
	@FXML CheckBox sellGST;
	@FXML TextField add_buyPrice;
	@FXML CheckBox buyPriceGST;
	@FXML TextField add_Discount;
	@FXML TextField add_stock;
	@FXML TextField initialStock;
	//@FXML TextField  lowStock;
	@FXML ComboBox<String> buyGSTpercentage;
	@FXML ComboBox<String> sellGSTpercentage;
	@FXML ComboBox<String> units;
	
	List<String> categoryList=new ArrayList<String>();
	List<Unit> unitList=new ArrayList<>();
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		productCodeLabel.setVisible(false);
		getProductCategoryList();
		getUnitList();
		units.getSelectionModel().selectedIndexProperty().addListener((v,oldValue,newValue)->{
			handleUnitSelection();
		});
		//TextFields.bindAutoCompletion(units.getEditor(),units.getItems()).setVisibleRowCount(3);
		TextFields.bindAutoCompletion(productCategory,categoryList).setVisibleRowCount(3);
		
		if(getProdModel()!=null) {
			Product pro=getProdModel();
			add_Discount.setText(Double.toString(pro.getDiscount()));
			add_prodDesc.setText(pro.getDescription());
			add_productName.setText(pro.getName());
			productCode.setText(Long.toString(pro.getProductCode()));
			if(null != initialStock)
		    initialStock.setText(String.valueOf(pro.getStock()));
		    productCategory.setText(pro.getProductCategory()); 
		    add_PrdHSNCodes.setText(pro.getProductHSNCode());
		    units.setValue(pro.getUnit().getUnitName());
		    add_stock.setText(DataTypeConverter.getStringFromDouble(pro.getStock()));
		  //  lowStock.setText(Double.toString(pro.getLowStockThershold()));
		    if(null!=pro.getRetailGST() && "Y".equals(pro.getRetailGST())){
		    	//double retailGSTPrice=priceService.getRetailGSTPrice(pro.getRetailPrice(), pro.getRetailGSTpercentage(), pro.getDiscount());
		    	
		    	double sellGSTPrice=gstService.getGSTIncludedPrice(pro.getSellPrice(), pro.getSellPriceGSTPercentage());
		    	add_sellPrice.setText(Double.toString(sellGSTPrice));
		    	sellGST.setSelected(true);
		    	sellGSTpercentage.setValue(pro.getSellPriceGSTPercentage()+"%");
		    }else {
		    	sellGST.setSelected(false);
		    	add_sellPrice.setText(Double.toString(pro.getSellPrice()));
		    }
		    if(null!=pro.getBuyPriceGST() && "Y".equals(pro.getBuyPriceGST())) {
		    	double buyGSTPrice=priceService.getWholeSaleGSTPrice(pro.getBuyPrice(), pro.getBuyPriceGSTPercentage(), pro.getDiscount());
		    			
		    			
		    	//double wholeSaleGSTPrice=pro.getWholesalePrice();
		    	
		    	add_buyPrice.setText(Double.toString(buyGSTPrice));
		    	buyPriceGST.setSelected(true);
		    	buyGSTpercentage.setValue(pro.getBuyPriceGSTPercentage()+"%");
		    }else {
		    	buyPriceGST.setSelected(false);
		    	add_buyPrice.setText(Double.toString(pro.getBuyPrice()));
		    }	   
		}else {
			long prdCode=productService.getProductCode();
			productCode.setText(Long.toString(prdCode+1)); // productCode+1 cause every time new product will get new product code
		}
	}

	public void getUnitList() {
		unitList=productService.getUnitList();
		units.getItems().clear();
		if(units.getItems().isEmpty()) {
			units.getItems().add("Add Unit");
		    unitList.forEach(unit->units.getItems().add(unit.getUnitName()));
		}
	}

	private void getProductCategoryList() {
		categoryList=productService.getCategoryList();
	}

	
	public void handleUnitSelection() {
		if("Add Unit".equals(units.getValue())) {
			StackPane addUnit = (StackPane) loader.load(URLS.ADD_UNIT);
			BorderPane root = new BorderPane();
			root.setCenter(addUnit);
			StackPane stk=new StackPane();
			stk.getChildren().add(addUnit);
			Stage stage = new Stage();
		    Scene scene = new Scene(stk, Constants.POPUP_UNIT_WINDOW_WIDTH, Constants.POPUP_UNIT_WINDOW_HEIGHT); 
	        stage.setTitle("Add Units");
	        stage.setScene(scene);
	        stage.showAndWait();
		}
	}

	@FXML
	public void addProduct(ActionEvent event){		
		Product prod=new Product();
		if(validateProduct()) {
			
		String productName=add_productName.getText();
		String productDesc=add_prodDesc.getText();
		Double retailPrice=Calculate.getNonEmptyDoubleValue(add_sellPrice.getText());
		Double buyPrice=Calculate.getNonEmptyDoubleValue(add_buyPrice.getText());
		Double discount=Calculate.getNonEmptyDoubleValue(add_Discount.getText());
		if(discount>100.00) {
			discount=0.00;
		}
		double stock=Calculate.getNonEmptyDoubleValue(add_stock.getText());
	//	stock=stock+Calculate.getNonEmptyDoubleValue(initialStock.getText());
		String wholeSaleGSTper=buyGSTpercentage.getValue();
		String retailGSTper=sellGSTpercentage.getValue();
		if(null==wholeSaleGSTper) {
			wholeSaleGSTper="0%";
		}
		if(null==retailGSTper) {
			retailGSTper="0%";
		}
		
		if(buyPriceGST.isSelected()) {
			//wholesalePrice=Calculate.getWholeSalePrice(wholesalePrice,wholeSaleGSTper);
			buyPrice=priceService.getWholeSalePrice(buyPrice, Double.parseDouble(Calculate.trimPercentage(wholeSaleGSTper)), discount);
			
			prod.setBuyPriceGST(new SimpleStringProperty("Y"));
			prod.setBuyPriceGSTPercentage(new SimpleDoubleProperty(Double.parseDouble(Calculate.trimPercentage(wholeSaleGSTper))));
		}else {
			prod.setBuyPriceGST(new SimpleStringProperty("N"));
			prod.setBuyPriceGSTPercentage(new SimpleDoubleProperty(Double.parseDouble(Calculate.trimPercentage(wholeSaleGSTper))));
		}
		if(sellGST.isSelected()) {
			//retailPrice=Calculate.getRetailPrice(retailPrice,retailGSTper,discount);
			//retailPrice=priceService.getRetailPrice(retailPrice, Double.parseDouble(Calculate.trimPercentage(retailGSTper)), discount);
			retailPrice=gstService.getGSTExcludedPrice(retailPrice, Double.parseDouble(Calculate.trimPercentage(retailGSTper)));
			prod.setSellPriceGST(new SimpleStringProperty("Y"));
			prod.setSellPriceGSTPercentage(new SimpleDoubleProperty(Double.parseDouble(Calculate.trimPercentage(retailGSTper))));
		}else{
			prod.setSellPriceGST(new SimpleStringProperty("N"));
			prod.setSellPriceGSTPercentage(new SimpleDoubleProperty(Double.parseDouble(Calculate.trimPercentage(retailGSTper))));
			
		}			
		prod.setName(new SimpleStringProperty(productName));
		prod.setProductCode(new SimpleLongProperty(Long.parseLong(productCode.getText())));
		prod.setDescription(new SimpleStringProperty(productDesc));
		prod.setSellPrice(new SimpleDoubleProperty(Calculate.getFormatedDoubleValue(retailPrice)));
		prod.setBuyPrice(new SimpleDoubleProperty(Calculate.getFormatedDoubleValue(buyPrice)));
		prod.setDiscount(new SimpleDoubleProperty(discount));
		prod.setStock(new SimpleDoubleProperty(stock)); 
		prod.setProductCategory(new SimpleStringProperty(productCategory.getText()));
		prod.setProductHSNCode(new SimpleStringProperty(add_PrdHSNCodes.getText()));	
		prod.setLowStockThershold(new SimpleDoubleProperty(new Double(0.0)));
		Unit pUnit=unitList.stream()
		.filter(unit -> (unit.getUnitName()).equals(units.getValue())).findAny()
		.orElse(null);
		prod.setUnit(pUnit);
			if(getProdModel()!=null)
			{
				prod.setProductId(new SimpleIntegerProperty(getProdModel().getProductId()));
				productService.updateProduct(prod);
				
				
			}
			else
			productService.addProduct(prod);
			((Node)(event.getSource())).getScene().getWindow().hide();
			ApplicationContext applicationContext=loader.getApplicationcontext();
			ManageProductController  manageProductController=( ManageProductController)applicationContext.getBean("ManageProductController");
			if(null != manageProductController)
				manageProductController.getRefreshedTable();
			
		
			PurchaseOrderController  purchaseOrderController=( PurchaseOrderController)applicationContext.getBean("PurchaseOrderController");
			if(null != purchaseOrderController)
				purchaseOrderController.getRefreshedProducts();
			}
	}
	
	
	public Double getDoubleNumber(String text) {
		try {
			return Double.parseDouble(text);
		}catch(Exception e) {
			return Double.valueOf("0");
		}
	}

	private boolean validateProduct() {
		boolean flag=true;
		try{
			long code=Long.parseLong(productCode.getText());
			if(!productCode.getText().isEmpty()) {
				if(getProdModel()!=null) {
					if(!(getProdModel().getProductCode()==code)) {
						boolean codeExits=productService.checkProductCode(code);
						if(codeExits) {
							productCodeLabel.setVisible(true);
							flag=false;
						}else {
							productCodeLabel.setVisible(false);
							flag=true;
						}
					}
				}else {
					boolean codeExits=productService.checkProductCode(code);
					if(codeExits) {
						productCodeLabel.setVisible(true);
						flag=false;
					}else {
						productCodeLabel.setVisible(false);
						flag=true;
					}
				}
			}
		}catch(Exception e) {
			Popup.showErrorAlert(Constants.ERROR_TITLE,"Please enter a product code as a number",AlertType.ERROR);
			flag=false;
		}
		if(null==add_productName.getText() || null== units.getValue() || null==productCode.getText()) {
			Popup.showErrorAlert(Constants.ERROR_TITLE,Constants.ERROR_COMMON_VALIDATION,AlertType.ERROR);
			flag=false;
		}
		else if(add_productName.getText().isEmpty() || productCode.getText().isEmpty()) {
			Popup.showErrorAlert(Constants.ERROR_TITLE,Constants.ERROR_COMMON_VALIDATION,AlertType.ERROR);
			flag=false;
		}
		return flag;
	}


	public Product getProdModel() {
		return prodModel;
	}


	public void setProdModel(Product prodModel) {
		this.prodModel = prodModel;
	}
}

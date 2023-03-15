package com.billdiary.ui;


import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.billdiary.config.SpringFxmlLoader;
import com.billdiary.javafxUtility.Popup;
import com.billdiary.model.Address;
import com.billdiary.model.Product;
import com.billdiary.model.Supplier;
import com.billdiary.service.PriceService;
import com.billdiary.service.ProductService;
import com.billdiary.service.SupplierService;
import com.billdiary.utility.Constants;
import com.billdiary.utility.URLS;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.util.converter.DoubleStringConverter;

@Controller("PurchaseOrderController")
public class PurchaseOrderController implements Initializable{
	
	
	final static Logger LOGGER = LoggerFactory.getLogger(PurchaseOrderController.class); 
	
	@Autowired
	SupplierService supplierService;
	@Autowired
	private SpringFxmlLoader loader;
	@Autowired
	ProductService productService;
	@Autowired
	AddProductController addProductController;
	@Autowired
	public LayoutController layoutController;
	@Autowired
	PriceService priceService;
	
	
	@FXML TextField totalAmount;
	@FXML TextField supplierIdName;
	@FXML DatePicker orderDate;
	@FXML TextArea address;
	@FXML TextField mobileNo;
	@FXML TextField city;
	@FXML TextField gstNo;
	@FXML TextField productCodeName;
	@FXML TextField Quantity;
	@FXML TextField buyPriceField;
	@FXML TableView<Product> productTable;
	@FXML TableColumn<Product, Double> productQuantity;
	@FXML TableColumn<Product, Double> buyPrice;
	@FXML TableColumn<Product, Double> totalPrice;
	
	
	public Supplier selectedSupplier;
	public List<Supplier> supplierList=new ArrayList<>();
	public List<String> supplierNameList=new ArrayList<>();
	public List<Product> productList=new ArrayList<>();
	public List<String> productNameList=new ArrayList<>();
	private ObservableList<Product> data = FXCollections.observableArrayList();
	public Product selectedProduct;
	
	Task<Void> fetchProducts = null;
	AutoCompletionBinding<String> acb = null;
	
	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	
		supplierList.clear();
		supplierNameList.clear();
		productList.clear();
		productNameList.clear();
		
		try {
			Task<Void> fetchSuppliers = new Task<Void>() {
			    @Override public Void call() {
			    	supplierList=supplierService.fetchSuppliers();
			    	for(Supplier supplier:supplierList) {
						supplierNameList.add(supplier.getSupplierID()+" : "+supplier.getSupplierName());
					}
					TextFields.bindAutoCompletion(supplierIdName, supplierNameList).setVisibleRowCount(5);
			        return null;
			    }
			};
			fetchProducts = new Task<Void>() {
			    @Override public Void call() {
			    	refreshProducts();
			    	
			    	
			        return null;
			    }
			};
			
			
			
			new Thread(fetchSuppliers).start();
			new Thread(fetchProducts).start();

			
			productTable.setItems(data);
			Quantity.setText("1");
			orderDate.setValue(LocalDate.now());
			supplierIdName.focusedProperty().addListener((ov, oldV, newV) -> {
				if (!newV) {
					selectSupplier();
				}
			});
			productCodeName.focusedProperty().addListener((ov, oldV, newV) -> {
				if (!newV) {
					Quantity.setText(Integer.toString(1));
					String product = productCodeName.getText();
					if (null != product) {
						Product prd = productList.stream().filter(x -> (x.getProductCode() + " : " + x.getName()).equals(product))
								.findAny().orElse(null);
						if (null != prd) {
							double buyPri=priceService.getWholeSaleGSTPrice(prd.getBuyPrice(),prd.getBuyPriceGSTPercentage(),0.0);
							buyPriceField.setText(Double.toString(buyPri));
							selectedProduct = prd;
						}

					}
				}
			});
			productQuantity.setCellFactory(TextFieldTableCell.<Product, Double>forTableColumn(new  DoubleStringConverter()));
			totalPrice.setCellFactory(TextFieldTableCell.<Product, Double>forTableColumn(new DoubleStringConverter()));
			buyPrice.setCellFactory(TextFieldTableCell.<Product, Double>forTableColumn(new DoubleStringConverter()));
		}catch(Exception e) {
			LOGGER.error(e.getMessage(),e);
		}
	}
	
	public void refreshProducts() {
		productList.clear();
    	productList=productService.fetchProducts();
    	
    	for(Product product:productList) {
    		productNameList.add(product.getProductCode()+" : "+product.getName());
		}
    	if(null != acb)
    		acb.dispose();
    	
    	 acb = TextFields.bindAutoCompletion(productCodeName, productNameList);
    	 acb.setVisibleRowCount(5);
	}
	
	private void selectSupplier() {
		String supp=supplierIdName.getText();
		if (null != supp) {
			selectedSupplier = supplierList.stream()
					.filter(x -> (x.getSupplierID()+" : "+x.getSupplierName()).equals(supp)).findAny()
					.orElse(null);
			if (null != selectedSupplier) {
				Address addr=selectedSupplier.getAddress();
				address.setText(addr.getStreet1());
				mobileNo.setText(selectedSupplier.getSupplierMobileNo());
				city.setText(addr.getCity());
				gstNo.setText(selectedSupplier.getSupplierGstNo());
			}
		}
		
	}
	
	@FXML public void addProductToTable() {
		if(null!=selectedProduct) {
			Product prd=new Product();
			prd=(Product)selectedProduct.clone();
			prd.setQuantity(new SimpleDoubleProperty(0.0));
			if(null!=Quantity.getText() && !Quantity.getText().isEmpty()) {
				double quant=Double.parseDouble(Quantity.getText());
				double wholeSalePri=Double.parseDouble(buyPriceField.getText());
				double totalPrice=wholeSalePri*quant;
				prd.setQuantity(new SimpleDoubleProperty(quant));
				prd.setTotalPrice(new SimpleDoubleProperty(totalPrice));
				prd.setBuyPrice(new SimpleDoubleProperty(wholeSalePri));
				
			}
			data.add(prd);
			int index=data.size()-1;
			prd.getDelete().setOnAction(e->deleteButtonClickedThroughHyperlink(index));
			}
			
			productTable.setItems(data);
			
			calculateTotalAmount();
			
			productCodeName.clear();
			Quantity.clear();
			buyPriceField.clear();
			productCodeName.requestFocus();
			selectedProduct=null;
		}
	private Object deleteButtonClickedThroughHyperlink(int index) {
		data.remove(index);
		calculateTotalAmount();
		return null;
	}
	

	private void calculateTotalAmount() {
		double total=0.00;
		for(Product prd:data) {
			total=total+prd.getTotalPrice();
		}
		totalAmount.setText(String.valueOf(total));
	}

	@FXML public void showAddProduct() {
		addProductController.setProdModel(null);
		StackPane addProduct=(StackPane)  loader.load(URLS.ADD_PRODUCT_PAGE);
		BorderPane root = new BorderPane();
		root.setCenter(addProduct);
		layoutController.loadWindow(root,"Add Product Details",Constants.POPUP_WINDOW_WIDTH,Constants.POPUP_WINDOW_HEIGHT);
		
	}
	@FXML
	public void handleKeyAction(KeyEvent event) {
		
		final KeyCombination keyComb = new KeyCodeCombination(KeyCode.P, KeyCombination.CONTROL_DOWN);

		if ((KeyCode) event.getCode() == KeyCode.ENTER) {
			try {
				addProductToTable();
			} catch (Exception e) {
				LOGGER.error(e.getMessage(),e);
				Popup.showErrorAlert(Constants.ERROR_TITLE,Constants.ERROR_COMMON_WRONG,AlertType.ERROR);
			}
		}
	}
	
	/**
	 * this function invokes when user clicks on save & print button
	 * @param event
	 */
	@FXML
	public void purchaseOrderSaveAndPrint(ActionEvent event) {
		LOGGER.debug("purchaseOrderSaveAndPrint");
		
		if(savePurchaseOrder()) {
			/*System.out.println("Invoice saved");
			createPDF();
			clearAllFields();
			LOGGER.info("Invoice saved & PDF Generated");*/
			clearAllFields();
			LOGGER.info("purchaseOrder saved");
						
		}else {
			LOGGER.debug("purchaseOrder not saved");
			Popup.showAlert(Constants.INVOICE_TITLE,Constants.INVOICE_UNSUCCESSFULL_STATUS,AlertType.INFORMATION);
		}
		
	}

	
	private boolean savePurchaseOrder() {
		boolean savePurchaseOrder=false;
		
		for(Product product:data) {
			double stock=product.getStock()+product.getQuantity();
			if(stock>=0) {
				product.setStock(new SimpleDoubleProperty(stock));
				int stockUpdated=productService.purchaseProductStock(product.getProductId(),product.getQuantity());
			}
		}
		
		savePurchaseOrder=true;
		
		return savePurchaseOrder;
	}
	
	/*
	 *For clearing all fields fron screen  
	 */
	private void clearAllFields() {
		
		totalAmount.clear();
		supplierIdName.clear();
		address.clear();
        mobileNo.clear();
		city.clear();
		gstNo.clear();
		productCodeName.clear();
		Quantity.clear();
		buyPriceField.clear();
		data.clear();
		
	}

	public void getRefreshedProducts() {
		try {
			productList.clear();
			productNameList.clear();
			refreshProducts();
		}catch(Exception e) {
			LOGGER.error("Exception occured at refreshProductTable", e);
		}
	}

	

}

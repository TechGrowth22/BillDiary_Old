package com.billdiary.ui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.billdiary.config.SpringFxmlLoader;
import com.billdiary.model.Product;
import com.billdiary.service.ProductService;
import com.billdiary.utility.Constants;
import com.billdiary.utility.URLS;

import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

@Controller("HomeController")
public class HomeController implements Initializable {
	
	final static Logger LOGGER = LoggerFactory.getLogger(PurchaseOrderController.class);
	
	@Autowired
	SpringFxmlLoader loader;
	@Autowired
	public HomeController homeController;
	@Autowired
	public LayoutController layoutController;
	@Autowired
	public ProductService productService;

	//@FXML public Text MainTitle;
	public  StackPane MainStage= new StackPane();
	AnchorPane mainInnerWindow;
	
	
	@FXML public StackPane homepage;
	@FXML public  BorderPane borderpane;
	@FXML public AnchorPane mainView;
	@FXML public AnchorPane lowStocks;
	@FXML public Text mainViewTitle;
	@FXML public Text lowstk;
	@FXML public TextFlow notificationTextFlow;
	@FXML Text trylanguage;
	
	@FXML public AnchorPane mainAnchorPane;
	
	
	public void initialize(URL arg0, ResourceBundle arg1){
		/*StringBuffer sb=new StringBuffer("");
		Task<Void> getLowStockProducts = new Task<Void>() {
		    @Override public Void call() {
		    	List<Product> productList=new ArrayList<>();
		    	productList=productService.getLowStockProducts();
		    	System.out.println("Low Stock products :"+productList.size());
				productList.forEach(product->{
					sb.append(product.getProductId()+" : "+product.getName()+"\n");
				});
				lowstk.setText(sb.toString());
		        return null;
		    }
		};
		new Thread(getLowStockProducts).start();*/
		
	}
	
	
	@FXML public void showDashBoard()
	{
		LOGGER.debug("Entering Class HomeController : method :showDashBoard");
		try{
		StackPane dashBoard=(StackPane)loader.load(URLS.DASHBOARD_PAGE);
		mainAnchorPane.getChildren().clear();
		mainAnchorPane.getChildren().add(dashBoard);
		AnchorPane.setTopAnchor(dashBoard, 0.0);
		AnchorPane.setBottomAnchor(dashBoard, 0.0);
		AnchorPane.setLeftAnchor(dashBoard, 0.0);
		AnchorPane.setRightAnchor(dashBoard, 0.0);
		mainViewTitle.setText("DashBoard");
		}catch(Exception e)
		{
			LOGGER.error("Entering Class HomeController : method :showDashBoard "+e.getMessage());
		}
	}
	
	@FXML public void showProduct()
	{
		LOGGER.debug("Entering Class HomeController : method :showProduct");
		try{
			
		StackPane manageProduct2=(StackPane)loader.load(URLS.MANAGE_PRODUCT_PAGE);
		mainAnchorPane.getChildren().clear();
		mainAnchorPane.getChildren().add(manageProduct2);
		AnchorPane.setTopAnchor(manageProduct2, 0.0);
		AnchorPane.setBottomAnchor(manageProduct2, 0.0);
		AnchorPane.setLeftAnchor(manageProduct2, 0.0);
		AnchorPane.setRightAnchor(manageProduct2, 0.0);
		}
		catch(Exception e)
		{
			LOGGER.error("Entering Class HomeController : method :showProduct "+e.getMessage());
		}
		
	}
	
	@FXML public void showCustomer()
	{
	 	LOGGER.debug("Entering Class HomeController : method :showCustomer");
		try{
			StackPane manageCustomer2=(StackPane)loader.load(URLS.MANAGE_CUSTOMER_PAGE);
			mainAnchorPane.getChildren().clear();
			mainAnchorPane.getChildren().add(manageCustomer2);
			AnchorPane.setTopAnchor(manageCustomer2, 0.0);
			AnchorPane.setBottomAnchor(manageCustomer2, 0.0);
			AnchorPane.setLeftAnchor(manageCustomer2, 0.0);
			AnchorPane.setRightAnchor(manageCustomer2, 0.0);
		}
		catch(Exception e)
		{
			LOGGER.error("Exception Occured in HomeController : method :showCustomer "+e.getMessage());
		}
		
	}
	
	
	@FXML public void showSupplier()
	{
		LOGGER.debug("Entering Class HomeController : method :showSupplier");
		try{
			StackPane manageSupplier=(StackPane)loader.load(URLS.MANAGE_SUPPLIER);
			mainAnchorPane.getChildren().clear();
			mainAnchorPane.getChildren().add(manageSupplier);
			AnchorPane.setTopAnchor(manageSupplier, 0.0);
			AnchorPane.setBottomAnchor(manageSupplier, 0.0);
			AnchorPane.setLeftAnchor(manageSupplier, 0.0);
			AnchorPane.setRightAnchor(manageSupplier, 0.0);
		}
		catch(Exception e)
		{
			LOGGER.error("Entering Class HomeController : method :showSupplier "+e.getMessage());
		}
	}
	@FXML public void showInvoice()
	{
		LOGGER.info("Entering Class HomeController : method :showInvoice");
		try{
			StackPane manageInvoice = (StackPane) loader.load(URLS.MANAGE_INVOICE);
			BorderPane root = new BorderPane();
			root.setCenter(manageInvoice);
			layoutController.loadWindow(root, "Invoice", Constants.INVOICE_WINDOW_WIDTH,
					Constants.INVOICE_WINDOW_HEIGHT);
		}
		catch(Exception e)
		{
			LOGGER.error("Entering Class HomeController : method :showInvoice "+e.getMessage());
		}
		
	}
	
	@FXML public void showInvoiceHistory()
	{
		LOGGER.debug("Entering Class HomeController : method :showInvoiceHistory");
		try{
			
		StackPane invoiceHistory=(StackPane)loader.load(URLS.INVOICE_HISTORY);
		mainAnchorPane.getChildren().clear();
		mainAnchorPane.getChildren().add(invoiceHistory);
		AnchorPane.setTopAnchor(invoiceHistory, 0.0);
		AnchorPane.setBottomAnchor(invoiceHistory, 0.0);
		AnchorPane.setLeftAnchor(invoiceHistory, 0.0);
		AnchorPane.setRightAnchor(invoiceHistory, 0.0);
		}
		catch(Exception e)
		{
			LOGGER.error("Entering Class HomeController : method :showInvoiceHistory "+e.getMessage());
		}
		
	}
	
	@FXML public void showBarcodeController()
	{
		LOGGER.debug("Entering Class HomeController : method :showBarcodeController");
		try{
			
		StackPane invoiceHistory=(StackPane)loader.load(URLS.BARCODE_CONTROLLER);
		mainAnchorPane.getChildren().clear();
		mainAnchorPane.getChildren().add(invoiceHistory);
		AnchorPane.setTopAnchor(invoiceHistory, 0.0);
		AnchorPane.setBottomAnchor(invoiceHistory, 0.0);
		AnchorPane.setLeftAnchor(invoiceHistory, 0.0);
		AnchorPane.setRightAnchor(invoiceHistory, 0.0);
		}
		catch(Exception e)
		{
			LOGGER.error("Entering Class HomeController : method :showBarcodeController "+e.getMessage());
			e.printStackTrace();
		}
		
	}
	
	@FXML public void showPurchaseOrder()
	{
		LOGGER.info("Entering Class HomeController : method :showInvoice");
		try{
			StackPane purchaseOrder=(StackPane)loader.load(URLS.PURCHASE_ORDER);
			mainAnchorPane.getChildren().clear();
			mainAnchorPane.getChildren().add(purchaseOrder);
			AnchorPane.setTopAnchor(purchaseOrder, 0.0);
			AnchorPane.setBottomAnchor(purchaseOrder, 0.0);
			AnchorPane.setLeftAnchor(purchaseOrder, 0.0);
			AnchorPane.setRightAnchor(purchaseOrder, 0.0);
		}
		catch(Exception e)
		{
			LOGGER.error("Entering Class HomeController : method :showInvoice "+e.getMessage());
		}
		
	}
	
	
	
	/*public  StackPane getRoot() {
		return homepage;
	}
	@FXML private void manageProducts(ActionEvent event)
	{
		 mainInnerWindow=(AnchorPane) loader.load(URLS.MANAGE_PRODUCT_PAGE);
		//MainTitle.setText("Manage Products");
		if(setMainView())
		{
			System.out.println("Manage Products window loaded successfully");
		}else {
			System.out.println("while loading Manage Products window some error has been occured ");
		}	
	}
	
	@FXML private void manageCustomers(ActionEvent event)
	{
		mainInnerWindow=(AnchorPane) loader.load(URLS.MANAGE_CUSTOMER_PAGE);
		if(setMainView())
		{
			System.out.println("Manage Customer window loaded successfully");
		}else {
			System.out.println("while loading Manage Customer window some error has been occured ");
		}
	}
	
	public void createMainStage(StackPane mainView)
	{
		layoutController.loadWindow(MainStage, Constants.APPLICATION_TITLE,Constants.WINDOW_WIDTH,Constants.WINDOW_HEIGHT);
	}
	
	private boolean setMainView()
	{
		boolean windowShow=false;
		try
		{
		mainView.getChildren().clear();
		
		mainInnerWindow.setLayoutX(mainView.getLayoutX());
		mainInnerWindow.setLayoutY(mainView.getLayoutY());
		
		System.out.println("new view"+mainInnerWindow.getWidth()+ " "+mainInnerWindow.getHeight());
		System.out.println("mainview"+mainView.getWidth()+ " "+mainView.getHeight());
		
		mainInnerWindow.setMaxHeight(mainView.getHeight());
		mainInnerWindow.setMaxWidth(mainView.getWidth());
		
		mainInnerWindow.resize(mainView.getWidth(), mainView.getHeight());
		System.out.println("new view"+mainInnerWindow.getWidth()+ " "+mainInnerWindow.getHeight()+" :"+mainInnerWindow.computeAreaInScreen());
		System.out.println("mainview:"+mainView.computeAreaInScreen());
		mainInnerWindow.setLayoutX(mainView.getLayoutX());
		mainInnerWindow.setLayoutY(mainView.getLayoutY());
		
		
		AnchorPane.setTopAnchor(mainInnerWindow, 0.0);
        AnchorPane.setRightAnchor(mainInnerWindow, 0.0);
        AnchorPane.setLeftAnchor(mainInnerWindow, 0.0);
        AnchorPane.setBottomAnchor(mainInnerWindow, 0.0);
		
		mainView.getChildren().add(mainInnerWindow);
		mainInnerWindow.resize(mainView.getWidth(), mainView.getHeight());
		mainView.autosize();
		
		
		
		windowShow=true;
		}catch(Exception e)
		{
			windowShow=false;
			System.out.println(e.getMessage());
		}
		return windowShow;
	}
	*/
}

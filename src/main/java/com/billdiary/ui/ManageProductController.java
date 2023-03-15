package com.billdiary.ui;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.billdiary.config.SpringFxmlLoader;
import com.billdiary.javafxUtility.Popup;
import com.billdiary.model.Product;
import com.billdiary.service.ProductService;
import com.billdiary.utility.Constants;
import com.billdiary.utility.URLS;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.LongStringConverter;

@Controller("ManageProductController")
public class ManageProductController implements Initializable{
	
	final static Logger LOGGER = LoggerFactory.getLogger(ManageProductController.class);
	
	@Autowired
	private SpringFxmlLoader loader;
	
	@Autowired
	public LayoutController layoutController;
	
	@Autowired
	AddProductController addProductController;
	
	@Autowired
	private ProductService productService;
	
	List<Product> productList=new ArrayList<>();
	@FXML TextField productName;
	@FXML TextField productId;
	
    private ObservableList < Product > data = FXCollections.observableArrayList();
 
    @FXML private TableView < Product > ProductTable;
    @FXML TableColumn<Product,Long>productCode;
	@FXML TableColumn<Product,Double>BuyPrice;
	@FXML TableColumn<Product,Double>SellPrice;
	@FXML TableColumn<Product,Double>Discount;
	@FXML TableColumn<Product,Double>Stock;
	
	@FXML Pagination pagination;
	private static int pages;
	private static int index=0;
	private static long count=0;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{	
		try {
		
		productCode.setCellFactory(TextFieldTableCell.<Product,Long>forTableColumn(new LongStringConverter()));
		SellPrice.setCellFactory(TextFieldTableCell.<Product,Double>forTableColumn(new DoubleStringConverter()));
		BuyPrice.setCellFactory(TextFieldTableCell.<Product,Double>forTableColumn(new DoubleStringConverter()));
		Discount.setCellFactory(TextFieldTableCell.<Product,Double>forTableColumn(new DoubleStringConverter()));
		Stock.setCellFactory(TextFieldTableCell.<Product,Double>forTableColumn(new DoubleStringConverter()));		
		count=productService.getProductCount();
		Task<Void> showTable = new Task<Void>() {
		    @Override public Void call() {
        		pages=getPages(count);
        		LOGGER.debug("Total pages: " + pages + "Total product count: "+ count);
            	updateTable(pages,index,Constants.rowsPerPage);
		        return null;
		    }
		};
		new Thread(showTable).start();
		pagination.setPageCount(getPages(count));
		pagination.currentPageIndexProperty().addListener((obs, oldIndex, newIndex) -> 
			{
				updateTable(pages, newIndex.intValue(),Constants.rowsPerPage);
			}
		);	
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * 
	 * All pagination related funtions
	 * 
	 */
	
	public int getPages(long count) {
		return (int)(((count-1)/Constants.rowsPerPage)+1);
	}
	
	public void refreshPagination() {
		count=productService.getProductCount();
		pages=getPages(count);
		pagination.setPageCount(pages);
		LOGGER.debug("refreshPagination completed");
	}
	
	public void updateTable(int pages, int index,int rowsPerPage) {
		List<Product> products=productService.getProducts(pages, index, rowsPerPage);	
		data.clear();
		if(data.isEmpty())
		{
	        for(Product prods:products)
	        {
	        	data.add(prods);
	        	int pid=prods.getProductId();
	        	int rowIndex=data.indexOf(prods);
	        	prods.getDelete().setOnAction(e->deleteButtonClickedThroughHyperlink(pid,rowIndex));
	        	prods.getEdit().setOnAction(e->editButtonClickedThroughHyperlink(pid,rowIndex));
	        }
	     }
		ProductTable.setItems(data);	
	}
		
	/**
	 * This function invoked when user press edit hyperlink from UI
	 * @param productId
	 * @param rowIndex
	 */
	public void editButtonClickedThroughHyperlink(int productId,int rowIndex) {
		
		Product product=data.get(rowIndex);
		product.setProductId(new SimpleIntegerProperty(productId));
	    addProductController.setProdModel(product);
		StackPane addShop = (StackPane) loader.load(URLS.ADD_PRODUCT_PAGE);
		BorderPane root = new BorderPane();
		root.setCenter(addShop);
		layoutController.loadWindow(root, "Edit Product Details", Constants.POPUP_WINDOW_WIDTH,
				Constants.POPUP_WINDOW_HEIGHT);
		
	}
	
	/**
	 * This function invoked when user clicks on delete hyperlink
	 * @param productId
	 * @param rowIndex
	 */
	@SuppressWarnings("restriction")
	public void deleteButtonClickedThroughHyperlink(int productId,int rowIndex)
	{
		LOGGER.info("Inside DeleteButtonClicked");
		if(productService.deleteProduct(productId)) {
			refreshPagination();
			if(rowIndex==0 && !(pagination.getCurrentPageIndex()==Constants.ZERO)) {
				pagination.setCurrentPageIndex(pagination.getCurrentPageIndex()-1);
			}else {
				updateTable(pages, pagination.getCurrentPageIndex(),Constants.rowsPerPage);
			}
		}else {
			Popup.showAlert(Constants.ERROR_TITLE, "Product can not be deleted cause it has some other relation with sold product", AlertType.ERROR);
		}
		
		
		
	}
	/**
	 * This funtion invoke onClick of Add Product Buttons
	 */
	@FXML public void addNewProduct()
	{
		addProductController.setProdModel(null);
		StackPane addProduct=(StackPane)  loader.load(URLS.ADD_PRODUCT_PAGE);
		BorderPane root = new BorderPane();
		root.setCenter(addProduct);
		layoutController.loadWindow(root,"Add Product Details",Constants.POPUP_WINDOW_WIDTH,Constants.POPUP_WINDOW_HEIGHT);
	}
	
	@FXML public void deleteButtonClicked() throws Exception
	{
		
		System.out.println("Inside DeleteButtonClicked");
		ObservableList< Product> ListItems,SelectedListItem;
		ListItems=ProductTable.getItems();
		SelectedListItem=ProductTable.getSelectionModel().getSelectedItems();	
		int id=SelectedListItem.get(0).getProductId();
		boolean productDeleted=false;
		productDeleted=productService.deleteProduct(id);
		if(productDeleted)
		{
			System.out.println("Product Deleted");
		}
		else {
			System.out.println("Product not Deleted");
		}
		getRefreshedTable();
		
	}
	
	@FXML public void saveProduct() throws Exception
	{
		 ObservableList < Product> ObproductList =  ProductTable.getSelectionModel().getSelectedItems();
		System.out.println(ObproductList.get(0).getDescription());
		if(ObproductList!=null)
		{
			productService.saveProduct(ObproductList);
			productList.clear();
			data.clear();
			ProductTable.setItems(data);
			getRefreshedTable();	
		}
	}
	@FXML public void searchProduct()
	{
		LOGGER.info("Inside searchProduct()");
		ObservableList<Product> data = FXCollections.observableArrayList();
		List<Product> obj = productService.fetchProducts();
		int size = obj.size();
		String ProdName = productName.getText();
		if(productId.getText().equals(""))
		{
			productId.setText("0");
		}
		Integer ProdId = Integer.parseInt(productId.getText());
		System.out.println("prod name is:"+productName.getText());
		boolean search=true;
		int count=0;
		
		for(Product pd:obj)
		{	
			count++;
			if(ProdName.equals("") && ProdId==0)
			{	System.out.println("inside if");
				initialize(null, null);
				search=false;
				break;
			}
			else if(!ProdName.equals("") && ProdId!=0)
			{
				search=false;
			}
			
			System.out.println(Integer.parseInt(productId.getText()));
			if(ProdId==(pd.getProductId()) && ProdName.toLowerCase().equals(pd.getName().toLowerCase()))
			{
				data.add(pd);
				ProductTable.setItems(data);
				int pid=pd.getProductId();
				int rowIndex=data.indexOf(pd);
				pd.getDelete().setOnAction(e->deleteButtonClickedThroughHyperlink(pid,rowIndex));
				search=false;
				break;
			}
			if(count>=size)
			{
				System.out.println("inside else");
				ObservableList< Product> ListItems;
				ListItems=ProductTable.getItems();
				System.out.println(ListItems.size());
				while(ListItems.size()!=0)
				{
					ListItems.remove(0);
				}	
			}
		}	
		if(search == true)
		{	
			count = 0;
			for(Product pd:obj)
			{
				if(ProdId ==(pd.getProductId())|| ProdName.toLowerCase().equals(pd.getName().toLowerCase()))
				{
					data.add(pd);
					ProductTable.setItems(data);
					int pid=pd.getProductId();
					int rowIndex=data.indexOf(pd);
					pd.getDelete().setOnAction(e->deleteButtonClickedThroughHyperlink(pid,rowIndex));
					count++;
					search=false;	
				}
				if(count>=obj.size()-1&& search ==true)
				{
					ObservableList< Product> ListItems;
					ListItems=ProductTable.getItems();
					while(ListItems.size()!=0)
					{
						ListItems.remove(0);
					}
				}
			}
		}
		productId.setText("");
		productName.setText("");	
		LOGGER.info("Exiting from  searchProduct()");
	}
	
	public void getRefreshedTable()
	{
		try {
			productList.clear();
			data.clear();
			if(null != pagination) {
				refreshPagination();
				updateTable(pages, pagination.getCurrentPageIndex(),Constants.rowsPerPage);
			}
			
		}catch(Exception e) {
			LOGGER.error("Exception occured at refreshProductTable", e);
		}
		
	}
	
	
	
	@FXML private <T>void setEditedValue(CellEditEvent<Product,T> event)
	{
		if("ProductName".equals(event.getTableColumn().getId())) {
			String ProductName=event.getNewValue().toString();
			event.getTableView().getItems().get(event.getTablePosition().getRow()).setName(new SimpleStringProperty(ProductName));
		}
		if("ProductDesc".equals(event.getTableColumn().getId())) {
			String ProductDesc=event.getNewValue().toString();
			event.getTableView().getItems().get(event.getTablePosition().getRow()).setDescription(new SimpleStringProperty(ProductDesc));
		}
		if("BuyPrice".equals(event.getTableColumn().getId())) {
			Double wholesalePrice=(Double)event.getNewValue();
			event.getTableView().getItems().get(event.getTablePosition().getRow()).setBuyPrice(new SimpleDoubleProperty(wholesalePrice));
			BuyPrice.setCellFactory(TextFieldTableCell.<Product,Double>forTableColumn(new DoubleStringConverter()));
			
		}
		if("SellPrice".equals(event.getTableColumn().getId())) {
			Double retailPrice=(Double)event.getNewValue();
			event.getTableView().getItems().get(event.getTablePosition().getRow()).setSellPrice(new SimpleDoubleProperty(retailPrice));
			SellPrice.setCellFactory(TextFieldTableCell.<Product,Double>forTableColumn(new DoubleStringConverter()));
		}
		if("Discount".equals(event.getTableColumn().getId())) {
			Double discount=(Double)event.getNewValue();
			event.getTableView().getItems().get(event.getTablePosition().getRow()).setDiscount(new SimpleDoubleProperty(discount));
			Discount.setCellFactory(TextFieldTableCell.<Product,Double>forTableColumn(new DoubleStringConverter()));	
		
		}
		if("Stock".equals(event.getTableColumn().getId())) {
			Integer stock=(Integer)event.getNewValue();
			event.getTableView().getItems().get(event.getTablePosition().getRow()).setStock(new SimpleDoubleProperty(stock));
			Stock.setCellFactory(TextFieldTableCell.<Product,Double>forTableColumn(new DoubleStringConverter()));	
		}	
	}
	
	@FXML public void createProductTemplate() {
		String message = productService.createProductTemplate();
		Popup.showAlert(Constants.PRODUCT_TEMPLATE,message,AlertType.INFORMATION);
	}
	
	@FXML 
	public void uploadProducts() {
		String message="";
		Stage stage = new Stage();
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Resource File");
		fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
		File file = fileChooser.showOpenDialog(stage);
          if (file != null) {
        	LOGGER.info(file.getAbsolutePath()+ " is being uploaded");
        	message = productService.uploadProducts(file.toPath());
          }
          getRefreshedTable();
          Popup.showAlert(Constants.PRODUCT_UPLOAD, message, AlertType.INFORMATION);
	}
	
	@FXML public void refresh() {
		   getRefreshedTable();
	}
}
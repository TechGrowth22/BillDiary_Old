package com.billdiary.ui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;

import com.billdiary.config.SpringFxmlLoader;
import com.billdiary.model.Customer;
import com.billdiary.model.Product;
import com.billdiary.model.Supplier;
import com.billdiary.service.SupplierService;
import com.billdiary.utility.Constants;
import com.billdiary.utility.URLS;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.util.converter.DoubleStringConverter;

@Controller("ManageSupplierController")
public class ManageSupplierController implements Initializable {

	
	@Autowired
	private SpringFxmlLoader loader;
	@Autowired
	public LayoutController layoutController;
	
	@Autowired
	public SupplierService supplierService;
	
	@FXML
	TextField supplierName;
	@FXML
	TextField supplierID;
	@FXML
	TextField suppliermobileNo;
	
	@FXML
	private TableView<Supplier> supplierTable;
	@FXML
	TableColumn<Supplier, Double> supplierUnpaidBalance;
	@FXML
	TableColumn<Supplier, Double> supplierBillingRate;
	
	List<Supplier> supplierList = new ArrayList<>();
	
	@FXML Pagination pagination;
	private static int pages;
	private static int index=0;
	private static long count=0;
	
	
	private ObservableList<Supplier> data = FXCollections.observableArrayList();
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	
		/*supplierTable.setItems(data);
		System.out.println("f");
		populate(retrieveData());
		System.out.println("ff");*/
		count=supplierService.getSupplierCount();
		Task<Void> showTable = new Task<Void>() {
		    @Override public Void call() {
		    	//count=productService.getProductCount();
        		pages=getPages(count);
        		System.out.println("pages:"+ pages+"count: "+count );
            	updateTable(pages,index,Constants.rowsPerPage);
		        return null;
		    }
		};
		new Thread(showTable).start();
		pagination.setPageCount(getPages(count));
		pagination.currentPageIndexProperty().addListener((obs, oldIndex, newIndex) -> 
			{
				//System.out.println("pages:"+ pages+"count: "+count );
				updateTable(pages, newIndex.intValue(),Constants.rowsPerPage);
	        
			}
		);	
		
		supplierUnpaidBalance.setCellFactory(TextFieldTableCell.<Supplier, Double>forTableColumn(new DoubleStringConverter()));
		supplierBillingRate.setCellFactory(TextFieldTableCell.<Supplier, Double>forTableColumn(new DoubleStringConverter()));
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
		count=supplierService.getSupplierCount();
		pages=getPages(count);
		pagination.setPageCount(pages);
		System.out.println("refreshPagination completed");
	}
	
	public void updateTable(int pages, int index,int rowsPerPage) {
		List<Supplier> Suppliers=supplierService.getSuppliers(pages, index, rowsPerPage);	
		data.clear();
		if(data.isEmpty())
		{
	        for(Supplier sup:Suppliers)
	        {
	        	data.add(sup);
	        	long pid=sup.getSupplierID();
	        	int rowIndex=data.indexOf(sup);
	        	sup.getDeleteHyperlink().setOnAction(e->deleteButtonClickedThroughHyperlink(pid,rowIndex));
	        	sup.getSaveHyperlink().setOnAction(e->editButtonClickedThroughHyperlink(pid,rowIndex));
	        }
	     }
		supplierTable.setItems(data);	
	}
	
	
	private void editButtonClickedThroughHyperlink(long supID, int index) {
		Supplier sup=data.get(index);
		ApplicationContext applicationContext=loader.getApplicationcontext();
		AddSupplierController addSupplierController=(AddSupplierController)applicationContext.getBean("AddSupplierController");
		addSupplierController.setSupModel(sup);
		StackPane addSupplier = (StackPane) loader.load(URLS.ADD_SUPPLIER);
		BorderPane root = new BorderPane();
		root.setCenter(addSupplier);
		layoutController.loadWindow(root, "Update Supplier Details", Constants.POPUP_WINDOW_WIDTH,
				Constants.POPUP_WINDOW_HEIGHT);
	
	}

	private void deleteButtonClickedThroughHyperlink(long supID,int rowIndex) {
		supplierService.removeSupplier(supID);
		refreshPagination();
		if(rowIndex==0 && !(pagination.getCurrentPageIndex()==Constants.ZERO)) {
			pagination.setCurrentPageIndex(pagination.getCurrentPageIndex()-1);
			//updateTable(pages, pagination.getCurrentPageIndex()-1,Constants.rowsPerPage);
		}else {
			updateTable(pages, pagination.getCurrentPageIndex(),Constants.rowsPerPage);
		}
		
	}
	
	@FXML
	public void showAddSupplier() {
		StackPane addSupplier = (StackPane) loader.load(URLS.ADD_SUPPLIER);
		ApplicationContext applicationContext = loader.getApplicationcontext();
		AddSupplierController addSupplierController = (AddSupplierController) applicationContext
				.getBean("AddSupplierController");
		addSupplierController.cleanFields();
		//addCustomerController.setParentName("ManageSupplierController");
		BorderPane root = new BorderPane();
		root.setCenter(addSupplier);
		layoutController.loadWindow(root, "Add Supplier Details", Constants.POPUP_WINDOW_WIDTH,
				Constants.POPUP_WINDOW_HEIGHT);
	}
	
	@FXML 
	public void searchSupplier() {
		
	}
	public void getRefreshedTable() {
		supplierList.clear();
		data.clear();
		/*supplierTable.setItems(data);
		populate(retrieveData())*/;
		refreshPagination();
		updateTable(pages, pagination.getCurrentPageIndex(),Constants.rowsPerPage);
	}
	/*private void populate(final List<Supplier> suppliers) {
		if (data.isEmpty()) {
			for (Supplier sup : suppliers) {
				data.add(sup);
				int supID = sup.getSupplierID();
				int index = data.indexOf(sup);
				//cust.getDeleteHyperlink().setOnAction(e -> deleteButtonClickedThroughHyperlink(custID));
				//cust.getSaveHyperlink().setOnAction(e -> saveButtonClickedThroughHyperlink(custID, index));
				sup.getDeleteHyperlink().setOnAction(e -> deleteButtonClickedThroughHyperlink(supID,index));
				sup.getSaveHyperlink().setOnAction(e -> editButtonClickedThroughHyperlink(supID, index));
			}
		}
	}
	private List<Supplier> retrieveData() {	
		try {
			if (supplierList.isEmpty()) {
			//	supplierList = customerService.fetchCustomers();
				supplierList = supplierService.fetchSuppliers();
			}
			return supplierList;

		} catch (Exception e) {
			System.out.println("ManageSupplierController" + e.getMessage());
		}
		return new ArrayList<Supplier>();
	}
*/
}

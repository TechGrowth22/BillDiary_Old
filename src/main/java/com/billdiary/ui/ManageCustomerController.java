package com.billdiary.ui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import org.controlsfx.control.table.TableFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import com.billdiary.config.SpringFxmlLoader;
import com.billdiary.model.Customer;
import com.billdiary.model.Product;
import com.billdiary.service.CustomerService;
import com.billdiary.utility.Constants;
import com.billdiary.utility.URLS;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.util.converter.DoubleStringConverter;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

@Controller("ManageCustomerController")
public class ManageCustomerController implements Initializable {

	@Autowired
	private CustomerService customerService;
	@Autowired
	public LayoutController layoutController;
	@Autowired
	private AddCustomerController addCustomerController;
	@Autowired
	private SpringFxmlLoader loader;
	

	@FXML
	TextField mobileNo;
	@FXML
	TextField customerName;

	@FXML
	private TableView<Customer> customerTable;
	@FXML TableColumn<Customer,Double>Balance;
	TableFilter<Customer> filter;
	
	@FXML Pagination pagination;
	private static int pages;
	private static int index=0;
	private static long count=0;

	private ObservableList<Customer> data = FXCollections.observableArrayList();
	List<Customer> customerList = new ArrayList<>();

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		
	/*	try {
			customerTable.setItems(data);
			populate(retrieveData());
			filter = new TableFilter(customerTable);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}*/
		count=customerService.getCustomerCount();
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
		Balance.setCellFactory(TextFieldTableCell.<Customer,Double>forTableColumn(new DoubleStringConverter()));
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
		count=customerService.getCustomerCount();
		pages=getPages(count);
		pagination.setPageCount(pages);
		System.out.println("refreshPagination completed");
	}
	
	public void updateTable(int pages, int index,int rowsPerPage) {
		List<Customer> customers=customerService.getCustomers(pages, index, rowsPerPage);	
		data.clear();
		if(data.isEmpty())
		{
	        for(Customer cust:customers)
	        {
	        	data.add(cust);
	        	int pid=cust.getCustomerID();
	        	int rowIndex=data.indexOf(cust);
	        	cust.getDeleteHyperlink().setOnAction(e->deleteButtonClickedThroughHyperlink(pid,rowIndex));
	        	cust.getSaveHyperlink().setOnAction(e->editButtonClickedThroughHyperlink(pid,rowIndex));
	        }
	     }
		customerTable.setItems(data);	
	}

	/*private List<Customer> retrieveData() {
		try {
			if (customerList.isEmpty()) {
				customerList = customerService.fetchCustomers();
			}
			return customerList;
		} catch (Exception e) {
			System.out.println("ManageCustomerController" + e.getMessage());
		}
		return new ArrayList<Customer>();
	}

	private void populate(final List<Customer> customers) {
		if (data.isEmpty()) {
			for (Customer cust : customers) {
				data.add(cust);
				int custID = cust.getCustomerID();
				int index = data.indexOf(cust);
				cust.getDeleteHyperlink().setOnAction(e -> deleteButtonClickedThroughHyperlink(custID));
				cust.getSaveHyperlink().setOnAction(e -> editButtonClickedThroughHyperlink(custID, index));
			}
		}

	}*/

	public void editButtonClickedThroughHyperlink(int customerId, int index) {
		Customer cust = data.get(index);
		cust.setCustomerID(new SimpleIntegerProperty(customerId));
		addCustomerController.setCustModel(cust);
		addCustomerController.setParentName("CustomerController");
		StackPane addShop = (StackPane) loader.load(URLS.ADD_CUSTOMER);
		BorderPane root = new BorderPane();
		root.setCenter(addShop);
		layoutController.loadWindow(root, "Edit Customer Details", Constants.POPUP_WINDOW_WIDTH,
				Constants.POPUP_WINDOW_HEIGHT);
	}

	public void deleteButtonClickedThroughHyperlink(int customerId,int rowIndex) {
		System.out.println(customerId);
		customerService.deleteCustomer(customerId);
		refreshPagination();
		if(rowIndex==0 && !(pagination.getCurrentPageIndex()==Constants.ZERO)) {
			pagination.setCurrentPageIndex(pagination.getCurrentPageIndex()-1);
			//updateTable(pages, pagination.getCurrentPageIndex()-1,Constants.rowsPerPage);
		}else {
			updateTable(pages, pagination.getCurrentPageIndex(),Constants.rowsPerPage);
		}
		

	}

	public void saveButtonClickedThroughHyperlink(int customerId, int index) {
		System.out.println(customerId);
		System.out.println(index);
		Customer cust = data.get(index);
		Customer upcust = customerService.updateCustomer(cust);
		data.set(index, upcust);
		getRefreshedTable();

	}

	@FXML
	private <T> void setEditedValue(CellEditEvent<Customer, T> event) {
		if ("customerName".equals(event.getTableColumn().getId())) {
			String customerName = event.getNewValue().toString();
			event.getTableView().getItems().get(event.getTablePosition().getRow())
					.setCustomerName(new SimpleStringProperty(customerName));
		}
		if ("address".equals(event.getTableColumn().getId())) {
			String address = event.getNewValue().toString();
			event.getTableView().getItems().get(event.getTablePosition().getRow())
					.setAddress(new SimpleStringProperty(address));
		}
		if ("mobileNO".equals(event.getTableColumn().getId())) {
			String mobileNO = event.getNewValue().toString();
			event.getTableView().getItems().get(event.getTablePosition().getRow())
					.setMobile_no(new SimpleStringProperty(mobileNO));
		}
		if ("city".equals(event.getTableColumn().getId())) {
			String city = event.getNewValue().toString();
			event.getTableView().getItems().get(event.getTablePosition().getRow())
					.setCity(new SimpleStringProperty(city));
		}
		if ("country".equals(event.getTableColumn().getId())) {
			String country = event.getNewValue().toString();
			event.getTableView().getItems().get(event.getTablePosition().getRow())
					.setCountry(new SimpleStringProperty(country));
		}
		if ("emailID".equals(event.getTableColumn().getId())) {
			String emailID = event.getNewValue().toString();
			event.getTableView().getItems().get(event.getTablePosition().getRow())
					.setEmailID(new SimpleStringProperty(emailID));
		}
		/*
		 * if("joiningDate".equals(event.getTableColumn().getId())) { String
		 * joiningDate=event.getNewValue().toString();
		 * event.getTableView().getItems().get(event.getTablePosition().getRow()).
		 * setJoiningDate(new SimpleStringProperty(joiningDate)); }
		 */

	}

	@FXML
	public void searchCustomer() {

	}

	@FXML
	public void showAddCustomer() {
		try {
			StackPane addShop = (StackPane) loader.load(URLS.ADD_CUSTOMER);
			addCustomerController.setParentName("CustomerController");
			addCustomerController.clearFields();
			BorderPane root = new BorderPane();
			root.setCenter(addShop);
			layoutController.loadWindow(root, "Add Customer Details", Constants.POPUP_WINDOW_WIDTH,
					Constants.POPUP_WINDOW_HEIGHT);
		}catch(Exception e) {
			
		}

	}

	public void getRefreshedTable() {
		customerList.clear();
		data.clear();
		/*customerTable.setItems(data);
		populate(retrieveData());*/
		refreshPagination();
		updateTable(pages, pagination.getCurrentPageIndex(),Constants.rowsPerPage);
	}

}

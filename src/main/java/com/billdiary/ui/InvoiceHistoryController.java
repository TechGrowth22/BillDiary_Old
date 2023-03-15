package com.billdiary.ui;

import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.billdiary.model.Invoice;
import com.billdiary.model.Product;
import com.billdiary.service.InvoiceHistoryService;
import com.billdiary.utility.Constants;

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
import javafx.util.converter.DateStringConverter;
import javafx.util.converter.LocalDateStringConverter;
import javafx.util.converter.LongStringConverter;

@Controller("InvoiceHistoryController")
public class InvoiceHistoryController  implements Initializable{
	final static Logger LOGGER = LoggerFactory.getLogger(InvoiceHistoryController.class); 
	
	@FXML
	TextField invoiceName;
	
	@FXML 
	TextField invoiceIdForSearch;
	
	private ObservableList < Invoice > data = FXCollections.observableArrayList();
	
	@FXML private TableView < Invoice > invoiceTable;
	@FXML TableColumn<Invoice,Long> invoiceIdForTable;
	@FXML TableColumn<Invoice,Date> invoiceDate;
	
	@Autowired
	InvoiceHistoryService invoiceHistoryService;
	
	@FXML Pagination pagination;
	private static int pages;
	private static int index=0;
	private static long count=0;
	

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		LOGGER.info("Entering in InvoiceHistoryController");
		try {
		
			
			count = invoiceHistoryService.getInvoiceCount();
			Task<Void> showTable = new Task<Void>() {
			    @Override public Void call() {
	        		pages=getPages(count);
	        		System.out.println("pages:"+ pages+"   "+"count: "+count );
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
			invoiceIdForTable.setCellFactory(TextFieldTableCell.<Invoice,Long>forTableColumn(new LongStringConverter()));
			
		}catch(Exception e) {
			LOGGER.info(e.getMessage(), e);
		}
		LOGGER.info("Exiting from InvoiceHistoryController");
		/*
		try {
			
			productCode.setCellFactory(TextFieldTableCell.<Product,Long>forTableColumn(new LongStringConverter()));
			RetailPrice.setCellFactory(TextFieldTableCell.<Product,Double>forTableColumn(new DoubleStringConverter()));
			WholesalePrice.setCellFactory(TextFieldTableCell.<Product,Double>forTableColumn(new DoubleStringConverter()));
			Discount.setCellFactory(TextFieldTableCell.<Product,Double>forTableColumn(new DoubleStringConverter()));
			Stock.setCellFactory(TextFieldTableCell.<Product,Double>forTableColumn(new DoubleStringConverter()));		
			count=productService.getProductCount();
			Task<Void> showTable = new Task<Void>() {
			    @Override public Void call() {
	        		pages=getPages(count);
	        		System.out.println("pages:"+ pages+"   "+"count: "+count );
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
		}*/
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
		count=invoiceHistoryService.getInvoiceCount();
		pages=getPages(count);
		pagination.setPageCount(pages);
		System.out.println("refreshPagination completed");
	}
	
	public void updateTable(int pages, int index,int rowsPerPage) {
		List<Invoice> invoices=invoiceHistoryService.getInvoices(pages, index, rowsPerPage);	
		data.clear();
		if(data.isEmpty())
		{
	        for(Invoice invoice:invoices)
	        {
	        	data.add(invoice);
	        	long invoiceId=invoice.getInvoiceId();
	        	int rowIndex=data.indexOf(invoice);
	        	//prods.getDelete().setOnAction(e->deleteButtonClickedThroughHyperlink(pid,rowIndex));
	        	//prods.getSave().setOnAction(e->editButtonClickedThroughHyperlink(pid,rowIndex));
	        }
	     }
		invoiceTable.setItems(data);	
	}
	
	@FXML public void searchInvoice()
	{
		
	}
	
	@FXML public void refresh() {
		
	}

}

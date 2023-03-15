package com.billdiary.ui;

import java.net.URL;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.billdiary.service.InvoiceService;
import com.billdiary.service.ProductService;
import com.billdiary.utility.Calculate;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;

@Controller("ManageDashBoardController")
public class ManageDashBoardController implements Initializable{
	
	final static Logger LOGGER = LoggerFactory.getLogger(ManageDashBoardController.class);
	
	ObservableList<PieChart.Data> expenseChartData =
            FXCollections.observableArrayList(
            );
    @FXML PieChart expenseChart ;
 
    @Autowired
    InvoiceService invoiceService;
    
    @Autowired
    ProductService productService;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{	
		
		refresh();
	}
	
	public void getExpenseChartData() {
		expenseChartData.clear();
		expenseChart.getData().clear();
		expenseChartData.add(new PieChart.Data("Cost of Sales",Calculate.getFormatedDoubleValue(invoiceService.getTotalInvoicesAmount())));
		expenseChartData.add(new PieChart.Data("Current Inventory Cost", Calculate.getFormatedDoubleValue(productService.getTotalInventoryCost())));
		expenseChart.getData().addAll(expenseChartData);
		expenseChartData.forEach(data ->
          data.nameProperty().bind(
                  Bindings.concat(
                          data.getName(), " ", data.pieValueProperty(), " Rs"
                  )
          )
		);
	}
	
	@FXML public void refresh() {
		getExpenseChartData();
	}

}
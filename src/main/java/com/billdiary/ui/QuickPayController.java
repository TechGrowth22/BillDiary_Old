package com.billdiary.ui;

import java.io.File;
import java.io.IOException;

import java.io.PrintWriter;

import java.time.LocalDate;

import javax.xml.transform.TransformerException;

import org.apache.fop.apps.FOPException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;


import com.billdiary.config.SpringFxmlLoader;
import com.billdiary.model.QuickProductDetails;

import com.billdiary.utility.FOPPdfDemo;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;

import javafx.fxml.FXML;
import javafx.scene.Node;

import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;

import javafx.scene.control.TableColumn;

import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

@Controller("QuickPayController")
public class QuickPayController  {
	@Autowired
	public LayoutController layoutController;
	
	@Autowired
	FOPPdfDemo pdfdemo;
	@Autowired
	private SpringFxmlLoader loader;
	
	
	@FXML private Hyperlink Delete1;
	@FXML private TextField CompanyName;
	@FXML private ImageView Logo;
	@FXML private TextField Name;
	@FXML private TextArea address;
	@FXML private TextField phoneno;
	@FXML private DatePicker todaydate;
	@FXML private TextField NameofProduct;
	@FXML private TextField Quantity;
	@FXML private TextField amount;
	@FXML private TextField total;
	@FXML private TextField Discount;
	@FXML private TextField TotalAfterDiscount;
	@FXML private TableView<QuickProductDetails> billTable;
	@FXML private TableColumn<QuickProductDetails, String>nameofProduct;
	@FXML private TableColumn<QuickProductDetails, Integer>quantity;
	@FXML private TableColumn<QuickProductDetails, Integer>amtperqauntity;
	@FXML private TableColumn<QuickProductDetails, Integer>totalamt;
	@FXML private TableColumn<QuickProductDetails, Hyperlink>Delete;
	ObservableList<QuickProductDetails> data = FXCollections.observableArrayList();
	Hyperlink Delete2=new Hyperlink();
	float totalAmount=0;
	float DiscountedAmount=0;
	int discount=0;
	float totalAmountPerProduct=0;
	public void deleteButtonClicked()
	{
		System.out.println("Inside DeleteButtonClicked");
		ObservableList< QuickProductDetails> ListItems,SelectedListItem;
		ListItems=billTable.getItems();
		SelectedListItem=billTable.getSelectionModel().getSelectedItems();	
		SelectedListItem.forEach(ListItems::remove);
		totalAmount=0;
		for(int i=0;i<data.size();i++)
		{
			totalAmount+=(data.get(i).getQuantity()*data.get(i).getAmtperquantity());
		}
		String totalAmountPerProductString=""+totalAmount;
		total.setText(totalAmountPerProductString);
		CalculateDiscountAmount();
		System.out.println(data.size());
	}
	public QuickProductDetails objectCreationToAddProduct()
	{	
		System.out.println("Inside objectCreationToAddProduct");
		int totalAmountPerProduct=totalAmountPerProduct();
		QuickProductDetails p=new QuickProductDetails(NameofProduct.getText(), Integer.parseInt(Quantity.getText()), Integer.parseInt(amount.getText()),totalAmountPerProduct,Delete2);
		return p;
	
	}

	public void calculateAmountToSet()
	{
		System.out.println("Inside calculateAmountToSet");
		totalAmountPerProduct=totalAmountPerProduct();
		totalAmount+=totalAmountPerProduct;
		String totalAmountPerProductString=""+totalAmount;
		total.setText(totalAmountPerProductString);
		
	}
	public int totalAmountPerProduct()
	{
		System.out.println("Inside totalAmountPerProduct");
		int totalAmountToReturn=0;
		totalAmountToReturn+=(Integer.parseInt(Quantity.getText())*Integer.parseInt(amount.getText()));
		totalamt.setCellValueFactory(new PropertyValueFactory<QuickProductDetails,Integer>("totalamt"));
		System.out.println(" returning from totalamtperproduct");
		return totalAmountToReturn;
	}
	@FXML 
	public void CalculateDiscountAmount()
	{
		totalAmount=0;
		System.out.println("Inside CalculateDiscountAmount");
		discount=Integer.parseInt(Discount.getText());
		System.out.println(discount);
		DiscountedAmount=(discount*Float.parseFloat(total.getText()))/100;
		System.out.println(DiscountedAmount);
		totalAmount=Float.parseFloat(total.getText())-DiscountedAmount;
		System.out.println(totalAmount);
		String totalAmountString=""+totalAmount;
		TotalAfterDiscount.setText(totalAmountString);
		
		
	}
	@FXML 
	private void AddToTable()
	{	
		
		billTable.setEditable(true);
		todaydate.setValue(LocalDate.now());
		QuickProductDetails object=objectCreationToAddProduct();
		data.add(object);
		
		calculateAmountToSet();
		totalAmountPerProduct();
		
		nameofProduct.setCellValueFactory(new PropertyValueFactory<QuickProductDetails,String>("nameofproduct"));
		quantity.setCellValueFactory(new PropertyValueFactory<QuickProductDetails,Integer>("quantity"));
		amtperqauntity.setCellValueFactory(new PropertyValueFactory<QuickProductDetails,Integer>("amtperquantity"));
		Delete.setCellValueFactory(new PropertyValueFactory<QuickProductDetails,Hyperlink>("Delete"));
		billTable.setItems(data);
		
		object.getDelete().setStyle("-fx-background-color: #00ff00");
		object.getDelete().setOnAction(e->deleteButtonClicked());
		
		System.out.println(Discount.getText());
		if(Discount.getText().equals(""))
		{
			Discount.setText("0");
		}
		
		NameofProduct.clear();
		Quantity.clear();
		this.amount.clear();
		
		
	}
	
	
	@FXML private void generateBill() throws IOException, FOPException, TransformerException
		{
			generateXML();
			//FOPPdfDemo a=new FOPPdfDemo();
			pdfdemo.convertToPDF();
		 }

	public void generateXML()
	{
		try
		{
		//	URL inputUrl = getClass().getResource("C:\\bill\\Employees.xml");
		//	String input = ""+inputUrl;
			File input = new File("C:\\bill\\Employees.xml");
			PrintWriter writer = new PrintWriter(input, "UTF-8");
			writer.println("<?xml version='1.0'?><employees><companyname>"+CompanyName.getText()+"</companyname>");
			writer.println("<Logo>"+Logo.getImage()+"</Logo>");
			writer.println("<NameOfClient>"+Name.getText()+"</NameOfClient>");
			writer.println("<date>"+todaydate.getValue()+"</date>");
			writer.println("<Address>"+address.getText()+"</Address>");
			writer.println("<phone>"+phoneno.getText()+"</phone>");
			int id=101;
			for(int i=0;i<data.size();i++)
			{
				writer.println("<employee><id>"+id+"</id>");
				writer.println("<name>"+data.get(i).getNameofproduct()+"</name>");
				writer.println("<Quantity>"+data.get(i).getQuantity()+"</Quantity>");
				writer.println("<amtperquantity>"+data.get(i).getAmtperquantity()+"</amtperquantity>");
				writer.println("<total>"+data.get(i).getTotalamt()+"</total>");
				writer.println("</employee>");
				id++;
			}
			writer.println("<total>"+total.getText()+"</total>");
			writer.println("<discount>"+Discount.getText()+"</discount>");
			writer.println("<Totalafterdiscount>"+TotalAfterDiscount.getText()+"</Totalafterdiscount>");
			writer.println("</employees>");			
			writer.close();
		    
		    
		} catch (IOException e) 
		{
		   // do something
		}
		
		
	}

	@FXML 
	public void AddPage()
	{
		System.out.println("inside add page");
		AnchorPane addProduct=(AnchorPane) loader.load("/fxml/AddProducts.fxml");
		BorderPane root = new BorderPane();
		root.setCenter(addProduct);
		layoutController.loadWindow(root,"Add Products",565,134);
		
	}
	@FXML public void HidePage(Event event)
	{
		((Node)(event.getSource())).getScene().getWindow().hide();
		AddToTable();
		
	}
	
	@FXML 
	public void initialize()
	{
		//File file=new File("C:\\Users\\HP\\Desktop\\pay-bills logo.jpg");
		//Image image=new Image(file.toURI().toString());
		//Logo.setImage(image);
		Discount.textProperty().addListener(new ChangeListener<String>() {
			
		    @Override
		    public void changed(ObservableValue<? extends String> observable,
		            String oldValue, String newValue) {
		    		
		    		CalculateDiscountAmount();
		    }
		});
	}	
	
}

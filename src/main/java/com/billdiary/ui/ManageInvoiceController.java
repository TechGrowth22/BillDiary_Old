package com.billdiary.ui;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import org.controlsfx.control.textfield.TextFields;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.billdiary.entities.ShopEntity;
import com.billdiary.javafxUtility.Popup;
import com.billdiary.javafxUtility.TabTraversalEventHandler;
import com.billdiary.model.Customer;
import com.billdiary.model.Invoice;
import com.billdiary.model.InvoiceTemplateA4;
import com.billdiary.model.Product;
import com.billdiary.service.CustomerService;
import com.billdiary.service.GSTService;
import com.billdiary.service.InvoiceService;
import com.billdiary.service.PriceService;
import com.billdiary.service.ProductService;
import com.billdiary.service.ShopService;
import com.billdiary.service.SoldProductService;
import com.billdiary.utility.BasicCalculator;
import com.billdiary.utility.Calculate;
import com.billdiary.utility.Constants;
import com.billdiary.utility.GeneratePDF;
import com.billdiary.utility.PdfGenaratorUtil;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;

@Controller("ManageInvoiceController")
@Scope("prototype")
public class ManageInvoiceController implements Initializable {

	final static Logger LOGGER = LoggerFactory.getLogger(ManageInvoiceController.class); 
	
	@Autowired
	ShopService shopService; 
	
	@Autowired
	public InvoiceService invoiceService;
	
	@Autowired
	public SoldProductService soldProductService;
	
	@Autowired
	public LayoutController layoutController;
	
	@Autowired
	GSTService gstService; 
	
	@Autowired
	BasicCalculator calculator;
	
	@Autowired
	public Calculate calculate;
	
	@FXML
	SplitMenuButton saveButton;

	@FXML TextField paidAmount;
	@FXML TextField totalAmount;
	@FXML TextField bigFinalAmount;
	@FXML TextField amountDue;
	@FXML TextField discount;
	@FXML TextField finalAmount;
	@FXML TextArea invAddress;
	@FXML TextField invMobileNo;
	@FXML Text invNO;
	@FXML Text invDate;
	@FXML DatePicker invIssueDate;
	@FXML DatePicker invDueDate;
	@FXML TextField invCustName;
	@FXML CheckBox checkdefaultCustomer;
	@FXML TextField invProductName;
	@FXML TextField invProductQuantity;
	@FXML TextField invProductPrice;
	@FXML TextField taxableAmt;
	@FXML TextField totalCGST;
	@FXML TextField totalSGST;
	@FXML TextField totalAmt;

	/** All table related **/
	@FXML
	TableView<Product> productTable;
	private ObservableList<Product> data = FXCollections.observableArrayList();
	@FXML TableColumn<Product, Double> productQuantity;
	@FXML TableColumn<Product, Double> valuePrice;
	@FXML TableColumn<Product, Double> productDiscount;
	@FXML TableColumn<Product, Integer> productID;
	@FXML TableColumn<Product, Integer> serialNumber;
	@FXML TableColumn<Product, Double> totalPrice;
	@FXML TableColumn<Product, Double> gstRate;
	@FXML TableColumn<Product, Double> mrpPrice;;
	

	Customer selectedCustomer = null;
	Product selectedProduct = null;
	ObservableList<String> options;

	@Autowired
	private CustomerService customerService;
	List<String> customerNameList = new ArrayList<>();
	List<Customer> custList = new ArrayList<>();

	@Autowired
	private ProductService productService;
	List<String> productNameList = new ArrayList<>();
	List<Product> prodList = new ArrayList<>();

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		refreshInvoiceTable();
		
		Task<Void> fetchCustomerList = new Task<Void>() {
		    @Override public Void call() {
		    	refreshCustomerList();
		    	return null;
		    }
		};
		Task<Void> fetchProductList = new Task<Void>() {
		    @Override public Void call() {
		    	refreshProductList();
		    	return null;
		    }
		};
		new Thread(fetchCustomerList).start();
		new Thread(fetchProductList).start();
		
		LocalDate localDate = LocalDate.now();
		//invDate.setText(localDate.toString() + " (YYYY-DD-MM)");
		invIssueDate.setValue(localDate);
		invDueDate.setValue(localDate);
		//int invoiceNO = calculateInvoiceNO();
		invNO.setText(invoiceService.generateInvoiceNO());
		
		

		productQuantity
				.setCellFactory(TextFieldTableCell.<Product, Double>forTableColumn(new  DoubleStringConverter()));
		valuePrice.setCellFactory(TextFieldTableCell.<Product, Double>forTableColumn(new DoubleStringConverter()));
		productDiscount.setCellFactory(TextFieldTableCell.<Product, Double>forTableColumn(new DoubleStringConverter()));
		productID.setCellFactory(TextFieldTableCell.<Product, Integer>forTableColumn(new IntegerStringConverter()));
		serialNumber.setCellFactory(TextFieldTableCell.<Product, Integer>forTableColumn(new IntegerStringConverter()));
		totalPrice.setCellFactory(TextFieldTableCell.<Product, Double>forTableColumn(new DoubleStringConverter()));
		gstRate.setCellFactory(TextFieldTableCell.<Product, Double>forTableColumn(new DoubleStringConverter()));
		mrpPrice.setCellFactory(TextFieldTableCell.<Product, Double>forTableColumn(new DoubleStringConverter()));
		
		invCustName.focusedProperty().addListener((ov, oldV, newV) -> {
			if (!newV) {
				selectCustomerAddMob();
			}
		});

		invProductName.focusedProperty().addListener((ov, oldV, newV) -> {
			if (!newV) {
				invProductQuantity.setText(Integer.toString(1));
				String product = invProductName.getText();
				if (null != product) {
					Product prd = prodList.stream().filter(x -> (x.getProductCode() + ": " + x.getName()).equals(product))
							.findAny().orElse(null);
					if (null != prd) {
						
						double unitPrice= Calculate.getFormatedDoubleValue(prd.getSellPrice());
						//double unitPrice= priceService.getProductRatePrice(prd.getRetailPrice(),prd.getRetailGSTpercentage(),1);
						invProductPrice.setText(Double.toString(Calculate.getFormatedDoubleValue(unitPrice)));
						selectedProduct = prd;
					}

				}
			}
		});

		invAddress.addEventFilter(KeyEvent.KEY_PRESSED, new TabTraversalEventHandler());
		
		discount.textProperty().addListener((ov, oldV, newV) -> {
			calculatefinalAmount();	
		});
		
		totalAmount.textProperty().addListener((ov, oldV, newV) -> {
			calculatefinalAmount();
			
		});
		paidAmount.textProperty().addListener((ov,oldV,newV)->{
			calculateAmountDue();
		});
		taxableAmt.textProperty().addListener((ov,oldV,newV)->{
			calculateTaxableAmount();
		});
	
		finalAmount.textProperty().bindBidirectional(bigFinalAmount.textProperty());
		finalAmount.textProperty().addListener((ov,oldV,newV)->{
			paidAmount.setText("0");
			amountDue.setText(finalAmount.getText());
		});
	}

	private void calculateTaxableAmount() {
		
		
	}

	private void refreshInvoiceTable() {
		data.clear();	
	}

	private int calculateInvoiceNO() {
		int invoiceNO = 1;
		return invoiceNO;
	}

	public void refreshCustomerList() {
		if (null != custList) {
			custList.clear();
		}
		if (null != customerNameList) {
			customerNameList.clear();
		}
		/**
		 * Fetching Fresh Customer list
		 */
		custList = customerService.fetchCustomers();
		/**
		 * Creating a customer list with Name + Mobile no
		 */
		for (Customer cust : custList) {
			customerNameList.add(cust.getCustomerID()+" "+cust.getCustomerName() + " " + cust.getMobile_no());
		}
		TextFields.bindAutoCompletion(invCustName, customerNameList).setVisibleRowCount(5);
	}

	private void refreshProductList() {
		if (null != prodList) {
			prodList.clear();
		}
		if (null != productNameList) {
			productNameList.clear();
		}

		prodList = productService.fetchProducts();

		for (Product prod : prodList) {
			productNameList.add(prod.getProductCode() + ": " + prod.getName());
		}
		TextFields.bindAutoCompletion(invProductName, productNameList).setVisibleRowCount(5);
	}
	
	public void calculatefinalAmount() {
		double total;
		double disc;
		total=Calculate.getNonEmptyDoubleValue(totalAmount.getText());
		disc=Calculate.getNonEmptyDoubleValue(discount.getText());
		total=total-(total*(disc/100));
		finalAmount.setText(String.valueOf(Calculate.getFormatedDoubleValue(total)));
	}
	
	public void calculateAmountDue()
	{
		double paidAmt;
		double total;
		total=Calculate.getNonEmptyDoubleValue(finalAmount.getText());
		paidAmt=Calculate.getNonEmptyDoubleValue(paidAmount.getText());
		amountDue.setText(String.valueOf(Calculate.getFormatedDoubleValue(total-paidAmt)));
	}
	

	@FXML
	public void handleKeyAction(KeyEvent event) {
		
		final KeyCombination keyComb = new KeyCodeCombination(KeyCode.P, KeyCombination.CONTROL_DOWN);

		if ((KeyCode) event.getCode() == KeyCode.ENTER) {
			try {
				addProductToTable();
			} catch (Exception e) {
				LOGGER.error(e.getMessage(),e);
			}
		}
		if (keyComb.match(event)) {
			try {
				generateInvoiceSaveAndPrint(new ActionEvent());
			} catch (Exception e) {
				LOGGER.error(e.getMessage(),e);
			}
		}
	}

	@FXML
	public void handleKeyActionEnter(KeyEvent event) {
		if ((KeyCode) event.getCode() == KeyCode.ENTER) {
			try {
				selectCustomerAddMob();
			} catch (Exception e) {
				LOGGER.error(e.getMessage(),e);
			}
		}
	}
	
	public void selectCustomerAddMob() {
		String customer = invCustName.getText();
		if (null != customer) {
			Customer cust = custList.stream()
					.filter(x -> (x.getCustomerID()+" "+x.getCustomerName() + " " + x.getMobile_no()).equals(customer)).findAny()
					.orElse(null);
			if (null != cust)
				selectedCustomer = cust;

			if (null != selectedCustomer) {
				invAddress.setText(selectedCustomer.getAddress());
				invMobileNo.setText(selectedCustomer.getMobile_no());
			}
		}
	}
	private void addProduct() {
		if(null==selectedProduct) {
			Popup.showErrorAlert(Constants.ERROR_TITLE,Constants.INVALID_PRODUCT,AlertType.ERROR);
		}else if(invProductQuantity.getText()=="" || invProductQuantity.getText().isEmpty() || invProductQuantity.getText().equals("0")) {
			Popup.showErrorAlert(Constants.ERROR_TITLE,Constants.INVALID_QUANTITY,AlertType.ERROR);
		}else {
			Product pr=new Product();
			pr=(Product)selectedProduct.clone();
				
			pr.setSerialNumber(new SimpleIntegerProperty(productTable.getItems().size() + 1));
			pr.setQuantity(new SimpleDoubleProperty(Double.parseDouble(invProductQuantity.getText())));
			
			/**
			 * new changes 03-07-2018
			 */
			double mrpPrice=Calculate.getFormatedDoubleValue(pr.getSellPrice());
			double valuePrice=Calculate.getFormatedDoubleValue(mrpPrice*pr.getQuantity());
			double discountSubtValue=calculator.getDiscountSubtractedValue(valuePrice,pr.getDiscount());
			double gstAddedWithDiscountSubtracted=Calculate.getFormatedDoubleValue(gstService.getGSTIncludedPrice(discountSubtValue, pr.getSellPriceGSTPercentage()));
			
			pr.setMrpPrice(new SimpleDoubleProperty(mrpPrice));
			pr.setValuePrice(new SimpleDoubleProperty(valuePrice));
			pr.setTotalPrice(new SimpleDoubleProperty(gstAddedWithDiscountSubtracted));
			
			
			/*double rate=calculator.getDiscountaddedValue(pr.getRetailPrice(),pr.getDiscount());	
			
			double valuePrice=rate*pr.getQuantity();
			pr.setValuePrice(new SimpleDoubleProperty(Calculate.getFormatedDoubleValue(valuePrice)));
		
			double totalPrice=priceService.getProductTotalPrice(rate,pr.getRetailGSTpercentage(),pr.getQuantity(),pr.getDiscount());
			pr.setTotalPrice(new SimpleDoubleProperty(totalPrice));
			double mrpPrice=Calculate.getFormatedDoubleValue(rate);*/
			
			data.add(pr);
			int index=data.size()-1;
			pr.getDelete().setOnAction(e->deleteButtonClickedThroughHyperlink(index));
			
			productTable.setItems(data);
			calculateTotalAmount();
			invProductName.clear();
			invProductPrice.clear();
			invProductQuantity.clear();
			selectedProduct=null;
			calculateGST();
		}
	}
	
	
	
	private void calculateGST() {
		/**
		 * taxableAmt
		 * totalCGST
		 * totalSGST
		 * totalAmt
		 */
		double taxableAmount=0;
		double totalCGSTAmount=0;
		double totalSGSTAmount=0;
		double totalGSTAddedAmount=0;
		double subtractedAmont=0;
		for (Product row : productTable.getItems()) {
			taxableAmount=taxableAmount+calculator.getDiscountSubtractedValue(row.getValuePrice(), row.getDiscount());
			//taxableAmount=row.getValuePrice()+taxableAmount;  it was creating calculation problem
			totalGSTAddedAmount=totalGSTAddedAmount+row.getTotalPrice();
			    if(row.getSellPriceGSTPercentage().compareTo(0.00)!=0) {
					subtractedAmont=totalGSTAddedAmount-taxableAmount;
					totalCGSTAmount=totalSGSTAmount=(subtractedAmont/2);
			    }
		    }
		taxableAmt.setText(Calculate.getFormatedDoubleToStringValue(taxableAmount));
		totalCGST.setText(Calculate.getFormatedDoubleToStringValue(totalCGSTAmount));
		totalSGST.setText(Calculate.getFormatedDoubleToStringValue(totalSGSTAmount));
		totalAmt.setText(Calculate.getFormatedDoubleToStringValue(totalGSTAddedAmount));
		
	}

	private void calculateTotalAmount() {
		double total=0;
		for (Product row : productTable.getItems()) {
			total=total+row.getTotalPrice();
		    }
		totalAmount.setText(String.valueOf(total));	
	}

	private void deleteButtonClickedThroughHyperlink(int index) {
		if(index==0)
			data.remove(index);
		else
			data.remove(index-1);
		
		calculateTotalAmount();
		calculateGST();
	}
	
	
	@FXML
	public void addProductToTable() {
		String product = invProductName.getText();
		String productQuantity = invProductQuantity.getText();
		if (null != product) {
			Product prd = prodList.stream().filter(x -> (x.getProductCode() + ": " + x.getName()).equals(product))
					.findAny().orElse(null);
			if (null != prd) {
				Double remainingStock = prd.getStock() - Double.valueOf(productQuantity);
				if(remainingStock < 0) {
					Popup.showAlert(Constants.PRODUCT_QUANTITY_ERROR,"Product's quantity is not sufficient to place an Order, Please check stocks of the product" , AlertType.ERROR);
				}else {
					addProduct();
					invProductName.requestFocus();
				}
			}
		}
		
	}
	
	
	
	
	public boolean saveInvoice() {
		boolean invoiceSaved=false;
		Customer cust=invoiceService.getCustomer(invoiceService.trimCustomerID(invCustName.getText()));
		if(null==invDueDate.getValue()) {
			
			invDueDate.setValue(LocalDate.now());
		}
		Invoice inv=new Invoice();
		inv.setInvoiceId(new SimpleLongProperty(Long.parseLong(invNO.getText())));
		inv.setCustomer(cust);
		inv.setAmountDue(Calculate.getNonEmptyDoubleValue(amountDue.getText()));
		inv.setFinalAmount(Calculate.getNonEmptyDoubleValue(finalAmount.getText()));
		inv.setPaidAmount(Calculate.getNonEmptyDoubleValue(paidAmount.getText()));
		inv.setProduct_sale_qty(data.size());
		inv.setLastAmountPaidDate(LocalDate.now());
		inv.setInvoiceDate(invIssueDate.getValue());
		inv.setInvoiceDueDate(LocalDate.now());
		invoiceSaved=invoiceService.save(inv);
		if(invoiceSaved) {
			
			updateProductStock();
			updateSoldProducts(inv);
			updateCustomerBalance(cust,Double.parseDouble(amountDue.getText()));
			LOGGER.info("Product Stock updated");	
		}
		return invoiceSaved;
	}
	
	
	private void updateSoldProducts(Invoice inv) {
		soldProductService.updateSoldProducts(inv,data);
	}

	private void updateCustomerBalance(Customer cust,Double amount) {
		customerService.updateCustomerBalance(cust.getCustomerID(),amount);
	}

	private void updateProductStock() {
		for(Product product:data) {
			//double stock=product.getStock()-product.getQuantity();
			if(product.getQuantity()>=0) {
				//product.setStock(new SimpleDoubleProperty(stock));
				int result=productService.updateProductStock(product.getProductId(),product.getQuantity());
				
			}
		}
	}	
	@FXML
	public void generateInvoiceSave(ActionEvent event) {
		LOGGER.debug("generateInvoiceSave");
		if(saveInvoice()) {
			System.out.println("Invoice saved");
			createPDF();
			clearAllFields();
			LOGGER.info("Invoice saved & PDF Generated");			
		}else {
			LOGGER.error("invoice not saved");
			Popup.showAlert(Constants.INVOICE_TITLE,Constants.INVOICE_UNSUCCESSFULL_STATUS,AlertType.INFORMATION);
		}
		
	}
	
	@FXML public void  selectDefaultCustomer() {
		if(checkdefaultCustomer.isSelected()) {
			Customer cust=customerService.getDefaultCustomer();
			if(cust!=null) {
				selectedCustomer=cust;
			invCustName.setText(selectedCustomer.getCustomerID()+" "+selectedCustomer.getCustomerName()+" "+selectedCustomer.getMobile_no());
			invAddress.setText(selectedCustomer.getAddress());
			invMobileNo.setText(selectedCustomer.getMobile_no());
			invCustName.setEditable(false);
			invAddress.setEditable(false);
			invMobileNo.setEditable(false);
			}
		}else {
			invCustName.clear();
			invAddress.clear();
			invMobileNo.clear();
			invCustName.setEditable(true);
			invAddress.setEditable(true);
			invMobileNo.setEditable(true);
			selectedCustomer=null;
		}
	}
	
	
	@FXML
	public void generateInvoiceSaveAndPrint(ActionEvent event) {
		System.out.println("generateInvoiceSaveAndPrint");
		if(null==selectedCustomer) {
			if(null==invCustName.getText() || invCustName.getText().isEmpty() || null==invMobileNo || invMobileNo.getText().isEmpty()) {
				Popup.showAlert(Constants.INVOICE_ERROR,Constants.INVOICE_CUSTOMER_EMPTY,AlertType.ERROR);
			}else {
				Customer customer=new Customer();
				customer.setCustomerName(new SimpleStringProperty(invCustName.getText()));
				customer.setAddress(new SimpleStringProperty(invAddress.getText()==null?"":invAddress.getText()));
				customer.setMobile_no(new SimpleStringProperty(invMobileNo.getText()));
				customer.setStatus(new SimpleStringProperty(Constants.ACTIVE));
				DateFormat dateFormat = new SimpleDateFormat(Constants.DATE_FORMAT);
				String strDate = dateFormat.format(new Date());
				customer.setRegistrationDate(new SimpleStringProperty(strDate));
				customer=customerService.addNewCustomerByInvoive(customer);
				selectedCustomer=customer;
				invCustName.setText(selectedCustomer.getCustomerID()+" "+selectedCustomer.getCustomerName()+" "+selectedCustomer.getMobile_no());
			}
			
		}
		if(null!=selectedCustomer) {
			if(saveInvoice()) {
				System.out.println("Invoice saved");
				createPDF();
				clearAllFields();
				LOGGER.info("Invoice saved & PDF Generated");
							
			}else {
				System.out.println("invoice not saved");
				Popup.showAlert(Constants.INVOICE_TITLE,Constants.INVOICE_UNSUCCESSFULL_STATUS,AlertType.INFORMATION);
			}
		}
		
	}
	
	private void createPDF() {
		try {

				GenerateInvoicePDF();
			}
			catch(Exception e){
				LOGGER.info(e.getMessage()+ e.getCause()+ e.getClass());
			}
	}

	private InvoiceTemplateA4 generateInvoiceTemplateA4() {
		InvoiceTemplateA4 invoiceTemplate=new InvoiceTemplateA4();
		invoiceTemplate.setInvoiceNO(invNO.getText());
		
		invoiceTemplate.setCompanyName("BillDiary.com");
		/*ImageView imageView=new ImageView();
		Image image=new Image(getClass().getResource(URLS.INVOICE_LOGO).getFile());
		imageView.setImage(image);
		invoiceTemplate.setLogo(imageView);*/
		invoiceTemplate.setCustomerName(invCustName.getText());
		invoiceTemplate.setCustomerAddress(null==invAddress.getText()?"":invAddress.getText());
		invoiceTemplate.setAmountDue(amountDue.getText());
		invoiceTemplate.setFinalAmount(finalAmount.getText());
		List<Product> products=new ArrayList<>();
		data.forEach(product->{
			products.add(product);
		});
		invoiceTemplate.setProducts(products);
		invoiceTemplate.setDiscount(discount.getText());
		invoiceTemplate.setMobileNo(invMobileNo.getText());
		LocalDate today=LocalDate.now();
		invoiceTemplate.setToday(today);
		invoiceTemplate.setPaidAmount(paidAmount.getText());
		invoiceTemplate.setTotalAmount(totalAmount.getText());
		return invoiceTemplate;	
	}
	private void clearAllFields() {
		invNO.setText(invoiceService.generateInvoiceNO());
		paidAmount.clear();
		totalAmount.clear();
		bigFinalAmount.clear();
		amountDue.clear();
		discount.clear();
		finalAmount.clear();
		selectDefaultCustomer();
		//invAddress.clear();
		//invMobileNo.clear();
		//invCustName.clear();
		invProductName.clear();
		invProductQuantity.clear();
		invProductPrice.clear();
		taxableAmt.clear();
		totalCGST.clear();
		totalSGST.clear();
		totalAmt.clear();
		data.clear();
	}
	
	public boolean GenerateInvoicePDF() throws Exception {
		Map<String,String> valueMap = new HashMap<>();
		valueMap.put("invNO", invNO.getText());	
		valueMap.put("invIssueDate", null == invIssueDate.getValue()?"":invIssueDate.getValue().toString());	
		valueMap.put("invCustName", invCustName.getText());	
		valueMap.put("invDueDate", null == invDueDate.getValue()?"":invDueDate.getValue().toString());	
		valueMap.put("totalAmount", totalAmount.getText());	
		valueMap.put("taxableAmt", taxableAmt.getText());	
		valueMap.put("totalSGST", totalSGST.getText());	
		valueMap.put("totalCGST", totalCGST.getText());	
		valueMap.put("finalAmount", finalAmount.getText());	
		valueMap.put("paidAmount", paidAmount.getText());	
		valueMap.put("amountDue",amountDue.getText());	
		
		
		
		return invoiceService.GenerateInvoicePDF(data, valueMap);
	}

}

package com.billdiary.ui;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.ResourceBundle;

import org.controlsfx.validation.Severity;
import org.controlsfx.validation.ValidationMessage;
import org.controlsfx.validation.ValidationSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.billdiary.exception.ApplicationException;
import com.billdiary.javafxUtility.ControlFXValidation;
import com.billdiary.javafxUtility.Popup;
import com.billdiary.javafxUtility.TabTraversalEventHandler;
import com.billdiary.model.Customer;
import com.billdiary.service.CustomerService;
import com.billdiary.service.ProductService;
import com.billdiary.utility.Constants;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyEvent;


@Controller("AddCustomerController")
public class AddCustomerController implements Initializable{
	
	final static Logger LOGGER = LoggerFactory.getLogger(AddCustomerController.class); 

	ValidationSupport support=new ValidationSupport();
	
	@Autowired 
	ControlFXValidation controlFXValidation;
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private ManageCustomerController manageCustomerController;
	
	@Autowired
	private ManageInvoiceController manageInvoiceController;
	
	public Customer custModel;

	@FXML TextField addCustomerName;
	@FXML TextArea addAddress;
	@FXML TextField addMobileNo;
	@FXML TextField addCity;
	@FXML TextField addEmailID;
	@FXML ChoiceBox<String> addCountry;
	@FXML ChoiceBox<String> addState;
	@FXML TextArea addAdditionalInfo;
	@FXML ChoiceBox<String> addCustomerGroup;
	@FXML TextField addZipCode;
	@FXML DatePicker Anniversary_Date;
	@FXML DatePicker Birth_Date;
	@FXML TextField balance;
	@FXML  ChoiceBox<String> status;
	
	public String parentName;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		support.registerValidator(addCustomerName, true, controlFXValidation.getStringValidator());
		addAddress.addEventFilter(KeyEvent.KEY_PRESSED, new TabTraversalEventHandler());
		addAdditionalInfo.addEventFilter(KeyEvent.KEY_PRESSED, new TabTraversalEventHandler());
		status.getSelectionModel().selectFirst();
		if(custModel!=null)
		{	
			addCustomerName.setText(custModel.getCustomerName());
			addAdditionalInfo.setText(custModel.getAddAdditionalInfo());
			addAddress.setText(custModel.getAddress());
			addCity.setText(custModel.getCity());
			addCountry.setValue(custModel.getCountry());
			addCustomerGroup.setValue(custModel.getCustomerGroup());
			addEmailID.setText(custModel.getEmailID());
			addMobileNo.setText(custModel.getMobile_no());
			addState.setValue(custModel.getState());;
			addZipCode.setText(custModel.getZipCode());
			Anniversary_Date.setValue(custModel.getAnniversary_date());
			Birth_Date.setValue(custModel.getBirth_date());
		    balance.setText(String.valueOf(custModel.getBalance()));
		    status.setValue(custModel.getStatus());
		}
	}

	
	@FXML
	public void addCustomer(ActionEvent event){
		Customer cust=new Customer();
		cust.setCustomerName(new SimpleStringProperty(addCustomerName.getText()));
		cust.setAddress(new SimpleStringProperty(addAddress.getText()));
		cust.setMobile_no(new SimpleStringProperty(addMobileNo.getText()));
	    cust.setCity(new SimpleStringProperty(addCity.getText()));
	    cust.setEmailID(new SimpleStringProperty(addEmailID.getText()));
	    cust.setCountry(new SimpleStringProperty(addCountry.getValue()));
	    cust.setState(new SimpleStringProperty(addState.getValue()));
	    cust.setZipCode(new SimpleStringProperty(addZipCode.getText())); 
		cust.setAnniversary_date(Anniversary_Date.getValue());
	    cust.setBirth_date(Birth_Date.getValue());
	    cust.setCustomerGroup(new SimpleStringProperty(addCustomerGroup.getValue()));
	    cust.setAddAdditionalInfo(new SimpleStringProperty(addAdditionalInfo.getText()));
	    cust.setStatus(new SimpleStringProperty(status.getValue()));
	    DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
	    String strDate = dateFormat.format(new Date());
	    cust.setRegistrationDate(new SimpleStringProperty(strDate));
	    if(!(balance.getText().isEmpty()) && validateDoubleField(balance.getText())) {
	    	cust.setBalance(new SimpleDoubleProperty(Double.parseDouble(balance.getText())));
	    }else {
	    	cust.setBalance(new SimpleDoubleProperty(0.00));
	    }
	    /**
	     * All customer Customer validation 
	     */
	    
	    
	    
	    try {
			if(validateCustomer(cust))
			{
			    if(custModel==null) {
			    	
			    	customerService.addCustomer(cust);
			    }else{ 
					cust.setCustomerID(new SimpleIntegerProperty(custModel.getCustomerID()));
				    customerService.updateCustomer(cust);
				    }
				 setCustModel(null);
				((Node)(event.getSource())).getScene().getWindow().hide();
				if(null!=this.parentName) {
				if(this.parentName.equals("CustomerController")) {
					manageCustomerController.getRefreshedTable();
				}else if(this.parentName.equals("InvoiceController")) {
					manageInvoiceController.refreshCustomerList();;
				}
			  }
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(),e);
		}
	}
	
	private boolean validateDoubleField(String text) {
		return true;
	}
	private boolean validateCustomer(Customer cust) throws ApplicationException {
		boolean valid=true;
		
		if(null==cust.getMobile_no() ||cust.getMobile_no().isEmpty()) {
			Popup.showErrorAlert(Constants.ERROR_TITLE,Constants.ERROR_COMMON_VALIDATION,AlertType.ERROR);
			valid=false;
			//throw new ApplicationException("Invalid mobile number");
		}
		if(null==cust.getCustomerName() || cust.getCustomerName().isEmpty()) {
			valid=false;
			Popup.showErrorAlert(Constants.ERROR_TITLE,Constants.ERROR_COMMON_VALIDATION,AlertType.ERROR);
			//throw new ApplicationException("Invalid customer number");
		}	
	/*Collection<ValidationMessage> mes=support.getValidationResult().getErrors();
	    
	    for(ValidationMessage m:mes) {
		    	if(m.getSeverity()==Severity.ERROR)
		    	{
		    		valid=false;
		    		Popup.showErrorAlert(Constants.ERROR_TITLE,Constants.ERROR_CUSTOMER_FEILD_VALIDATION,AlertType.ERROR);
		    	}
	    	}*/
		return valid;	
	}
	public void clearFields() {
		addAdditionalInfo.setText(null);
		addAddress.setText(null);
		addCity.setText(null);
		addCountry.setValue(null);
		addCustomerGroup.setValue(null);
		addCustomerName.setText(null);
		addEmailID.setText(null);
		addMobileNo.setText(null);
		addState.setValue(null);
		addZipCode.setText(null);
		Anniversary_Date.setValue(null);
		Birth_Date.setValue(null);
	}

	public String getParentName() {
		return parentName;
	}
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
	public Customer getCustModel() {
		return custModel;
	}
	public void setCustModel(Customer custModel) {
		this.custModel = custModel;
	}


}

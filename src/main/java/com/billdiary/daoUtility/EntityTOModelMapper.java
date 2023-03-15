package com.billdiary.daoUtility;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.billdiary.entities.CustomerEntity;
import com.billdiary.entities.InvoiceEntity;
import com.billdiary.entities.ProductCategoryEntity;
import com.billdiary.entities.ProductEntity;
import com.billdiary.entities.SupplierEntity;
import com.billdiary.entities.UnitEntity;
import com.billdiary.model.Address;
import com.billdiary.model.Customer;
import com.billdiary.model.Invoice;
import com.billdiary.model.Product;
import com.billdiary.model.Supplier;
import com.billdiary.model.Unit;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

@Component
public class EntityTOModelMapper {

	final static Logger LOGGER = LoggerFactory.getLogger(EntityTOModelMapper.class); 
	public List<Supplier> getSupplierModels(List<SupplierEntity> supplierEntityList) {
		List<Supplier> supplierList=new ArrayList<>();
		for(SupplierEntity supEntity:supplierEntityList) {
			Supplier sup=new Supplier();
			sup.setSupplierID(new SimpleLongProperty(supEntity.getSupplierID()));
			sup.setSupplierName(new SimpleStringProperty(supEntity.getSupplierName()));
			sup.setSupplierCompany(new SimpleStringProperty(supEntity.getSupplierCompany()));
			sup.setSupplierAccountNo(new SimpleStringProperty(supEntity.getSupplierAccountNo()));
		//	sup.setSupplierAsOfDate(Date.from(supEntity.getSupplierAsOfDate().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));
			if(null!=supEntity.getSupplierAsOfDate())
			sup.setSupplierAsOfDate(supEntity.getSupplierAsOfDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
			sup.setSupplierBillingRate(new SimpleDoubleProperty(supEntity.getSupplierBillingRate()));
			sup.setSupplierEmailID(new SimpleStringProperty(supEntity.getSupplierEmailID()));
			sup.setSupplierGovID(new SimpleStringProperty(supEntity.getSupplierGovID()));
			sup.setSupplierFaxNo(new SimpleStringProperty(supEntity.getSupplierFaxNo()));
			sup.setSupplierWebsite(new SimpleStringProperty(supEntity.getSupplierWebsite()));
			sup.setSupplierUnpaidBalance(new SimpleDoubleProperty(supEntity.getSupplierUnpaidBalance()));
			sup.setSupplierGstNo(new SimpleStringProperty(supEntity.getSupplierGstNo()));
			sup.setSupplierOther(new SimpleStringProperty(supEntity.getSupplierOther()));
			sup.setSupplierMobileNo(new SimpleStringProperty(supEntity.getSupplierMobileNo()));
			sup.setSupplierPhoneNo(new SimpleStringProperty(supEntity.getSupplierPhoneNo()));
			Address address=new Address();
			address.setId(supEntity.getAddressEntity().getId());
			address.setZipcode(supEntity.getAddressEntity().getZipcode());
			address.setCity(supEntity.getAddressEntity().getCity());
			address.setCountry(supEntity.getAddressEntity().getCountry());
			address.setState(supEntity.getAddressEntity().getState());
			address.setStreet1(supEntity.getAddressEntity().getStreet1());
			sup.setAddress(address);
			supplierList.add(sup);
		}
		return supplierList;
	}

	public Supplier getSupplierModel(SupplierEntity supEntity) {
		// TODO Auto-generated method stub
		Supplier sup=new Supplier();
		sup.setSupplierID(new SimpleLongProperty(supEntity.getSupplierID()));
		sup.setSupplierName(new SimpleStringProperty(supEntity.getSupplierName()));
		sup.setSupplierCompany(new SimpleStringProperty(supEntity.getSupplierCompany()));
		sup.setSupplierAccountNo(new SimpleStringProperty(supEntity.getSupplierAccountNo()));
	//	sup.setSupplierAsOfDate(Date.from(supEntity.getSupplierAsOfDate().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));
		if(null!=supEntity.getSupplierAsOfDate())
		sup.setSupplierAsOfDate(supEntity.getSupplierAsOfDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
		sup.setSupplierBillingRate(new SimpleDoubleProperty(supEntity.getSupplierBillingRate()));
		sup.setSupplierEmailID(new SimpleStringProperty(supEntity.getSupplierEmailID()));
		sup.setSupplierGovID(new SimpleStringProperty(supEntity.getSupplierGovID()));
		sup.setSupplierFaxNo(new SimpleStringProperty(supEntity.getSupplierFaxNo()));
		sup.setSupplierWebsite(new SimpleStringProperty(supEntity.getSupplierWebsite()));
		sup.setSupplierUnpaidBalance(new SimpleDoubleProperty(supEntity.getSupplierUnpaidBalance()));
		sup.setSupplierGstNo(new SimpleStringProperty(supEntity.getSupplierGstNo()));
		sup.setSupplierOther(new SimpleStringProperty(supEntity.getSupplierOther()));
		sup.setSupplierMobileNo(new SimpleStringProperty(supEntity.getSupplierMobileNo()));
		sup.setSupplierPhoneNo(new SimpleStringProperty(supEntity.getSupplierPhoneNo()));
		return sup;
	}
	
	public Product getProductModel(ProductEntity productEntity) {
	    Product product=new Product();
	    if(null!=productEntity) {
		    product.setProductId(new SimpleIntegerProperty(productEntity.getId()));
		    product.setProductCode(new SimpleLongProperty(productEntity.getProductCode()));
		    product.setDescription(new SimpleStringProperty(productEntity.getDescription()));
		    product.setDiscount(new SimpleDoubleProperty(productEntity.getDiscount()));
		    product.setName(new SimpleStringProperty(productEntity.getName()));
		    product.setProductHSNCode(new SimpleStringProperty(productEntity.getProductHSNCode()));
		    product.setSellPrice(new SimpleDoubleProperty(productEntity.getSell_price()));
		    product.setBuyPrice(new SimpleDoubleProperty(productEntity.getBuy_price()));
		    product.setStock(new SimpleDoubleProperty(productEntity.getStock()));
		    product.setProductCategory(new SimpleStringProperty(productEntity.getProductCategory()));
		    product.setBuyPriceGST(new SimpleStringProperty(productEntity.getBuyGST()));
		    product.setBuyPriceGSTPercentage(new SimpleDoubleProperty(productEntity.getBuyGSTPercentage()));
		    product.setSellPriceGST(new SimpleStringProperty(productEntity.getSellGST()));
		    product.setSellPriceGSTPercentage(new SimpleDoubleProperty(productEntity.getSellGSTPercentage()));
		    product.setLowStockThershold(new SimpleDoubleProperty(productEntity.getLowStockThershold()));
		    Unit unit=getUnitEntity(productEntity);
		    product.setUnit(unit);
	    }
	    return product;
	}
	public List<Product> getProductModels(List<ProductEntity> productEntityList) {
		List<Product> productList=new ArrayList<>();
		
		for(ProductEntity productEntity:productEntityList)
		{
			Product prod=new Product();
			prod.setProductId(new SimpleIntegerProperty(productEntity.getId()));
			prod.setProductCode(new SimpleLongProperty(productEntity.getProductCode()));
			prod.setDescription(new SimpleStringProperty(productEntity.getDescription()));
			prod.setDiscount(new SimpleDoubleProperty(productEntity.getDiscount()));
			prod.setName(new SimpleStringProperty(productEntity.getName()));
			prod.setProductHSNCode(new SimpleStringProperty(productEntity.getProductHSNCode()));
			prod.setSellPrice(new SimpleDoubleProperty(productEntity.getSell_price()));
			prod.setBuyPrice(new SimpleDoubleProperty(productEntity.getBuy_price()));
			prod.setStock(new SimpleDoubleProperty(productEntity.getStock()));
			prod.setProductCategory(new SimpleStringProperty(productEntity.getProductCategory()));
			prod.setBuyPriceGST(new SimpleStringProperty(productEntity.getBuyGST()));
			prod.setBuyPriceGSTPercentage(new SimpleDoubleProperty(productEntity.getBuyGSTPercentage()));
			prod.setSellPriceGST(new SimpleStringProperty(productEntity.getSellGST()));
			prod.setSellPriceGSTPercentage(new SimpleDoubleProperty(productEntity.getSellGSTPercentage()));
			prod.setLowStockThershold(new SimpleDoubleProperty(productEntity.getLowStockThershold()));
			Unit unit=getUnitEntity(productEntity);
			prod.setUnit(unit);
			productList.add(prod);
	
		}
		
		return productList;
	}
	
	
	
	private Unit getUnitEntity(ProductEntity prod) {
		Unit unit=null;
		UnitEntity unitEntity=prod.getUnitEntity();
		if(null!=unitEntity) {
			unit=new Unit(unitEntity);
		}
		return unit;
	}

	/**
	 * Get Customer List From List of CustomerEntityList
	 * @param customerEntityList
	 * @return List<Customer>
	 */
	public List<Customer> getCustomerModels(List<CustomerEntity> customerEntityList) {
		List<Customer> customerList=new ArrayList<>();
		try {
		for(CustomerEntity customerEntity:customerEntityList)
		{
			Customer cust=new Customer();
			cust.setCustomerID(new SimpleIntegerProperty(customerEntity.getCustomerID()));
			//cust.setCustomerID(new SimpleIntegerProperty(customerEntity.getCustomerID()));
			cust.setCustomerName(new SimpleStringProperty(customerEntity.getCustomerName()));
			cust.setAddress(new SimpleStringProperty(customerEntity.getAddress()));
			cust.setCity(new SimpleStringProperty(customerEntity.getCity()));
			cust.setCountry(new SimpleStringProperty(customerEntity.getCountry()));
			cust.setMobile_no(new SimpleStringProperty(customerEntity.getMobileNo()));
			cust.setEmailID(new SimpleStringProperty(customerEntity.getEmailID()));
			cust.setAddAdditionalInfo(new SimpleStringProperty(customerEntity.getAddAdditionalInfo()));
			cust.setState(new SimpleStringProperty(customerEntity.getState()));
			cust.setCustomerGroup(new SimpleStringProperty(customerEntity.getCustomerGroup()));
			cust.setZipCode(new SimpleStringProperty(customerEntity.getZipCode()));
			cust.setAnniversary_date(customerEntity.getAnniversary_Date());
			cust.setBirth_date(customerEntity.getBirth_Date());
			cust.setBalance(new SimpleDoubleProperty(customerEntity.getBalance()));
			cust.setStatus(new SimpleStringProperty(customerEntity.getStatus()));
			DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
			if(null!=customerEntity.getRegDate()) {
		    String strDate = dateFormat.format(customerEntity.getRegDate());
		    cust.setRegistrationDate(new SimpleStringProperty(strDate));
			}else {
				cust.setRegistrationDate(new SimpleStringProperty(""));
			}
			
			customerList.add(cust);
		}
		}catch(Exception e)
		{
			LOGGER.error(e.getMessage(),e);
		}
		return customerList;
	}
	public Customer getCustomerOneModel(CustomerEntity customerEntity) {
		// TODO Auto-generated method stub
		Customer cust=new Customer();
		try {
			if(null!=customerEntity) {
				cust.setCustomerID(new SimpleIntegerProperty(customerEntity.getCustomerID()));
				//cust.setCustomerID(new SimpleIntegerProperty(customerEntity.getCustomerID()));
				cust.setCustomerName(new SimpleStringProperty(customerEntity.getCustomerName()));
				cust.setAddress(new SimpleStringProperty(customerEntity.getAddress()));
				cust.setCity(new SimpleStringProperty(customerEntity.getCity()));
				cust.setCountry(new SimpleStringProperty(customerEntity.getCountry()));
				cust.setMobile_no(new SimpleStringProperty(customerEntity.getMobileNo()));
				cust.setEmailID(new SimpleStringProperty(customerEntity.getEmailID()));
				cust.setAddAdditionalInfo(new SimpleStringProperty(customerEntity.getAddAdditionalInfo()));
				cust.setState(new SimpleStringProperty(customerEntity.getState()));
				cust.setCustomerGroup(new SimpleStringProperty(customerEntity.getCustomerGroup()));
				cust.setZipCode(new SimpleStringProperty(customerEntity.getZipCode()));
				cust.setAnniversary_date(customerEntity.getAnniversary_Date());
				cust.setBirth_date(customerEntity.getBirth_Date());
				cust.setBalance(new SimpleDoubleProperty(customerEntity.getBalance()));
				cust.setStatus(new SimpleStringProperty(customerEntity.getStatus()));
				DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
				if(null!=customerEntity.getRegDate()) {
					String strDate = dateFormat.format(customerEntity.getRegDate());
					cust.setRegistrationDate(new SimpleStringProperty(strDate));
				}else {
					cust.setRegistrationDate(new SimpleStringProperty(""));
				}
			}
			}catch(Exception e)
			{
				LOGGER.error(e.getMessage(),e);
			}
		return cust;
	}

	public List<String> getProductCategoryList(List<ProductCategoryEntity> categoryListEntity) {
		List<String> categoryList=new ArrayList<>();
		categoryListEntity.forEach(categoryEntity->{
			categoryList.add(categoryEntity.getCategoryName());	
		});
		return categoryList;
	}

	public List<Unit> getUnitList(List<UnitEntity> unitEntityList) {
		List<Unit> units=new ArrayList<>();
		unitEntityList.forEach(unitEntity->{
			units.add(new Unit(unitEntity));
		});
		return units;
	}

	public List<Invoice> getInvoiceFromInvoiceEntity(List<InvoiceEntity> invoiceEntityList) {
		List<Invoice> invoiceList = new ArrayList<Invoice>();
		for(InvoiceEntity invoiceEntity:invoiceEntityList) {
			Invoice invoice = new Invoice();
			invoice.setInvoiceId(new SimpleLongProperty(invoiceEntity.getInvoiceID()));
			CustomerEntity  customerEntity = invoiceEntity.getCustomerEntity();
			Customer customer = getCustomerOneModel(customerEntity);
			invoice.setCustomer(customer);			
			invoice.setInvoiceDate(invoice.getInvoiceDate());
			invoiceList.add(invoice); 
		}
		
		return invoiceList;
	}

	public Invoice getInvoiceModel(InvoiceEntity invoiceEntity) {
		Invoice invoice = new Invoice();
		invoice.setInvoiceId(new SimpleLongProperty(invoiceEntity.getInvoiceID()));
		CustomerEntity  customerEntity = invoiceEntity.getCustomerEntity();
		Customer customer = getCustomerOneModel(customerEntity);
		invoice.setCustomer(customer);			
		invoice.setInvoiceDate(invoiceEntity.getInvoiceDate());
		return invoice;
	}
	

}

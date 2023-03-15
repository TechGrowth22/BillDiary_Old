package com.billdiary.daoUtility;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.billdiary.entities.AddressEntity;
import com.billdiary.entities.CustomerEntity;
import com.billdiary.entities.InvoiceEntity;
import com.billdiary.entities.ProductEntity;
import com.billdiary.entities.SoldProductsEntity;
import com.billdiary.entities.SupplierEntity;
import com.billdiary.entities.UnitEntity;
import com.billdiary.entities.UserEntity;

import com.billdiary.model.Customer;
import com.billdiary.model.Invoice;
import com.billdiary.model.Product;
import com.billdiary.model.ProductColumns;
import com.billdiary.model.Supplier;
import com.billdiary.model.Unit;
import com.billdiary.model.User;
import com.billdiary.repository.InvoiceRepository;
import com.billdiary.repository.ProductRepository;
import com.billdiary.repository.UnitRepository;
import com.billdiary.service.PriceService;
import com.billdiary.service.ProductService;
import com.billdiary.utility.DataTypeConverter;
import com.billdiary.utility.DateUtiliy;

import javafx.collections.ObservableList;

@Component
public class ModelTOEntityMapper {
	
	final static Logger LOGGER = LoggerFactory.getLogger(ModelTOEntityMapper.class); 
	@Autowired
	UnitRepository unitRepository;
	
	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	InvoiceRepository invoiceRepository;
	
	@Autowired
	PriceService priceService;
	
	
	public UserEntity getUserEntity(User user)
	{
		UserEntity userEntity=new UserEntity();
		if(null!=user)
		{
			userEntity.setId(user.getId());
			userEntity.setUserName(user.getUserName());
			userEntity.setPassword(user.getPassword());
			userEntity.setRole(user.getRole());
		}
		
		return userEntity;
	}

	public SupplierEntity getSupplierEntity(Supplier sup) {
		// TODO Auto-generated method stub
		SupplierEntity supEntity=new SupplierEntity();
		supEntity.setSupplierID(0);
		supEntity.setSupplierName(sup.getSupplierName());
		supEntity.setSupplierCompany(sup.getSupplierCompany());
		supEntity.setSupplierAccountNo(sup.getSupplierAccountNo());
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date asOfDate=null;
		
		try {
			if(""!=sup.getSupplierAsOfDate()) {
			asOfDate = df.parse(sup.getSupplierAsOfDate());
			}
		} catch (ParseException e) {
			LOGGER.error(e.getMessage(),e);
		
		}
		supEntity.setSupplierAsOfDate(asOfDate);
		supEntity.setSupplierBillingRate(sup.getSupplierBillingRate());
		supEntity.setSupplierEmailID(sup.getSupplierEmailID());
		supEntity.setSupplierGovID(sup.getSupplierGovID());
		supEntity.setSupplierFaxNo(sup.getSupplierFaxNo());
		supEntity.setSupplierWebsite(sup.getSupplierWebsite());
		supEntity.setSupplierUnpaidBalance(sup.getSupplierUnpaidBalance());
		supEntity.setSupplierGstNo(sup.getSupplierGstNo());
		supEntity.setSupplierOther(sup.getSupplierOther());
		supEntity.setSupplierMobileNo(sup.getSupplierMobileNo());
		supEntity.setSupplierPhoneNo(sup.getSupplierPhoneNo());
		
		AddressEntity addressEnitity=new AddressEntity();
		//addressEnitity.setId(sup.getAddress().getId());
		addressEnitity.setId(null);
		addressEnitity.setStreet1(sup.getAddress().getStreet1());
		addressEnitity.setCity(sup.getAddress().getCity());
		addressEnitity.setState(sup.getAddress().getState());
		addressEnitity.setCountry(sup.getAddress().getCountry());
		addressEnitity.setZipcode(sup.getAddress().getZipcode());
		supEntity.setAddressEntity(addressEnitity);
		return supEntity;
	}

	public InvoiceEntity getInvoiceEntity(Invoice inv) {
		InvoiceEntity invoiceEntity=new InvoiceEntity();
		invoiceEntity.setCustomerEntity(getCustomerEntity(inv.getCustomer()));
		/** Set Customer ID**/
		invoiceEntity.getCustomerEntity().setCustomerID(inv.getCustomer().getCustomerID());
		invoiceEntity.setAmountDue(inv.getAmountDue());
		invoiceEntity.setFinalAmount(inv.getFinalAmount());
		invoiceEntity.setInvoiceDate(inv.getInvoiceDate());
		invoiceEntity.setInvoiceDueDate(inv.getInvoiceDueDate());
		invoiceEntity.setLastAmountPaidDate(inv.getLastAmountPaidDate());
		invoiceEntity.setPaidAmount(inv.getPaidAmount());
		invoiceEntity.setProduct_sale_qty(inv.getProduct_sale_qty());
		return invoiceEntity;
	}
	
	/**
	 * Get CustomerEntity List From ObservableCustomerList 
	 * @param obcustomerList
	 * @return List<CustomerEntity>
	 */

	public List<CustomerEntity> getCustEntitiesFromObservableList(ObservableList<Customer> obcustomerList) {
		// TODO Auto-generated method stub
		
		List<CustomerEntity> customerEntityList = new ArrayList<>();
		for(Customer cust:obcustomerList)
		{
			CustomerEntity customerEntity =new CustomerEntity();
			customerEntity.setCustomerID(cust.getCustomerID());
			customerEntity.setCustomerName(cust.getCustomerName());
			customerEntity.setMobileNo(cust.getMobile_no());
			customerEntity.setAddress(cust.getAddress());
			customerEntity.setCity(cust.getCity());
			customerEntity.setCountry(cust.getCountry());
			customerEntity.setEmailID(cust.getEmailID());
			customerEntity.setAddAdditionalInfo(cust.getAddAdditionalInfo());
			customerEntity.setState(cust.getState());
			customerEntity.setCustomerGroup(cust.getCustomerGroup());
			customerEntity.setZipCode(cust.getZipCode());
			customerEntity.setAnniversary_Date(cust.getAnniversary_date());
			customerEntity.setBirth_Date(cust.getBirth_date());
			customerEntity.setState(cust.getStatus());
			customerEntityList.add(customerEntity);
		}
		return customerEntityList;
	}
	
	/**
	 * customer model to Customer entity
	 */
	public CustomerEntity getCustomerEntity(Customer cust) {
		// TODO Auto-generated method stub
		CustomerEntity customerEntity =new CustomerEntity();
		customerEntity.setCustomerID(0);
		customerEntity.setCustomerName(cust.getCustomerName());
		customerEntity.setMobileNo(cust.getMobile_no());
		customerEntity.setAddress(cust.getAddress());
		customerEntity.setCity(cust.getCity());
		customerEntity.setCountry(cust.getCountry());
		customerEntity.setEmailID(cust.getEmailID());
		customerEntity.setAddAdditionalInfo(cust.getAddAdditionalInfo());
		customerEntity.setState(cust.getState());
		customerEntity.setCustomerGroup(cust.getCustomerGroup());
		customerEntity.setZipCode(cust.getZipCode());
		customerEntity.setAnniversary_Date(cust.getAnniversary_date());
		customerEntity.setBirth_Date(cust.getBirth_date());
		customerEntity.setBalance(cust.getBalance());
		customerEntity.setStatus(cust.getStatus());
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		Date regDate=null;
		try {
			regDate = df.parse("" == cust.getRegistrationDate()? String.valueOf(DateUtiliy.getDateStringFromLocalDate(LocalDate.now(),"MM/dd/yyyy")) :cust.getRegistrationDate() );
		} catch (ParseException e) {
			LOGGER.error(e.getMessage(),e);
		}
		customerEntity.setRegDate(regDate);
		return customerEntity;
	}
	
	public List<ProductEntity> getProdEntitiesFromObservableList(ObservableList<Product> obproductList) {
		List<ProductEntity> productEntityList = new ArrayList<>();
		for(Product prod:obproductList)
		{
			ProductEntity productEntity =new ProductEntity();
			productEntity.setDescription(prod.getDescription());
			productEntity.setProductCode(prod.getProductCode());
			productEntity.setProductHSNCode(prod.getProductHSNCode());
			productEntity.setDiscount(prod.getDiscount());
			productEntity.setId(prod.getProductId());
			productEntity.setName(prod.getName());
			productEntity.setSell_price(prod.getSellPrice());
			productEntity.setBuy_price(prod.getBuyPrice());
			productEntity.setStock(prod.getStock());
			productEntity.setProductCategory(prod.getProductCategory());
			productEntity.setLowStockThershold(prod.getLowStockThershold());
			productEntityList.add(productEntity);
		}
		return productEntityList;
	}
	
	public List<ProductEntity> getProdEntities(List<Product> productList) {
		List<ProductEntity> productEntityList = new ArrayList<>();
		for(Product prod:productList)
		{
			ProductEntity productEntity =new ProductEntity();
			productEntity.setDescription(prod.getDescription());
			productEntity.setProductCode(prod.getProductCode());
			productEntity.setProductHSNCode(prod.getProductHSNCode());
			productEntity.setDiscount(prod.getDiscount());
			productEntity.setId(prod.getProductId());
			productEntity.setName(prod.getName());
			productEntity.setSell_price(prod.getSellPrice());
			productEntity.setBuy_price(prod.getBuyPrice());
			productEntity.setStock(prod.getStock());
			productEntity.setProductCategory(prod.getProductCategory());
			productEntity.setLowStockThershold(prod.getLowStockThershold());
			productEntityList.add(productEntity);
		}
		return productEntityList;
	}
	public ProductEntity getProductEntity(Product prod) {
		ProductEntity productEntity =new ProductEntity();
		productEntity.setId(0);
		productEntity.setProductCode(prod.getProductCode());
		productEntity.setProductCode(prod.getProductCode());
		productEntity.setDescription(prod.getDescription());
		productEntity.setName(prod.getName());
		productEntity.setProductHSNCode(prod.getProductHSNCode());
		productEntity.setSell_price(prod.getSellPrice());
		productEntity.setBuy_price(prod.getBuyPrice());
		productEntity.setDiscount(prod.getDiscount());
		productEntity.setStock(prod.getStock());
		productEntity.setProductCategory(prod.getProductCategory());
		productEntity.setSellGST(prod.getRetailGST());
		productEntity.setBuyGST(prod.getBuyPriceGST());
		productEntity.setSellGSTPercentage(prod.getSellPriceGSTPercentage());
		productEntity.setBuyGSTPercentage(prod.getBuyPriceGSTPercentage());
		productEntity.setLowStockThershold(prod.getLowStockThershold());
		UnitEntity unitEntity=getUnitEntity(prod);
		productEntity.setUnitEntity(unitEntity);
		return productEntity;
	}

	private UnitEntity getUnitEntity(Product prod) {
		UnitEntity UnitEntity=new UnitEntity();
		Unit unit=prod.getUnit();
		if(null!=unit) {
			UnitEntity.setUnitId(unit.getUnitId());
			UnitEntity.setName(unit.getUnitName());
		}
		return UnitEntity;
	}
	
	public List<ProductEntity> getProductEntityFromProductColumns(List<ProductColumns> list){
		List<ProductEntity> productEntityList = new ArrayList<>();
		for(ProductColumns prod: list) {
			ProductEntity productEntity =new ProductEntity();
			productEntity.setDescription(prod.getDescription());
			productEntity.setProductCode(DataTypeConverter.getLongFromString(prod.getProductCode()));
			productEntity.setProductHSNCode(prod.getProductHSNCode());
			productEntity.setDiscount(DataTypeConverter.getDoubleFromString(prod.getDiscount()));
			productEntity.setId(DataTypeConverter.getIntegerFromString(prod.getProduct_id()));
			productEntity.setName(prod.getProductName());
			if("Y".equals(prod.getSellPriceGST())) {
				Double value = DataTypeConverter.getDoubleFromString(prod.getSellPrice());
				Double retailPrice = priceService.getRetailPrice(value, DataTypeConverter.getDoubleFromString(prod.getSellPriceGSTPercentage()), DataTypeConverter.getDoubleFromString(prod.getDiscount()));
				productEntity.setSell_price(retailPrice);
			}else {
				productEntity.setSell_price(DataTypeConverter.getDoubleFromString(prod.getSellPrice()));
			}
			
			if("Y".equals(prod.getBuyPriceGST())) {
				Double value = DataTypeConverter.getDoubleFromString(prod.getBuyPrice());
				Double wholesalePrice = priceService.getWholeSalePrice(value, DataTypeConverter.getDoubleFromString(prod.getBuyPriceGSTPercentage()), DataTypeConverter.getDoubleFromString(prod.getDiscount()));
				productEntity.setBuy_price(wholesalePrice);
			}else {
				productEntity.setBuy_price(DataTypeConverter.getDoubleFromString(prod.getBuyPrice()));
			}
			
		
			
			
			productEntity.setStock(DataTypeConverter.getDoubleFromString(prod.getStock()));
			productEntity.setProductCategory(prod.getProductCategory());
			productEntity.setLowStockThershold(DataTypeConverter.getDoubleFromString(prod.getLowStockThershold()));
			productEntity.setSellGST(prod.getSellPriceGST());
			productEntity.setSellGSTPercentage(DataTypeConverter.getDoubleFromString(prod.getSellPriceGSTPercentage()));
			productEntity.setBuyGST(prod.getBuyPriceGST());
			productEntity.setBuyGSTPercentage(DataTypeConverter.getDoubleFromString(prod.getBuyPriceGSTPercentage()));
			List<UnitEntity> u = unitRepository.findByName(prod.getUnitEntity());
			if(u.size() > 0) {
				productEntity.setUnitEntity(u.get(0));
			}else {
				UnitEntity unit=new UnitEntity();
				unit.setName(prod.getUnitEntity());
				unit.setUnitId(0);
				unit=unitRepository.save(unit);
				productEntity.setUnitEntity(unit);
			}
			productEntityList.add(productEntity);
		}
		
		return productEntityList;
	}

	public List<SoldProductsEntity> getSoldProductsListFromProduct(Invoice inv, List<Product> productList) {
		List<SoldProductsEntity> list = new ArrayList<SoldProductsEntity>();
		Optional<InvoiceEntity> optionalInvoice = invoiceRepository.findById(inv.getInvoiceId()); 
		InvoiceEntity invoice = optionalInvoice.get();
		for(Product product: productList) {
			SoldProductsEntity soldProduct = new SoldProductsEntity();
			soldProduct.setSoldProductId(0L);
			soldProduct.setInvoiceEntity(invoice);
			Optional<ProductEntity> optionalProduct = productRepository.findById(product.getProductId());
			ProductEntity productEntity = optionalProduct.get();
			soldProduct.setProductEntity(productEntity);
			soldProduct.setSoldProductQuantity(product.getQuantity());
			list.add(soldProduct);
		}
		
		return list;
	}



}

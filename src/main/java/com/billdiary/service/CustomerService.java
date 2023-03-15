package com.billdiary.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import com.billdiary.daoUtility.EntityTOModelMapper;
import com.billdiary.daoUtility.ModelTOEntityMapper;
import com.billdiary.entities.CustomerEntity;

import com.billdiary.model.Customer;

import com.billdiary.repository.CustomerRepository;

import com.billdiary.utility.Constants;

import javafx.collections.ObservableList;

@Service
public class CustomerService {
	
	final static Logger LOGGER = LoggerFactory.getLogger(CustomerService.class); 

	/*@Autowired
	CustomerDAO customerDAO;
	*/
	@Autowired
	ModelTOEntityMapper modelTOEntityMapper;
	
	@Autowired
	EntityTOModelMapper entityTOModelMapper;
	@Autowired
	CustomerRepository customerRepository;
	
	
	public List<Customer> fetchCustomers()
	{
		LOGGER.info("Entering in fetchCustomers");
		List<Customer> customerList=new ArrayList<>();
		List<CustomerEntity> customerEntityList=new ArrayList<>();
		//customerEntityList=customerDAO.fetchCustomers();
		customerEntityList=customerRepository.findAll();
		//customerEntityList=customerDAO.fetchActiveCustomers();
		customerList=entityTOModelMapper.getCustomerModels(customerEntityList);
		LOGGER.info("Exiting in fetchCustomers");
		return customerList;
	}
	
	
	public boolean deleteCustomer(int id)
	{
		boolean customerDeleted=false;
		LOGGER.info("Entering in deleteCustomer");
		customerRepository.setInactive(id);
		customerDeleted=true;
		LOGGER.info("Exiting in deleteCustomer");
		return customerDeleted;
	}
	public List<Customer> saveCustomer(ObservableList<Customer> obcustomerList)
	{
		List<CustomerEntity>  customerEntityList = modelTOEntityMapper.getCustEntitiesFromObservableList(obcustomerList);
		List<CustomerEntity> updatedCustEntities = new ArrayList<>();
		//updatedCustEntities=customerDAO.saveCustomer(customerEntityList);
		updatedCustEntities=customerRepository.saveAll(customerEntityList);
		List<Customer> customerList =new ArrayList<>();
		customerList=entityTOModelMapper.getCustomerModels(updatedCustEntities);
		
		return customerList;
	}
	
	public boolean addCustomer(Customer cust)
	{
		
		boolean customerAdded=false;
		CustomerEntity custEntity=modelTOEntityMapper.getCustomerEntity(cust);
		//customerAdded=customerDAO.addCustomer(custEntity);
		CustomerEntity savedEntity=customerRepository.save(custEntity);
		customerAdded=null==savedEntity?false:true;
		return customerAdded;
	}
	
	public Customer updateCustomer(Customer cust)
	{
		LOGGER.info("Entering in updateCustomer");
		Customer updatedCustomer=null;
		CustomerEntity updatedCustEnitity=null;
		CustomerEntity custEntity=modelTOEntityMapper.getCustomerEntity(cust);
		custEntity.setCustomerID(cust.getCustomerID());
		updatedCustEnitity=customerRepository.saveAndFlush(custEntity);
		updatedCustomer=entityTOModelMapper.getCustomerOneModel(updatedCustEnitity);
		LOGGER.info("Exiting in updateCustomer");
		return updatedCustomer;
	}
	
	/**
	 * All pagination methods
	 */
	public long getCustomerCount() {
		long count=customerRepository.count();
		return count;
	}
	public List<Customer> getCustomers(int pages, int index,int rowsPerPage) {
		LOGGER.info("Entering in getCustomers");
		List<Customer> customerList=new ArrayList<>();
		Page<CustomerEntity> customerEntities = customerRepository.findAll(PageRequest.of(index, rowsPerPage, Sort.Direction.ASC, "status"));
		customerEntities.forEach(customerEntity->{
			customerList.add(entityTOModelMapper.getCustomerOneModel(customerEntity));
		});
		LOGGER.info("Exiting in getCustomers");
		return customerList;
	}


	public void updateCustomerBalance(Integer customerID, Double amount) {
		customerRepository.updateCustomerBalance(customerID,amount);
	}


	public Customer getDefaultCustomer() {
		CustomerEntity custEntity=customerRepository.findByCustomerNameAndAddressAndMobileNo(Constants.DEFAULT_CUSTOMER_NAME,Constants.DEFAULT_CUSTOMER_ADDRESS,Constants.DEFAULT_CUSTOMER_MOBILE_NO);
		if(null==custEntity) {
			custEntity=new CustomerEntity();
			custEntity.setCustomerID(0);
			custEntity.setCustomerName(Constants.DEFAULT_CUSTOMER_NAME);
			custEntity.setAddress(Constants.DEFAULT_CUSTOMER_ADDRESS);
			custEntity.setMobileNo(Constants.DEFAULT_CUSTOMER_MOBILE_NO);
			custEntity.setStatus(Constants.ACTIVE);
			customerRepository.save(custEntity);
			custEntity=customerRepository.findByCustomerNameAndAddressAndMobileNo(Constants.DEFAULT_CUSTOMER_NAME,Constants.DEFAULT_CUSTOMER_ADDRESS,Constants.DEFAULT_CUSTOMER_MOBILE_NO);
		}
		Customer cust=entityTOModelMapper.getCustomerOneModel(custEntity);
		return cust;
	}


	public Customer addNewCustomerByInvoive(Customer customer) {
		CustomerEntity custEntity=modelTOEntityMapper.getCustomerEntity(customer);
		custEntity=customerRepository.saveAndFlush(custEntity);
		Customer cust=entityTOModelMapper.getCustomerOneModel(custEntity);
		return cust;
	}


	public Customer getCustomerById(String custID) {
		Customer cust=new Customer();
		try {
		Optional<CustomerEntity> custEntity=customerRepository.findById(Integer.parseInt(custID));
		cust=entityTOModelMapper.getCustomerOneModel(custEntity.get());
		}catch(Exception e) {
			LOGGER.error("Exception Occured"+e);
		}
		return cust;
	}
}

package com.billdiary.repository;


import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.billdiary.entities.CustomerEntity;

public interface CustomerRepository extends JpaRepository<CustomerEntity, Integer> {

	@Modifying
	@Query("update CustomerEntity customer set customer.status='INACTIVE' where customer.customerID=:customerID")
	@Transactional
	public int setInactive(@Param("customerID") int id);
	
	@Modifying
	@Query("update CustomerEntity customer set customer.balance=:balance where customer.customerID=:customerID")
	@Transactional
	public int updateCustomerBalance(@Param("customerID") int customerId,@Param("balance")double balance);
	
	public CustomerEntity findByCustomerNameAndAddressAndMobileNo(String customerName,String address,String mobile_no);
	
}

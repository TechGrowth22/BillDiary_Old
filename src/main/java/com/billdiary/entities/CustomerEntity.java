package com.billdiary.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name = "customer")
public class CustomerEntity implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "customer_id")
	private int customerID;
	
	@Column(name = "customer_name")
	private String customerName;
	
	@Column(name = "address")
	private String address;
	
	@Column(name="email_id")
	private String emailID;

	@Column(name = "mobile_no")
	private String mobileNo;
	
	@Column(name = "city")
	private String city;
	
	@Column(name = "country")
	private String country;
	
	
	@Column(name = "state")
	private String state;
	
	@Column(name = "customer_group")
	private String customerGroup;
	
	@Column(name = "zipCode")
	private String zipCode;
	
	
	@Column(name = "addAdditional_info")
	private String addAdditionalInfo;
	
	@Column(name = "regDate")
	private Date regDate;
	
	@Column(name="anniversary_Date")
	private LocalDate anniversary_date;
	
	@Column(name="birth_Date")
	private LocalDate birth_date;
	
	@Column(name="balance")
	private double balance;
	
	@OneToMany(mappedBy = "customerEntity")
	private List<InvoiceEntity> invoiceEntities;
	
	@Column(name="status")
	private String status;
	
	
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	public LocalDate getAnniversary_Date() {
		return anniversary_date;
	}
	public void setAnniversary_Date(LocalDate anniversary_date) {
		this.anniversary_date = anniversary_date;
	}
	public LocalDate getBirth_Date() {
		return birth_date;
	}
	public void setBirth_Date(LocalDate birth_date) {
		this.birth_date = birth_date;
	}
	/*@OneToOne(cascade = CascadeType.ALL) @JoinColumn( name = "address_id" )
    private AddressEntity addressEntity;*/
	//@OneToMany(mappedBy = "customerEntity", cascade=CascadeType.ALL, orphanRemoval=false)
	
	
	
	public List<InvoiceEntity> getInvoiceEntities() {
		return invoiceEntities;
	}
	public void setInvoiceEntities(List<InvoiceEntity> invoiceEntities) {
		this.invoiceEntities = invoiceEntities;
	}
	public Date getRegDate() {
		return regDate;
	}
	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}

	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	
	public int getCustomerID() {
		return customerID;
	}
	public void setCustomerID(int customerID) {
		this.customerID = customerID;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getEmailID() {
		return emailID;
	}
	public void setEmailID(String emailID) {
		this.emailID = emailID;
	}
	
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCustomerGroup() {
		return customerGroup;
	}
	public void setCustomerGroup(String customerGroup) {
		this.customerGroup = customerGroup;
	}
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	public String getAddAdditionalInfo() {
		return addAdditionalInfo;
	}
	public void setAddAdditionalInfo(String addAdditionalInfo) {
		this.addAdditionalInfo = addAdditionalInfo;
	}
	

}

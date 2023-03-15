package com.billdiary.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity
@Table(name = "address")
public class AddressEntity {
	
	
	@Id @GeneratedValue
	@Column(name = "address_id")
    private Long id; 
	@Column(name = "street1_id")
    private String street1;
	@Column(name = "street2_id")
    private String street2; 
	@Column(name = "city")
    private String city;
	@Column(name = "state")
    private String state;
	@Column(name = "country")
    private String country;
	@Column(name = "zipcode")
    private String zipcode;
	
	 /* @OneToOne(mappedBy = "addressEntity")
    private CustomerEntity customerEntity;*/
    
    @OneToOne(mappedBy = "addressEntity")
    private SupplierEntity supllierEntity;
    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStreet1() {
		return street1;
	}

	public void setStreet1(String street1) {
		this.street1 = street1;
	}

	public String getStreet2() {
		return street2;
	}

	public void setStreet2(String street2) {
		this.street2 = street2;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public SupplierEntity getSupllierEntity() {
		return supllierEntity;
	}

	public void setSupllierEntity(SupplierEntity supllierEntity) {
		this.supllierEntity = supllierEntity;
	}
    

}

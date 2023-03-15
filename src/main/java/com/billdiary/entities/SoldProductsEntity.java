package com.billdiary.entities;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "soldProduct")
public class SoldProductsEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "sold_product_id")
	private Long soldProductId;
	
	@ManyToOne(cascade = {CascadeType.DETACH}) @JoinColumn( name = "invoice_id" )
    private InvoiceEntity invoiceEntity;
	
	@ManyToOne(cascade = {CascadeType.DETACH}) @JoinColumn( name = "product_id" )
    private ProductEntity productEntity;

	private Double soldProductQuantity;
	
	public Long getSoldProductId() {
		return soldProductId;
	}

	public void setSoldProductId(Long soldProductId) {
		this.soldProductId = soldProductId;
	}

	public InvoiceEntity getInvoiceEntity() {
		return invoiceEntity;
	}

	public void setInvoiceEntity(InvoiceEntity invoiceEntity) {
		this.invoiceEntity = invoiceEntity;
	}

	public ProductEntity getProductEntity() {
		return productEntity;
	}

	public void setProductEntity(ProductEntity productEntity) {
		this.productEntity = productEntity;
	}

	public Double getSoldProductQuantity() {
		return soldProductQuantity;
	}

	public void setSoldProductQuantity(Double soldProductQuantity) {
		this.soldProductQuantity = soldProductQuantity;
	}
		
	
	
}

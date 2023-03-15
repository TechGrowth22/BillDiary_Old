package com.billdiary.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity
@Table(name = "invoice")
@NamedQueries({
	@NamedQuery(
            name = "InvoiceEntity.generateInvoiceNO",
            query = "select coalesce(max(invoice.invoiceID),0) FROM InvoiceEntity invoice"
            )
})
public class InvoiceEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "invoice_id")
	private Long invoiceID;
	
	@ManyToOne(cascade = {CascadeType.DETACH}) @JoinColumn( name = "customer_id" )
    private CustomerEntity customerEntity;
	
	@Column(name = "product_sale_qty")
	private int product_sale_qty;
	
	@Column(name = "final_Amount")
	private double finalAmount;
	
	@Column(name = "paid_Amount")
	private double paidAmount;
	
	@Column(name = "amount_Due")
	private double amountDue;
	
	@Column(name = "invoice_Date")
	private LocalDate invoiceDate;
	
	@Column(name = "invoice_Due_Date")
	private LocalDate invoiceDueDate;
	
	@Column(name = "last_Amount_Paid_Date")
	private LocalDate lastAmountPaidDate;
	
	@OneToMany(mappedBy="invoiceEntity")
    private List<SoldProductsEntity> SoldProducts;
	
	
	public Long getInvoiceID() {
		return invoiceID;
	}

	public void setInvoiceID(Long invoiceID) {
		this.invoiceID = invoiceID;
	}

	public CustomerEntity getCustomerEntity() {
		return customerEntity;
	}

	public void setCustomerEntity(CustomerEntity customerEntity) {
		this.customerEntity = customerEntity;
	}

	public int getProduct_sale_qty() {
		return product_sale_qty;
	}

	public void setProduct_sale_qty(int product_sale_qty) {
		this.product_sale_qty = product_sale_qty;
	}

	public double getFinalAmount() {
		return finalAmount;
	}

	public void setFinalAmount(double finalAmount) {
		this.finalAmount = finalAmount;
	}

	public double getPaidAmount() {
		return paidAmount;
	}

	public void setPaidAmount(double paidAmount) {
		this.paidAmount = paidAmount;
	}

	public double getAmountDue() {
		return amountDue;
	}

	public void setAmountDue(double amountDue) {
		this.amountDue = amountDue;
	}

	public LocalDate getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(LocalDate invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public LocalDate getInvoiceDueDate() {
		return invoiceDueDate;
	}

	public void setInvoiceDueDate(LocalDate invoiceDueDate) {
		this.invoiceDueDate = invoiceDueDate;
	}

	public LocalDate getLastAmountPaidDate() {
		return lastAmountPaidDate;
	}

	public void setLastAmountPaidDate(LocalDate lastAmountPaidDate) {
		this.lastAmountPaidDate = lastAmountPaidDate;
	}
	
	
	
}

package com.billdiary.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.billdiary.entities.InvoiceEntity;

public interface InvoiceRepository extends JpaRepository<InvoiceEntity, Long> {

	public long generateInvoiceNO();
	
	
	@Query("SELECT SUM(invoice.finalAmount) FROM InvoiceEntity invoice")
	public Double totalInvoicesAmount();
}

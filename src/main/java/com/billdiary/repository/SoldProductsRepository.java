package com.billdiary.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.billdiary.entities.InvoiceEntity;
import com.billdiary.entities.SoldProductsEntity;

public interface SoldProductsRepository extends JpaRepository<SoldProductsEntity, Long> {

	
}

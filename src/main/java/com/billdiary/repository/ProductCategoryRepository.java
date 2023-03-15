package com.billdiary.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.billdiary.entities.ProductCategoryEntity;

public interface ProductCategoryRepository extends JpaRepository<ProductCategoryEntity, Long> {

}

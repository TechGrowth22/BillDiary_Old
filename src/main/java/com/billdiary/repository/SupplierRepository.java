package com.billdiary.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.billdiary.entities.SupplierEntity;

public interface SupplierRepository extends JpaRepository<SupplierEntity, Long> {

}

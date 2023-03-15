package com.billdiary.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.billdiary.entities.ShopEntity;

public interface ShopRepository extends JpaRepository<ShopEntity, Long> {

}

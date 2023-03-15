package com.billdiary.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.billdiary.entities.UnitEntity;

public interface UnitRepository extends JpaRepository<UnitEntity, Long> {

	List<UnitEntity> findByName(String name);
}

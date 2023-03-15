package com.billdiary.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.billdiary.daoUtility.EntityTOModelMapper;
import com.billdiary.daoUtility.ModelTOEntityMapper;
import com.billdiary.entities.SupplierEntity;
import com.billdiary.model.Supplier;
import com.billdiary.repository.SupplierRepository;

@Service
public class SupplierService {

	final static Logger LOGGER = LoggerFactory.getLogger(SupplierService.class); 
	
	@Autowired
	SupplierRepository 	supplierRepository;
	
	@Autowired
	ModelTOEntityMapper modelTOEntityMapper;
	
	@Autowired
	EntityTOModelMapper entityTOModelMapper;
	
	
	public void addNewSupplier(Supplier sup) {
		ModelTOEntityMapper mapper=new ModelTOEntityMapper();
		SupplierEntity supEnitity=mapper.getSupplierEntity(sup);
		supplierRepository.save(supEnitity);
	}

	public List<Supplier> fetchSuppliers() {
		List<Supplier> supplierList=new ArrayList<>();
		try {
		
		List<SupplierEntity> supplierEntityList=new ArrayList<>();
		supplierEntityList=supplierRepository.findAll();
		
		supplierList=entityTOModelMapper.getSupplierModels(supplierEntityList);
		}catch(Exception e) {
			LOGGER.error(e.getMessage(),e);
		}
		return supplierList;
	}

	public void removeSupplier(long supID) {
		// TODO Auto-generated method stub
		supplierRepository.deleteById(supID);
		
	}

	public Supplier updateSupplier(Supplier sup) {
		
		SupplierEntity supEntity=modelTOEntityMapper.getSupplierEntity(sup);
		supEntity.setSupplierID(sup.getSupplierID());
		supEntity.getAddressEntity().setId(sup.getAddress().getId());
		//supEntity.setSupplierID(sup.getSupplierID());
		SupplierEntity updatedSupEntity=supplierRepository.save(supEntity);
		sup=entityTOModelMapper.getSupplierModel(updatedSupEntity);
		return sup;
	}
	/**
	 * All pagination methods
	 */
	public long getSupplierCount() {
		long count=supplierRepository.count();
		return count;
	}
	public List<Supplier> getSuppliers(int pages, int index,int rowsPerPage) {
		
		Page<SupplierEntity> supplierEntityList=supplierRepository.findAll(PageRequest.of(index, rowsPerPage));
		List<Supplier> supplierList=new ArrayList<>();
		supplierEntityList.forEach(supplierEntity->{
			supplierList.add(entityTOModelMapper.getSupplierModel(supplierEntity));
		});
		return supplierList;
	}
}

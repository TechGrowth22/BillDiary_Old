package com.billdiary.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.billdiary.daoUtility.ModelTOEntityMapper;
import com.billdiary.entities.SoldProductsEntity;
import com.billdiary.model.Invoice;
import com.billdiary.model.Product;
import com.billdiary.repository.SoldProductsRepository;

import javafx.collections.ObservableList;

@Service
public class SoldProductService {

	
	@Autowired
	SoldProductsRepository soldProductsRepository;
	
	@Autowired
	ModelTOEntityMapper modelTOEntityMapper;
	
	public boolean updateSoldProducts(Invoice inv, ObservableList<Product> data) {
		
		List<Product> productList = data.subList(0, data.size());
		
		List<SoldProductsEntity> soldproductsList = modelTOEntityMapper.getSoldProductsListFromProduct(inv,productList);
		soldProductsRepository.saveAll(soldproductsList);
		return false;
		
	}
}

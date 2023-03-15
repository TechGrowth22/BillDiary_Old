package com.billdiary.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.billdiary.entities.ShopEntity;
import com.billdiary.repository.ShopRepository;
@Service
public class ShopService {
	
	@Autowired
	ShopRepository shopRepository;
	
	public void saveShopDetails(ShopEntity addShopDetails) {
		shopRepository.save(addShopDetails);
	}
	public List<ShopEntity> getShopDetails() {
		List<ShopEntity> shop = new ArrayList<>();
		shop = shopRepository.findAll();
		return shop;
	}
	public void editShopDetails(ShopEntity addShopDetails) {
		List<ShopEntity> shopList = new ArrayList<>();
		shopList = shopRepository.findAll();
		ShopEntity shopEntity=shopList.get(0);
		shopRepository.delete(shopEntity);
		shopRepository.save(addShopDetails);
		//shopDAO.editShopDetails(addShopDetails);
	}
}

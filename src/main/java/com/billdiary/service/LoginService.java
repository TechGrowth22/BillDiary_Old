package com.billdiary.service;

//import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.billdiary.daoUtility.ModelTOEntityMapper;
import com.billdiary.entities.UserEntity;

import com.billdiary.model.User;
import com.billdiary.repository.UserRepository;


@Service
public class LoginService {
	
	//final static Logger LOGGER = Logger.getLogger(LoginService.class);
	@Autowired
	UserRepository userRepository;
	
	
	@Autowired
	ModelTOEntityMapper  modelTOEntityMapper;
	public boolean doLogin(User user)
	{
		//LOGGER.debug("In method LoginService:doLogin Entry ");
		boolean userLogged=false;
		
		UserEntity u=modelTOEntityMapper.getUserEntity(user);
		UserEntity userEntity=userRepository.getAdminUser(u.getUserName(), u.getPassword());
		
		if(null!=userEntity)
		{
			userLogged=true;
		}else {
			userLogged=false;
		}
		userLogged=true;
		//productDAO.fetchProducts();
		//loginDAO.fetchProducts();
		//LOGGER.debug("In method LoginService:doLogin Exit ");
		return userLogged;
		
	}
	
	/*
	 * 
	 *   add  RCFKID BIGINT NOT NULL 
   GENERATED ALWAYS AS IDENTITY
   (START WITH 1              
   INCREMENT BY 1             
   CYCLE nocache)
	 * 
	 * 
	 * 
	 * 
	 * 
	 */

}

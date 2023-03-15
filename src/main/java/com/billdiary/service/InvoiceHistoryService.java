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
import com.billdiary.entities.InvoiceEntity;
import com.billdiary.model.Invoice;
import com.billdiary.repository.InvoiceRepository;

@Service
public class InvoiceHistoryService {
	
	final static Logger LOGGER = LoggerFactory.getLogger(InvoiceHistoryService.class); 

	@Autowired
	InvoiceRepository invoiceRepository;
	
	@Autowired
	EntityTOModelMapper entityTOModelMapper;
	
	public List<Invoice> getAllInvoices(){
		List<InvoiceEntity> invoiceEntityList = invoiceRepository.findAll();
		List<Invoice> invoiceList = entityTOModelMapper.getInvoiceFromInvoiceEntity(invoiceEntityList);
		return invoiceList;
	}
	
	public Long getInvoiceCount() {
		long size = invoiceRepository.count();
		return size;
	}

	public List<Invoice> getInvoices(int pages, int index, int rowsPerPage) {
		LOGGER.info("Entering in the getInvoices");
		List<Invoice> invoiceList=new ArrayList<>();
		try {
			Page<InvoiceEntity> invoiceEntities = invoiceRepository.findAll(PageRequest.of(index, rowsPerPage));
			invoiceEntities.forEach(invoiceEntity->{
				invoiceList.add(entityTOModelMapper.getInvoiceModel(invoiceEntity));
			});
		}catch(Exception e) {
			LOGGER.error(e.getMessage(), e);
			return null;
		}
		LOGGER.info("Exting from the getInvoices");
		return invoiceList;
	}
}

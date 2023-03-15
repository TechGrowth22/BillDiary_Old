package com.billdiary.serviceImpl;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.billdiary.service.CsvReaderService;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

@Service
public class CsvReaderServiceImpl implements CsvReaderService{

	@Override
	public <T> List<T> buildCsvToBean(Path path, ColumnPositionMappingStrategy<T> mappingStrategy)
			throws Exception {
		
		 List<T> list= new ArrayList<T>();
	     Reader reader = Files.newBufferedReader(path);
	     CsvToBean<T> cb = new CsvToBeanBuilder<T>(reader)
	       .withMappingStrategy(mappingStrategy)
	       .build();
	 
	    list = cb.parse();
	    reader.close();
	    return list;
		
	}

}

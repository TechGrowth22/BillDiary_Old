package com.billdiary.serviceImpl;

import java.io.FileWriter;
import java.io.Writer;
import java.nio.file.Path;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.billdiary.service.CsvWriterService;
import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;

/**
 * This class has been created to read and write the CSV file using bean
 * @author Gajanan Gaikwad
 *
 */
@Service
public class CsvWriterServiceImpl implements CsvWriterService {

	final static Logger LOGGER = LoggerFactory.getLogger(CsvWriterServiceImpl.class); 
	
	@Override
	public <T> String writeCsvFromBean(Path path, List<T> list, CustomMappingStrategy<T> mappingStrategy) throws Exception {
		LOGGER.info("Entering into the writeCsvFromBean");
		Writer writer  = new FileWriter(path.toString());
		
	    StatefulBeanToCsv<T> sbc = new StatefulBeanToCsvBuilder<T>(writer)
	       .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
	       .withMappingStrategy(mappingStrategy)
	       .build();
	 
	    sbc.write(list);
	    writer.close();
	    LOGGER.info("Exiting from the writeCsvFromBean");
	    return path.toString();
	}

}

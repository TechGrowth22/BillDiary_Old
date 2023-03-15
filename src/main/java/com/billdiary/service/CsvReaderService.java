package com.billdiary.service;

import java.nio.file.Path;
import java.util.List;

import com.billdiary.serviceImpl.CustomMappingStrategy;
import com.opencsv.bean.ColumnPositionMappingStrategy;

public interface CsvReaderService {

	public <T> List<T> buildCsvToBean(Path path, ColumnPositionMappingStrategy<T> mappingStrategy ) throws Exception;
}

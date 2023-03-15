package com.billdiary.service;

import java.nio.file.Path;
import java.util.List;

import com.billdiary.serviceImpl.CustomMappingStrategy;

public interface CsvWriterService {

	public <T> String writeCsvFromBean(Path path, List<T> list, CustomMappingStrategy<T> mappingStrategy ) throws Exception;
}

package com.billdiary.serviceImpl;

import com.opencsv.bean.ColumnPositionMappingStrategy;

/**
 * This class has been created to set a header to CSV file using ColumnPositionMappingStrategy
 * @author Gajanan Gaikwad
 *
 * @param <T>
 */
public class CustomMappingStrategy <T> extends ColumnPositionMappingStrategy<T> {
	private  String[] header = null;
	
	public String[] getHeader() {
		return header;
	}

	public void setHeader(String[] header) {
		this.header = header;
	}
	
	@Override
    public String[] generateHeader(T bean){
        return this.header;
    }

	 
}

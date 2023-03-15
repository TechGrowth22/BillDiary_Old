package com.billdiary.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;


@Service
public class GeneralUitilies {
	

	
	/**
	 * This method is used for getting the file path and return the file path from resources
	 * @param fileName
	 * @return file Path
	 */
	public  File getPDFTemplate()
	{
		InputStream inputStream = null;
		File pdfTemplate = null;
		try {
			final String fileName = URLS.PDF_TEMPLATE;
			pdfTemplate = new File(fileName);
			if (pdfTemplate.exists()) {
				inputStream = new FileInputStream(pdfTemplate);
			} else {
				final URL url = getClass().getResource("/files/template.xsl");
				final String filePath=url.getPath();
				pdfTemplate=new File(filePath);
				
			}
		}catch (FileNotFoundException fileNotFoundEx) {
			//LOGGER.error(fileNotFoundEx);
			//throw new VIBRuntimeException("Error code properties file not found.");
			System.out.println(fileNotFoundEx.getMessage());
			
		} 
		finally {
			try {
				if(inputStream !=null){
					inputStream.close();
				}
			} catch (IOException e) {
			//	LOGGER.error(e);
				System.out.println(e.getMessage());
			}
		}
		
		return pdfTemplate;
	}
	
	public InputStream getFileAsInputStream (String fileURL)
	{
		File file=null;
		InputStream inputStream = null;
		try {
			inputStream = getClass().getResourceAsStream(fileURL);
			
		}catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		return inputStream;
	}
	
	public File getFile (String fileURL)
	{
		File file=null;
		InputStream inputStream = null;
		try {
			URL url  = getClass().getResource("/images/deleteIcon.jpg");
			final String filePath=url.getPath();
			file=new File(filePath);
		}catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		return file;
	}
	
	
	
		
	
	

}

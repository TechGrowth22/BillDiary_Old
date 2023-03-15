package com.billdiary.serviceImpl;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.billdiary.service.PdfService;
import com.billdiary.utility.BillDiaryFileUtility;
import com.billdiary.utility.Constants;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;

@Service
public class PdfServiceImpl implements PdfService{

	final static Logger LOGGER = LoggerFactory.getLogger(PdfServiceImpl.class); 
	
	public String generatePdfFromBufferedImages(List<BufferedImage> images){
		StringBuffer message =new StringBuffer(Constants.BARCODE_GENERATION_FAILURE);
		File dest;
		try {
			dest = BillDiaryFileUtility.getEmptyPdfFileForBarcodes();
			Document document = new Document();
			PdfWriter.getInstance(document, new FileOutputStream(dest));
			document.open();
			for(BufferedImage image: images) {
				Image img = Image.getInstance(image, null);
				document.add(img);
			}			
			 
			document.close();
			message.setLength(0);
			message.append(Constants.SUCCESS);
			message.append("\nBarcode File: ");
			message.append(dest.getAbsolutePath());
			
			
		} catch (IOException e) {
			LOGGER.error(e.getMessage(),e);
		} catch (DocumentException e) {
			LOGGER.error(e.getMessage(),e);
		}       
	    
		return message.toString();
	}
	
	
}

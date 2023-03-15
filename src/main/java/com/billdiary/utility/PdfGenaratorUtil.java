package com.billdiary.utility;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;



@Component
public class PdfGenaratorUtil {
	final static Logger LOGGER = LoggerFactory.getLogger(PdfGenaratorUtil.class); 
	
	@Autowired
	private TemplateEngine templateEngine;
	public void createPdf(String templateName, Map map,String invoiceNo) throws Exception {
		LOGGER.info("In createPdf method");
		Assert.notNull(templateName, "The templateName can not be null");
		Context ctx = new Context();
		if (map != null) {
		     Iterator itMap = map.entrySet().iterator();
		       while (itMap.hasNext()) {
			  Map.Entry pair = (Map.Entry) itMap.next();
		          ctx.setVariable(pair.getKey().toString(), pair.getValue());
			}
		}
		
		String processedHtml = templateEngine.process(templateName, ctx);
		  FileOutputStream os = null;
		  String fileName = UUID.randomUUID().toString();
	        try {
	        	File dir = new File(System.getProperty("user.dir")+File.separator+"invoices" + File.separator + DateUtiliy.getDirectoryByDate(new Date()));
	        	if(!dir.exists()) {
	        		dir.mkdirs();
	        	}
	            final File outputFile = new File(dir.getAbsolutePath()+File.separator+ invoiceNo + ".pdf");
	            os = new FileOutputStream(outputFile);

	            ITextRenderer renderer = new ITextRenderer();
	            renderer.setDocumentFromString(processedHtml);
	            renderer.layout();
	            renderer.createPDF(os, false);
	            renderer.finishPDF();
	            System.out.println("PDF created successfully");
	        }catch(Exception e) {
	        	e.printStackTrace();
	        	System.out.println(e+" "+e.getMessage());
	        }
	        finally {
	            if (os != null) {
	                try {
	                    os.close();
	                } catch (IOException e) { 
	                	
	                }
	            }
	        }
	        LOGGER.info("Exiting createPdf method");
	}
}

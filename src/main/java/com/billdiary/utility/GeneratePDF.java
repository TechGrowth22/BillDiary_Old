package com.billdiary.utility;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;

import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.fop.apps.FOPException;
import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.billdiary.javafxUtility.Popup;
import com.billdiary.model.InvoiceTemplateA4;
import com.billdiary.model.Product;

import javafx.scene.control.Alert.AlertType;

@Component
public class GeneratePDF {
	
	private File directory=null;
	//final static Logger LOGGER = Logger.getLogger(GeneratePDF.class);
	private static Logger LOGGER = LoggerFactory.getLogger(GeneratePDF.class); 
	
	
	public File getDirectory() {
		return directory;
	}

	public void setDirectory(File directory) {
		this.directory = directory;
	}

	public void createUserFolder(final String directoryName) throws IOException {
		final File homeDir = new File(System.getProperty("user.home"));
		directory=new File(homeDir,directoryName);
		if (!directory.exists() && !directory.mkdirs()) {
	        throw new IOException("Unable to create " + directory.getAbsolutePath());
	    }
	}
	
	public File getInvoiceTemplate() {
		URL url = getClass().getResource("/files/InvoiceTemplate_A4.xsl");
		File xsltFile = new File(url.getPath());
		return xsltFile;
	}
	
	public File createPDF(String fileName) throws IOException {
		final File pdf=new File(directory,fileName);
		if(!pdf.exists())
			pdf.createNewFile();
		return pdf;
	}
	
	
	public File generateXML(InvoiceTemplateA4 template)
	{
		
		File input=null;
		try
		{
			input = new File(directory,URLS.INVOICE_XML);
			if(!input.exists())
				input.createNewFile();
			PrintWriter writer = new PrintWriter(input, "UTF-8");
			writer.println("<?xml version='1.0'?><Invoice><companyname>"+template.getCompanyName()+"</companyname>");
			writer.println("<Logo>"+/*template.getLogo().getImage()*/"image"+"</Logo>");
			writer.println("<NameOfClient>"+template.getCustomerName()+"</NameOfClient>");
			writer.println("<date>"+LocalDate.now()+"</date>");
			writer.println("<Address>"+template.getCustomerAddress()+"</Address>");
			writer.println("<phone>"+template.getMobileNo()+"</phone>");
			writer.println("<invoiceNO>"+template.getInvoiceNO()+"</invoiceNO>");
			List<Product> products=template.getProducts();
			if(null!=products) {
				int id=1;
				products.forEach(product->{	
					writer.println("<products><id>"+product.getProductCode()+"</id>");
					writer.println("<name>"+product.getName()+"</name>");
					writer.println("<MRP>"+String.format("%.2f", product.getMrpPrice())+"</MRP>");
					writer.println("<rate>"+String.format("%.2f",product.getValuePrice())+"</rate>");
					writer.println("<quantity>"+product.getQuantity()+"</quantity>");
					/*writer.println("<amtperquantity>"+product.getRetailPrice()+"</amtperquantity>");*/
					writer.println("<discount>"+product.getDiscount()+"</discount>");
					writer.println("<GSTRate>"+product.getSellPriceGSTPercentage()+"</GSTRate>");
					writer.println("<total>"+String.format("%.2f",product.getTotalPrice())+"</total>");
					writer.println("</products>");
					
				});
			}	
			writer.println("<total>"+template.getTotalAmount()+"</total>");
			String discount=(template.getDiscount()==null || template.getDiscount().isEmpty()) ?"0":template.getDiscount();
			writer.println("<discount>"+discount+"</discount>");
			writer.println("<Totalafterdiscount>"+template.getFinalAmount()+"</Totalafterdiscount>");
			writer.println("<totalPaidAmount>"+template.getPaidAmount()+"</totalPaidAmount>");
			writer.println("<totalAmountDue>"+template.getAmountDue()+"</totalAmountDue>");
			writer.println("</Invoice>");			
			writer.close();    
		} catch (IOException e) 
		{
		   e.printStackTrace();
		}	
		
		return input;
	}

	public void transformXSLToPDF(InvoiceTemplateA4 template) {
		
		LOGGER.info("in transform");
		OutputStream out=null;
		try {
			createUserFolder("BillDiaryPDF");
			File xsltFile = getInvoiceTemplate();
			StreamSource xmlSource = new StreamSource(generateXML(template));
			FopFactory fopFactory = FopFactory.newInstance(new File(".").toURI());
			FOUserAgent foUserAgent = fopFactory.newFOUserAgent();

			StringBuffer buff=new StringBuffer("bill_");
			buff.append(template.getInvoiceNO());
			buff.append(".pdf");
			
			
			out = new java.io.FileOutputStream(createPDF(buff.toString()));
			LOGGER.info("upto Out ");
			Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, foUserAgent, out);
			// Setup XSLT
			TransformerFactory factory = TransformerFactory.newInstance();
			//InputStream targetStream = new FileInputStream(xsltFile);

			Transformer transformer = factory.newTransformer(new StreamSource(getClass().getResourceAsStream("/files/InvoiceTemplate_A4.xsl")));
			LOGGER.info("upto transformer ");
			// FOP
			Result res = new SAXResult(fop.getDefaultHandler());
			LOGGER.info("upto Result ");
			
			LOGGER.info("xmlSource " +xmlSource);
			LOGGER.info("Res "+ res);
			LOGGER.info("xsltFile "+ xsltFile+ " path "+ xsltFile.getAbsolutePath());
			LOGGER.info("transformer "+ transformer);
			if(null!=transformer)
			transformer.transform(xmlSource, res);
			Popup.showAlert(Constants.INVOICE_TITLE,Constants.INVOICE_SUCCESSFULL_PDF_STATUS+directory.getPath()+"\\"+buff.toString(),AlertType.INFORMATION);
			LOGGER.info("upto transformer ");

		}
		catch (FileNotFoundException e) {
			System.out.println("Error in PDF Generation");
			LOGGER.info(e.getMessage());
			e.printStackTrace();
		} catch (FOPException e) {
			System.out.println("Error in PDF Generation");
			LOGGER.info(e.getMessage());
			e.printStackTrace();
		} catch (TransformerConfigurationException e) {
			System.out.println("Error in PDF Generation");
			LOGGER.info(e.getMessage());
			e.printStackTrace();
		} catch (TransformerException e) {
			LOGGER.info(e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
			LOGGER.info(e.getMessage());
		}
		catch (Exception e) {
			e.printStackTrace();
			LOGGER.error( "My custom message",e );
			LOGGER.info(e.getMessage() + " "+ e.getClass()+ " "+ e.getClass());
		}
		finally {
			try {
				if(out!=null)
					out.close();
			} catch (IOException e) {	
				LOGGER.info(e.getMessage());
				e.printStackTrace();
			}
			LOGGER.info("Exit transform");
		}
		
	}
}

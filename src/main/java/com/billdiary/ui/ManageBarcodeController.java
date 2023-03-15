package com.billdiary.ui;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.billdiary.entities.ProductEntity;
import com.billdiary.javafxUtility.Popup;
import com.billdiary.service.BarcodeService;
import com.billdiary.service.PdfService;
import com.billdiary.service.ProductService;
import com.billdiary.utility.Constants;
import com.billdiary.utility.DataTypeConverter;
import com.google.zxing.BarcodeFormat;
import com.lowagie.text.PageSize;
import com.lowagie.text.Rectangle;

import javafx.application.HostServices;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;


@Controller("ManageBarcodeController")
public class ManageBarcodeController implements Initializable{
	
	final static Logger LOGGER = LoggerFactory.getLogger(ManageBarcodeController.class);
	
	@Autowired
	BarcodeService barcodeServiceImpl;
	
	@Autowired
	ProductService productService;
	
	@Autowired
	PdfService pdfServiceImpl;
	
	@FXML TextField barcodeImageHeightField;
	@FXML TextField barcodeImageWidthField;
	@FXML TextField manfacturerCodeField;
	@FXML TextField productIdField;
	
	@FXML ComboBox<String> countryDropdown;
	Map<String,String> countries = new HashMap<String,String>();
	
	@FXML ComboBox<String> generateBracodeForDropdown;
	List<String> generateBracodeForList = new ArrayList<String>();
	
	@FXML ComboBox<String> pageSizeListDropdown;
	List<String> pageSizeList = new ArrayList<String>();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		reset();
		
		generateBracodeForDropdown.valueProperty().addListener(new ChangeListener<String>() {
	        @Override public void changed(ObservableValue ov, String t, String t1) {
	        	setVisibilityOfProductIdField();
	          }    
	      });
	}
	
	public void reset() {
		getAvailableCountries();
		getGenerateBracodeForList();
		setDefaultManfacturerCode(Constants.DEFAULT_MANFACTURER_CODE);
		setBarcodeImageHeightField(String.valueOf(Constants.DEFAULT_BARCODE_HEIGHT));
		setBarcodeImageWidthField(String.valueOf(Constants.DEFAULT_BARCODE_WIDTH));
		getAvailablePageSizeList();
		setVisibilityOfProductIdField();
	}
	
	public void setVisibilityOfProductIdField(){
		if("For Single Product".equals(generateBracodeForDropdown.getValue())){
			productIdField.setVisible(true);
		}else {
			productIdField.setVisible(false);
		}
		//productIdField.managedProperty().bind(productIdField.visibleProperty());
	}
	
	public void setBarcodeImageHeightField(String height) {
		barcodeImageHeightField.setText(height);
	}
	
	public void setBarcodeImageWidthField(String width) {
		barcodeImageWidthField.setText(width);
	}
	
	public void setDefaultManfacturerCode(String manufacturerCode) {
		manfacturerCodeField.setText(manufacturerCode);
	}
	
	public void  getAvailablePageSizeList() {
		pageSizeList.clear();
		pageSizeList.add("A4");
		pageSizeListDropdown.getItems().clear();
		if(pageSizeListDropdown.getItems().isEmpty()) {
			for(String item:pageSizeList) {
				pageSizeListDropdown.getItems().add(item);
			}
		}
	}
	
	public void getGenerateBracodeForList() {
		generateBracodeForList.clear();
		generateBracodeForList.add("For All Products");
		generateBracodeForList.add("For Single Product");
		generateBracodeForDropdown.getItems().clear();
		if(generateBracodeForDropdown.getItems().isEmpty()) {
			for(String item:generateBracodeForList) {
				generateBracodeForDropdown.getItems().add(item);
			}
		}
	}
	
	public void getAvailableCountries() {
		countries.clear();
		countries.put("India", "890");
		countryDropdown.getItems().clear();
		if(countryDropdown.getItems().isEmpty()) {
			for(Map.Entry<String, String> country:countries.entrySet()) {
				countryDropdown.getItems().add(country.getKey());
			}
		}
	}
	
	public void generateBarcode() throws IOException {
		if(validateInput()) {
			List<ProductEntity> entities = productService.getAllProducts();
			Map<String, String> barcodesMetadata = new ConcurrentHashMap<String, String>();
			for(ProductEntity entity : entities) {
				String barcode = productService.getBarcodeFromProduct(entity);
				String metadata = getMetadata(barcode, entity.getName(), String.valueOf(entity.getSell_price()).concat(" Rs"));
				barcodesMetadata.put(barcode, metadata);
			}
			
			List<BufferedImage> pageImageList = getBarcodesImageFile(barcodesMetadata,"A4");
			int k=0;
			for( BufferedImage image:pageImageList) {
				java.io.File imageFile = new java.io.File("C:/Gajanan/" + "output_"+k+".png");
				javax.imageio.ImageIO.write(image, "PNG", imageFile);
				k++;
			}
			
			String message = pdfServiceImpl.generatePdfFromBufferedImages(pageImageList);
			Popup.showAlert("Barcode Generation", message, message.startsWith(Constants.SUCCESS)?AlertType.INFORMATION:AlertType.ERROR);
			
//			Path partPath = Paths.get("C:\\Gajanan\\git");
//			Desktop desktop =Desktop.getDesktop();
//            desktop.browse(partPath.toUri());
		}
		
	}
	
	private boolean validateInput() {
		if(null == countryDropdown.getValue() || Constants.EMPTY_STRING.equals(countryDropdown.getValue())) {
			Popup.showErrorAlert("Input Invalid", "please select a Country", AlertType.ERROR);
			return false;
		}else if(null == manfacturerCodeField.getText() || Constants.EMPTY_STRING.equals(manfacturerCodeField.getText()) || manfacturerCodeField.getText().length() != 5) {
			Popup.showErrorAlert("Input Invalid", "please enter a correct Manfacturer code", AlertType.ERROR);
			return false;
		}else if((null == barcodeImageHeightField.getText() || Constants.EMPTY_STRING.equals(barcodeImageHeightField.getText())) && validateGreaterThanZero(barcodeImageHeightField.getText()) ) {
			Popup.showErrorAlert("Input Invalid", "please enter a Barcode Height", AlertType.ERROR);
			return false;
		}else if(null == barcodeImageWidthField.getText() || Constants.EMPTY_STRING.equals(barcodeImageWidthField.getText())) {
			Popup.showErrorAlert("Input Invalid", "please enter a Barcode Width", AlertType.ERROR);
			return false;
		}else if(null == pageSizeListDropdown.getValue() || Constants.EMPTY_STRING.equals(pageSizeListDropdown.getValue())) {
			Popup.showErrorAlert("Input Invalid", "please select a page size", AlertType.ERROR);
			return false;
		}else if(null == generateBracodeForDropdown.getValue() || Constants.EMPTY_STRING.equals(generateBracodeForDropdown.getValue())) {
			Popup.showErrorAlert("Input Invalid", "please select generate barcode for", AlertType.ERROR);
			return false;
		}
		return true;
	}

	private boolean validateGreaterThanZero(String text) {
		try {
			Double value = Double.parseDouble(text);
			if(value <= 0)
				return false;
		}catch(Exception e) {
			LOGGER.error(e.getMessage(),e);
			return false;
		}
		return true;
	}

	public List<BufferedImage> getBarcodesImageFile(Map<String, String> barcodesMetadata, String pageSize) {
		List<BufferedImage> barcodeList = new ArrayList<BufferedImage>();
		List<BufferedImage> pageImageList = new ArrayList<BufferedImage>();
		int  barcodePrinted = 0;
		
		
		for(Map.Entry<String, String> entry : barcodesMetadata.entrySet()) {
			barcodeList.add(createBarcodeTotalImage(entry.getKey(),entry.getValue()));
		}
		
		
		while(barcodePrinted < barcodeList.size()) {
			
				BufferedImage pageImage = getBufferedImagePageSize(pageSize);
				Graphics2D g2D = pageImage.createGraphics();
				g2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
				g2D.setColor(java.awt.Color.WHITE);
				
				int currentX = 0;
				int currentY = 0;
				Rectangle rec = getRectangle(pageSize);
				for(Map.Entry<String, String> entry : barcodesMetadata.entrySet()) {
					BufferedImage barcodeImage = createBarcodeTotalImage(entry.getKey(),entry.getValue());
					if(currentX >= rec.getWidth() || (currentX + barcodeImage.getWidth()) >= rec.getWidth()  ) {
						currentX = 0;
						currentY = currentY + barcodeImage.getHeight() + Constants.theta;
					}
					
					if(currentY >= rec.getHeight() || (currentY + barcodeImage.getWidth()) >= rec.getHeight()) {
						LOGGER.debug("You can print only this much barcodes on single page");
						break;
					}
					g2D.drawImage(barcodeImage, currentX, currentY, null);
					currentX = currentX + barcodeImage.getWidth() + Constants.theta;
					++barcodePrinted;
					barcodesMetadata.remove(entry.getKey());
				}
				
				g2D.dispose();
				pageImageList.add(pageImage);
			}
		
		
		LOGGER.info("Total Product {} and Total Barcode printed {}", barcodeList.size(),barcodePrinted);
			
		return pageImageList;
	}
	
	private String getMetadata(String barcode, String productName, String productPrice) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(barcode);
		buffer.append("\n");
		buffer.append(productName);
		buffer.append("\n");
		buffer.append(productPrice);		
		return buffer.toString();
	}

	public BufferedImage createBarcodeTotalImage(String barcode, String metadata) {
		
		BufferedImage totalBarcodeImage = null;
		try {
			
			
			BufferedImage barcodeImage = getBarcodeImage(barcode, BarcodeFormat.EAN_13, DataTypeConverter.getIntegerFromString(barcodeImageWidthField.getText()),  DataTypeConverter.getIntegerFromString(barcodeImageHeightField.getText()));

			BufferedImage barcodeStringImage = getBarcodeMetadataImage(metadata);
			
			int width = barcodeImage.getWidth() < barcodeStringImage.getWidth() ? barcodeStringImage.getWidth() : barcodeImage.getWidth();
			int height = barcodeImage.getHeight() + barcodeStringImage.getHeight();
			
			width = width + Constants.theta;
			height = height + Constants.theta;
			
			
			totalBarcodeImage = new BufferedImage(width,height,java.awt.image.BufferedImage.TYPE_INT_ARGB);
			Graphics2D g2D = totalBarcodeImage.createGraphics();
			
			g2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
			
			// Set the canvas color
			g2D.setColor(java.awt.Color.WHITE);
			// Draw the second image (logo image) on the canvas inside the barcode image
			g2D.drawImage(barcodeImage, 0, 0, null);
			// Draw the primary image (barcode image) on the canvas
			g2D.drawImage(barcodeStringImage, 0, barcodeImage.getHeight(), null);
			
			g2D.dispose();
			// Save the final barcode image
			//java.io.File imageFile = new java.io.File("C:/Gajanan/" + "output.png");
			//javax.imageio.ImageIO.write(output, "PNG", imageFile);
			
		} catch (Exception e) {
			LOGGER.error(e.getMessage(),e);
		}
		return totalBarcodeImage;
	}
	
	
	
	

	
	private Rectangle getRectangle(String pageSize) {
		if(pageSize.equals("A4")) {
			return PageSize.A4;
		}
		return null;
	}

	public BufferedImage getBufferedImagePageSize(String page) {
		BufferedImage output = null;
		if(page.equals("A4")) {
			output = getPageImage(Math.round(PageSize.A4.getWidth()),Math.round(PageSize.A4.getHeight()));
		}else if(page.equals("A5")) {
			output = getPageImage(Math.round(PageSize.A5.getWidth()),Math.round(PageSize.A5.getHeight()));
		}
		return output;
	}
	
	public BufferedImage getPageImage(int width, int height) {
		BufferedImage pageImage = new BufferedImage(width,height,java.awt.image.BufferedImage.TYPE_INT_ARGB);
		return pageImage;
	}
	
	public BufferedImage getBarcodeImage(String barcode, BarcodeFormat format, int height, int width) throws Exception {
		if(height <= 0)
			height = Constants.DEFAULT_BARCODE_HEIGHT;
		if(width <= 0)
			width = Constants.DEFAULT_BARCODE_WIDTH;
		BufferedImage barcodeImage = barcodeServiceImpl.generateEAN13BarcodeImage(barcode, format, width,height);
		return barcodeImage;
	}
	
	
	public BufferedImage getBarcodeMetadataImage(String text) {
		BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = img.createGraphics();
        Font font = new Font("Arial", Font.PLAIN, 12);
        g2d.setFont(font);
        FontMetrics fm = g2d.getFontMetrics();
        String[] outputs = text.split("\n");
        int width = fm.stringWidth(outputs[0]);
        int height = 4*fm.getHeight();
        g2d.dispose();
		
        
        img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        g2d = img.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
        g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
        g2d.setFont(font);
        fm = g2d.getFontMetrics();
        g2d.setColor(Color.BLACK);

        FontRenderContext frc = g2d.getFontRenderContext();
       
        TextLayout layout = new TextLayout(text, font, frc);
        g2d.drawString(outputs[0], 2,(int) (15+0*layout.getBounds().getHeight()+5));
        for(int i=1; i<outputs.length; i++){
            g2d.drawString(outputs[i], 2,(int) (15+i*layout.getBounds().getHeight()+5));
        }
        g2d.dispose();
        return img;
	}
	
}
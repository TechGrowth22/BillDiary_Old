package com.billdiary.serviceImpl;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Hashtable;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.billdiary.service.BarcodeService;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.GlobalHistogramBinarizer;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.oned.Code128Writer;
import com.google.zxing.oned.EAN13Reader;
import com.google.zxing.oned.EAN13Writer;
import com.google.zxing.oned.EAN8Writer;
import com.google.zxing.oned.UPCAWriter;
import com.google.zxing.oned.UPCEWriter;
import com.google.zxing.qrcode.QRCodeWriter;

/**
 * This class has been used to generate barcode either 1D/2D i.e EAN/ QR Code
 * @author ggaikwad
 *
 */
@Service
public class BarcodeServiceImpl implements BarcodeService{
	
	final static Logger LOGGER = LoggerFactory.getLogger(BarcodeServiceImpl.class);
	
	public BufferedImage generateEAN13BarcodeImage(String barcodeText, BarcodeFormat format, int width, int height) throws Exception {
		if(BarcodeFormat.EAN_13.equals(format)) {
			EAN13Writer barcodeWriter = new EAN13Writer();
		    BitMatrix bitMatrix = barcodeWriter.encode(barcodeText, BarcodeFormat.EAN_13, width, height);
		    return MatrixToImageWriter.toBufferedImage(bitMatrix);
		}else if(BarcodeFormat.EAN_8.equals(format)){
			EAN8Writer barcodeWriter = new EAN8Writer();
			BitMatrix bitMatrix = barcodeWriter.encode(barcodeText, BarcodeFormat.EAN_8, width, height);
		    return MatrixToImageWriter.toBufferedImage(bitMatrix);
		}else if(BarcodeFormat.UPC_A.equals(format)){
			UPCAWriter barcodeWriter = new UPCAWriter();
			BitMatrix bitMatrix = barcodeWriter.encode(barcodeText, BarcodeFormat.UPC_A, width, height);
		    return MatrixToImageWriter.toBufferedImage(bitMatrix);
		}else if(BarcodeFormat.UPC_E.equals(format)) {
			UPCEWriter  barcodeWriter = new UPCEWriter();
			BitMatrix bitMatrix = barcodeWriter.encode(barcodeText, BarcodeFormat.UPC_E, width, height);
		    return MatrixToImageWriter.toBufferedImage(bitMatrix);
		}else if(BarcodeFormat.CODE_128.equals(format)) {
			Code128Writer  barcodeWriter = new Code128Writer();
			BitMatrix bitMatrix = barcodeWriter.encode(barcodeText, BarcodeFormat.CODE_128, width, height);
		    return MatrixToImageWriter.toBufferedImage(bitMatrix);
		}
		
	   return null;
	}
	
	public String decodeBarcodeImage(File file) {
		BufferedImage image = null;
		Result result = null;
		try {
			image = ImageIO.read(file);
			if (image == null) {
				System.out.println("the decode image may be not exit.");
			}
			LuminanceSource source = new BufferedImageLuminanceSource(image);
			BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
			
			BinaryBitmap bitmap1 = new BinaryBitmap(new GlobalHistogramBinarizer(source));
			
			//EAN13Reader reader =new EAN13Reader();
			
			MultiFormatReader reader = new MultiFormatReader();
            Hashtable hints = new Hashtable();
            hints.put(DecodeHintType.TRY_HARDER, true);
            reader.setHints(hints);
			
			result = reader.decode(bitmap);
			if(null!= result && null != result.getText())
				return result.getText();
			else {
				result = reader.decode(bitmap1);
				if(null!= result && null != result.getText())
					return result.getText();
			}
		//	result = new MultiFormatReader().decode(bitmap, null);
			return result.getText();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(),e);
		}
		return null;
	}
	
	public BufferedImage generateQRCodeImage(String barcodeText, int width, int height) throws Exception {
	    QRCodeWriter barcodeWriter = new QRCodeWriter();
	    BitMatrix bitMatrix = barcodeWriter.encode(barcodeText, BarcodeFormat.QR_CODE, 200, 200);
	    return MatrixToImageWriter.toBufferedImage(bitMatrix);
	}
}

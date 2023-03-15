package com.billdiary.service;

import java.awt.image.BufferedImage;
import java.io.File;

import com.google.zxing.BarcodeFormat;
/**
 * UPC-A = 12 (only 0-9)
 * UPC-E = 8 (only 0-9)
 * EAN13 = 13 (890(Country code) + XXXXX (manufacter's code) +remaining product codes)
 * 
 * @author ggaikwad
 *
 */
public interface BarcodeService {

	public BufferedImage generateEAN13BarcodeImage(String barcodeText, BarcodeFormat format, int width, int height) throws Exception;
	
	public BufferedImage generateQRCodeImage(String barcodeText, int width, int height) throws Exception;
	
	public String decodeBarcodeImage(File file);
}



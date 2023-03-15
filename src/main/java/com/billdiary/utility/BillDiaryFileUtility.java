package com.billdiary.utility;

import java.io.File;
import java.io.IOException;
import java.util.Date;

public class BillDiaryFileUtility {
	
	public static File baseDir = new File(System.getProperty("user.dir"));

	public static File getEmptyPdfFileForBarcodes() throws IOException {
		if(!baseDir.exists()) {
			baseDir.mkdirs();
		}
		File barcodesDir = new File(baseDir,"Barcodes"+File.separator+DateUtiliy.getDirectoryByDate(new Date()));
		if(!barcodesDir.exists()) {
			barcodesDir.mkdirs();
		}
		File barcodeFile = new File(barcodesDir,"Barcodes_"+DateUtiliy.getBarcodeFileNameByDateAndTime(new Date()));
		if(!barcodeFile.exists()) {
			barcodeFile.createNewFile();
		}
		return barcodeFile;
	}

}

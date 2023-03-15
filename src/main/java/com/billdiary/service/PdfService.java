package com.billdiary.service;

import java.awt.image.BufferedImage;
import java.util.List;

public interface PdfService {

	public String generatePdfFromBufferedImages(List<BufferedImage> images);
}

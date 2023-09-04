package com.bluex.manifiesto.pdf.service;

import java.io.IOException;

import org.springframework.ui.Model;

import com.lowagie.text.DocumentException;

public interface PdfService {
  public abstract String generatePdfContent(Model model);
  void convertHtmlToPdf(String htmlContent, String base64Image, String outputPath) throws IOException, DocumentException;
}

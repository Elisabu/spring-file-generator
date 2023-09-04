package com.bluex.manifiesto.pdf.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bluex.manifiesto.pdf.service.PdfService;

import org.springframework.ui.Model;

@RestController
@RequestMapping("/pdf")
public class PdfController {

  @Autowired
  private PdfService pdfService;

  @RequestMapping(value = "/create", method = RequestMethod.GET)
  public String createPdf(Model model) {
    return pdfService.generatePdfContent(model);
  }
}

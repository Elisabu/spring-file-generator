package com.bluex.manifiesto.pdf.service.impl;

import org.jsoup.Jsoup;
import org.jsoup.helper.W3CDom;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.xhtmlrenderer.layout.SharedContext;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.bluex.manifiesto.pdf.models.Manifiesto.Manifiesto;
import com.bluex.manifiesto.pdf.service.PdfService;
import com.bluex.manifiesto.pdf.service.utils.ServiceConstants;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lowagie.text.DocumentException;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;

@Service
public class PdfServiceImpl implements PdfService {

  @Autowired
  private TemplateEngine templateEngine;

  @Autowired
  private ResourceLoader resourceLoader;
  // private static final Logger logger =
  // LoggerFactory.getLogger(TransbankEquipoController.class);

  @Override
  public String generatePdfContent(Model model) {
    ObjectMapper objectMapper = new ObjectMapper();
    try {
      // Resource resource = new ClassPathResource("classpath:manifiesto.json");
      Resource resource = resourceLoader.getResource("classpath:static/manifiesto-medium.json");
      InputStream inputStream = resource.getInputStream();
      InputStreamReader jsonFile = new InputStreamReader(inputStream, StandardCharsets.UTF_8);

      Manifiesto manifest = objectMapper.readValue(jsonFile, Manifiesto.class);

      System.out.println("Document ID: " + manifest.getDocument_id());
      System.out.println("Os list: " + manifest.getOs_list()[0]);
      System.out.println("Os list: " + manifest.getOs_list()[0].getOs_id());

      fillTemplate(model, manifest);

      jsonFile.close();
      inputStream.close();
    } catch (IOException e) {
      e.printStackTrace();
    }

    Context context = new Context();
    context.setVariables(model.asMap());
    context.setVariable("image", ServiceConstants.TEMPLATE_BASE64_IMAGE);
    String htmlContent = templateEngine.process("index", context);

    String filePath = "file.html";

    try {
      // Convert the HTML content to bytes
      byte[] contentBytes = htmlContent.getBytes();

      // Create the file and write the content
      Path path = Paths.get(filePath);
      Files.write(path, contentBytes);

      System.out.println("HTML file has been written successfully.");
    } catch (IOException e) {
      e.printStackTrace();
    }

    try {
      String pdfPath = "file.pdf";
      String imageBase64 = ServiceConstants.TEMPLATE_BASE64_IMAGE;
      convertHtmlToPdf(htmlContent, imageBase64, pdfPath);
    } catch (Exception e) {
      e.printStackTrace();
    }

    return "PDF content";
  };

  @Override
  public void fillTemplate(Model model, Manifiesto manifest) {
    model.addAttribute("document_id", manifest.getDocument_id());
    model.addAttribute("retiro_id", manifest.getRetiro_id());
    model.addAttribute("courier", manifest.getCourier());
    model.addAttribute("carga", manifest.getCarga());
    model.addAttribute("client", manifest.getClient());
    model.addAttribute("os_list", manifest.getOs_list());
  }

  public void convertHtmlToPdf(String htmlContent, String base64Image, String outputPath)
      throws IOException, DocumentException {
    try (FileOutputStream fileOutputStream = new FileOutputStream(outputPath)) {
      Document document = Jsoup.parse(htmlContent);

      org.w3c.dom.Document doc = new W3CDom().fromJsoup(document);

      PdfRendererBuilder builder = new PdfRendererBuilder();
      builder.useFastMode();
      builder.withW3cDocument(doc, "");
      builder.toStream(fileOutputStream);
      builder.run();

    }
  }
}

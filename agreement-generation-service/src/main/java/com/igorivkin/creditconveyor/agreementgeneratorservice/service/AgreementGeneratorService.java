package com.igorivkin.creditconveyor.agreementgeneratorservice.service;

import com.igorivkin.creditconveyor.agreementgeneratorservice.AgreementGeneratorServiceApplication;
import com.igorivkin.creditconveyor.agreementgeneratorservice.model.CreditRequest;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.StandardCharsets;

@Service
public class AgreementGeneratorService {

    /**
     * Генерирует первичный договор в формате PDF по одобренной заявке.
     *
     * @param request одобренная заявка на кредит
     * @return файловый путь к сгенерированному PDF-документу
     */
    public String generateAgreement(CreditRequest request) throws IOException, DocumentException {
        final String pdfFileName = "agreements/" + request.getUuid() + ".pdf";
        final Document document = new Document();
        final PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(pdfFileName));
        document.open();
        try {
            final XMLWorkerHelper worker = XMLWorkerHelper.getInstance();
            final InputStream htmlInputStream = enrichAgreementTemplateFromRequest(request);
            worker.parseXHtml(writer, document, htmlInputStream, StandardCharsets.UTF_8);
            return pdfFileName;
        } finally {
            document.close();
        }
    }

    private InputStream enrichAgreementTemplateFromRequest(CreditRequest request) throws IOException {
        final InputStream htmlInputStream =
                AgreementGeneratorServiceApplication.class
                        .getClassLoader()
                        .getResourceAsStream("agreements/templates/basic.html");
        if (htmlInputStream != null) {
            String htmlContent = new String(htmlInputStream.readAllBytes(), StandardCharsets.UTF_8);
            htmlContent = htmlContent
                    .replace("${uuid}", request.getUuid().toString())
                    .replace("${username}", request.getName())
                    .replace("${approvedAmount}", String.valueOf(request.getApprovedAmount()))
                    .replace("${months}", String.valueOf(request.getMonths()));
            return new ByteArrayInputStream(htmlContent.getBytes());
        } else {
            return null;
        }
    }
}

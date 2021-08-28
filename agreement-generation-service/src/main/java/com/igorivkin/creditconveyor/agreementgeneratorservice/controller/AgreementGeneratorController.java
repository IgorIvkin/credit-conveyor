package com.igorivkin.creditconveyor.agreementgeneratorservice.controller;

import com.igorivkin.creditconveyor.agreementgeneratorservice.model.CreditRequest;
import com.igorivkin.creditconveyor.agreementgeneratorservice.model.GeneratorResult;
import com.igorivkin.creditconveyor.agreementgeneratorservice.service.AgreementGeneratorService;
import com.itextpdf.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class AgreementGeneratorController {

    private final AgreementGeneratorService agreementGeneratorService;

    @Autowired
    public AgreementGeneratorController(AgreementGeneratorService agreementGeneratorService) {
        this.agreementGeneratorService = agreementGeneratorService;
    }

    @PostMapping("/generate")
    public GeneratorResult generateAgreement(@RequestBody CreditRequest request)
            throws DocumentException, IOException {
        return GeneratorResult.builder()
                .uuid(request.getUuid())
                .filename(agreementGeneratorService.generateAgreement(request))
                .build();
    }
}

package com.igorivkin.creditconveyor.requestfilterservice.client;

import com.igorivkin.creditconveyor.requestfilterservice.model.rabbit.CreditRequest;
import com.igorivkin.creditconveyor.requestfilterservice.model.web.AgreementGeneratorResponse;
import com.igorivkin.creditconveyor.requestfilterservice.model.web.CreditRequestForAgreementGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
public class AgreementGeneratorClient {
    private final RestTemplate restTemplate;

    @Autowired
    public AgreementGeneratorClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Value("${services.agreement-generator.host}")
    private String agreementGeneratorServiceHost;

    public AgreementGeneratorResponse generateAgreement(CreditRequest request) {
        log.info("Request to generate agreement PDF-file: {}", request);
        return restTemplate.postForObject(
                agreementGeneratorServiceHost + "/generate",
                CreditRequestForAgreementGenerator.builder()
                        .uuid(request.getUuid())
                        .name(request.getName())
                        .approvedAmount(request.getRequestedAmount())
                        .months(request.getMonths())
                        .build(),
                AgreementGeneratorResponse.class);
    }
}

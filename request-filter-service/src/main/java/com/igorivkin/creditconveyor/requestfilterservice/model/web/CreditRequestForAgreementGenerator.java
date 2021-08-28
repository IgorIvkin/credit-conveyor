package com.igorivkin.creditconveyor.requestfilterservice.model.web;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreditRequestForAgreementGenerator {
    private UUID uuid;

    private String name;

    private Long approvedAmount;

    private Integer months;
}

package com.igorivkin.creditconveyor.agreementgeneratorservice.model;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreditRequest {
    private UUID uuid;

    private String name;

    private Long approvedAmount;

    private Integer months;
}

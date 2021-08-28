package com.igorivkin.creditconveyor.agreementgeneratorservice.model;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GeneratorResult {
    private UUID uuid;

    private String filename;
}

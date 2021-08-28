package com.igorivkin.creditconveyor.requestfilterservice.model.web;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AgreementGeneratorResponse {
    private UUID uuid;

    private String filename;

    @Override
    public String toString() {
        return "AgreementGeneratorResponse{" +
                "uuid=" + uuid +
                ", filename='" + filename + '\'' +
                '}';
    }
}

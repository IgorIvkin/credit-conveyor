package com.igorivkin.creditbackendforfrontend.model;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreditResponse {
    private UUID uuid;
}

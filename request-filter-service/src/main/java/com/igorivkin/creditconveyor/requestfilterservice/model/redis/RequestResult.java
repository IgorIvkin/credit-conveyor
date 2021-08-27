package com.igorivkin.creditconveyor.requestfilterservice.model.redis;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.UUID;

@RedisHash(value = "request-results")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestResult implements Serializable {
    @Id
    private UUID uuid;
    private RequestResultType result;
}

package com.igorivkin.creditbackendforfrontend.controller;

import com.igorivkin.creditbackendforfrontend.model.CreditRequest;
import com.igorivkin.creditbackendforfrontend.model.CreditResponse;
import com.igorivkin.creditbackendforfrontend.service.QueueSenderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class CreditConveyorController {
    private final QueueSenderService queueSenderService;

    public CreditConveyorController(QueueSenderService queueSenderService) {
        this.queueSenderService = queueSenderService;
    }

    @PostMapping("/requests")
    public ResponseEntity<CreditResponse> publishNewRequest(@RequestBody CreditRequest request) {
        UUID uuid = UUID.randomUUID();
        request.setUuid(uuid);
        queueSenderService.sendMessage(request);
        return ResponseEntity.ok(CreditResponse.builder()
                .uuid(uuid)
                .build());
    }
}

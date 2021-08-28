package com.igorivkin.creditconveyor.requestfilterservice.service;

import com.igorivkin.creditconveyor.requestfilterservice.model.rabbit.CreditRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.igorivkin.creditconveyor.requestfilterservice.config.RabbitMQConfig.NEW_REQUESTS_QUEUE;

@Slf4j
@Service
public class InitialQueueListenerService {

    private final RequestProcessingService requestProcessingService;

    @Autowired
    public InitialQueueListenerService(RequestProcessingService requestProcessingService) {
        this.requestProcessingService = requestProcessingService;
    }

    @RabbitListener(queues = NEW_REQUESTS_QUEUE)
    public void processCreditRequests(CreditRequest request) {
        log.info("New credit request incoming: {}", request);
        requestProcessingService.processRequest(request);
    }
}

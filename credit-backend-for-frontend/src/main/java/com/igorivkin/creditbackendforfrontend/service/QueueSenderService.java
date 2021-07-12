package com.igorivkin.creditbackendforfrontend.service;

import com.igorivkin.creditbackendforfrontend.config.RabbitMQConfig;
import com.igorivkin.creditbackendforfrontend.model.CreditRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class QueueSenderService {
    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public QueueSenderService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage(CreditRequest creditRequest) {
        log.info("New credit request: {}", creditRequest);
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.CREDIT_CONVEYOR_EXCHANGE,
                RabbitMQConfig.ROUTING_KEY,
                creditRequest
        );
    }
}

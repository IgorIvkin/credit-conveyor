package com.igorivkin.creditconveyor.requestfilterservice.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    public static final String CREDIT_CONVEYOR_EXCHANGE = "credit-conveyor";
    public static final String NEW_REQUESTS_QUEUE = "new-requests";
    public static final String ROUTING_KEY = "new-request";

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(CREDIT_CONVEYOR_EXCHANGE);
    }

    @Bean
    public Queue newRequestsQueue() {
        return new Queue(NEW_REQUESTS_QUEUE);
    }

    @Bean
    public Binding newRequestsBinding() {
        return BindingBuilder.bind(newRequestsQueue())
                .to(topicExchange())
                .with(ROUTING_KEY);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(producerJackson2MessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}


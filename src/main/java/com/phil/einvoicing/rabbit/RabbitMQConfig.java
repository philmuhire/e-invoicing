package com.phil.einvoicing.rabbit;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String INVOICE_QUEUE = "invoice.queue";

    @Bean
    public Queue invoiceQueue() {
        return new Queue(INVOICE_QUEUE, true, false, false);
    }
}


package com.phil.einvoicing.rabbit;

import com.phil.einvoicing.entity.Invoice;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQSender {

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public RabbitMQSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendInvoiceCreatedMessage(Invoice invoice) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.INVOICE_QUEUE, invoice);
    }
}


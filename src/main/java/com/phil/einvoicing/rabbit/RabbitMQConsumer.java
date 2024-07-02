package com.phil.einvoicing.rabbit;

import com.phil.einvoicing.entity.Invoice;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQConsumer {

    @RabbitListener(queues = RabbitMQConfig.INVOICE_QUEUE)
    public void handleInvoiceCreated(Invoice invoice) {
        System.out.println("Received Invoice: " + invoice);
        // Log the received invoice or perform any other necessary action
    }
}


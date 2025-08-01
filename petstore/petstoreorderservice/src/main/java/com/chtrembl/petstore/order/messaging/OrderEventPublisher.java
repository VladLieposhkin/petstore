package com.chtrembl.petstore.order.messaging;

import com.chtrembl.petstore.order.model.Order;
import com.azure.messaging.servicebus.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OrderEventPublisher {

    private final ServiceBusSenderClient senderClient;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public OrderEventPublisher(@Value("${azure.servicebus.connection-string}") String connectionString) {
        this.senderClient = new ServiceBusClientBuilder()
                .connectionString(connectionString)
                .sender()
                .queueName("order-queue")
                .buildClient();
    }

    public void publish(Order order) {
        try {
            String message = objectMapper.writeValueAsString(order);
            senderClient.sendMessage(new ServiceBusMessage(message));
            log.info("Published order update to Service Bus for session: {}", order.getId());
        } catch (Exception e) {
            log.error("Failed to send order to Service Bus", e);
        }
    }
}

package com.chtrembl.petstore.orderitemsreserver;

import com.chtrembl.petstore.orderitemsreserver.service.BlobUploader;
import com.microsoft.azure.functions.annotation.*;
import com.microsoft.azure.functions.*;

public class OrderItemsReserverFunction {

    @FunctionName("OrderItemsReserver")
    public void run(
            @ServiceBusQueueTrigger(
                    name = "message",
                    queueName = "order-queue",
                    connection = "AzureServiceBusConnection"
            ) String message,
            final ExecutionContext context) {

        context.getLogger().info("Received message: " + message);

        try {
            BlobUploader uploader = new BlobUploader();
            uploader.uploadWithRetry(message, context);
        } catch (Exception e) {
            context.getLogger().severe("Error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}

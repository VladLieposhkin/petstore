package com.chtrembl.petstore.orderitemsreserver.service;

import com.azure.storage.blob.*;
import com.azure.storage.blob.specialized.BlockBlobClient;
import com.azure.core.util.BinaryData;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.azure.functions.ExecutionContext;

public class BlobUploader {

    private static final String CONTAINER_NAME = "orders";
    private static final String CONNECTION_STRING = System.getenv("AzureWebJobsStorage");

    public void uploadWithRetry(String json, ExecutionContext context) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(json);
        String sessionId = root.get("id").asText();
        String fileName = sessionId + ".json";

        BlobServiceClient blobServiceClient = new BlobServiceClientBuilder()
                .connectionString(CONNECTION_STRING)
                .buildClient();

        BlobContainerClient containerClient = blobServiceClient.getBlobContainerClient(CONTAINER_NAME);
        if (!containerClient.exists()) {
            containerClient.create();
        }

        BlockBlobClient blobClient = containerClient.getBlobClient(fileName).getBlockBlobClient();

        int attempts = 0;
        while (attempts < 3) {
            try {
                blobClient.upload(BinaryData.fromString(json), true);
                context.getLogger().info("Uploaded file to blob: " + fileName);
                return;
            } catch (Exception e) {
                attempts++;
                context.getLogger().warning("Attempt " + attempts + " failed: " + e.getMessage());
            }
        }

        throw new RuntimeException("Upload failed after 3 attempts.");
    }
}

//package com.rovo.azureblob_filehandling.config;
//import com.azure.storage.blob.BlobClientBuilder;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//
//@Configuration
//public class AzureBlobConfig {
//
//    @Value("${azure.storage.account-key}")
//    private String connectionKey;
//    @Value("${azure.storage.account-name}")
//    private String accountName;
//    @Value("${azure.storage.container.name}")
//    private String containerName;
//
//    @Bean
//    public BlobClientBuilder getClient() {
//        BlobClientBuilder client = new BlobClientBuilder();
//        client.connectionString(connectionKey);
//        client.containerName(containerName);
//        return client;
//    }
//}
//
//

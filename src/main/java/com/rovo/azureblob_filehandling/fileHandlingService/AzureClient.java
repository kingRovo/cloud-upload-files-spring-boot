//package com.rovo.azureblob_filehandling.fileHandlingService;
//
//import com.azure.storage.blob.BlobClientBuilder;
//
//import org.springframework.beans.factory.annotation.Qualifier;
//
//import org.springframework.stereotype.Component;
//
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//
//
//
//
//@Component
//@Qualifier("AzureClient")
//public class AzureClient implements FileHandlingService{
//
//
//    private final BlobClientBuilder blobClientBuilder;
//
//    public AzureClient(BlobClientBuilder blobClientBuilder) {
//        this.blobClientBuilder = blobClientBuilder;
//    }
//
//    public String upload(MultipartFile file) {
//        if(file != null && file.getSize() > 0) {
//            try {
//                String fileName = file.getOriginalFilename();
//                blobClientBuilder.blobName(fileName).buildClient().upload(file.getInputStream(),file.getSize());
//                return fileName;
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        return "";
//    }
//
//
//
//}
//
//

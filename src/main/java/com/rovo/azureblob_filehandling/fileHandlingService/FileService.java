package com.rovo.azureblob_filehandling.fileHandlingService;

import com.rovo.azureblob_filehandling.model.ImageFile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileService {

    @Autowired
    @Qualifier("AWSClient")
    private FileHandlingService awsclient;


    /**
     * call the method to upload file based on client(AWS,Azure...) name
     * @param file
     * @param clientName
     * @param targetSize
     * @return
     */
    public List<ImageFile> UploadFile(MultipartFile file, String clientName,int targetSize){


        List<ImageFile> imageFiles =new ArrayList<>();

        if (clientName.equals("AWS")) {
            imageFiles.add(awsclient.upload(file));
            imageFiles.add(awsclient.reduceSize(file,targetSize));

        }

        return imageFiles;

    }



}

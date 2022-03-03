package com.rovo.azureblob_filehandling.fileHandlingService;


import com.rovo.azureblob_filehandling.model.ImageFile;
import org.springframework.web.multipart.MultipartFile;

public interface FileHandlingService {

    ImageFile upload(MultipartFile multipartFile);
    ImageFile reduceSize(MultipartFile multipartFile, int targetSize);

}


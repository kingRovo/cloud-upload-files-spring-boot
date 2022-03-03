package com.rovo.azureblob_filehandling.fileController;

import com.rovo.azureblob_filehandling.fileHandlingService.FileService;


import com.rovo.azureblob_filehandling.model.ImageFile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RestController
@RequestMapping("api/v1/files")
@Slf4j
public class FileController {

    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    /**
     * this method accept file and size and pass to service.
     * @param imgData
     * @param cloudType
     * @param targetSize
     * @return ResponseEntity
     */

    @PostMapping("/")
    public  ResponseEntity<List<ImageFile>>  uploadImg(@RequestParam(value = "file") MultipartFile imgData, @RequestParam("cloud") String cloudType, @RequestParam("targetSize") int targetSize){

        try {

            return new ResponseEntity<>(fileService.UploadFile(imgData, cloudType, targetSize), HttpStatus.OK);

        }
        catch (Exception exception){

            log.error("Invalid request..");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}

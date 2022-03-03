package com.rovo.azureblob_filehandling.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor
public class ImageFile {
    @NonNull
    private String fileSize;
    @NonNull
    private String fileUrl;

}

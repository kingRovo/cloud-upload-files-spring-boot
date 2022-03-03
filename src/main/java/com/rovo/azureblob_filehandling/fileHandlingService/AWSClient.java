package com.rovo.azureblob_filehandling.fileHandlingService;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.rovo.azureblob_filehandling.model.ImageFile;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.io.FileUtils;
import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;


import java.awt.image.BufferedImage;
import java.io.*;
import java.text.DecimalFormat;
import java.util.Date;


@Qualifier("AWSClient")
@Component
@Slf4j
public class AWSClient implements FileHandlingService {

    private AmazonS3 amazonS3;

    public AWSClient(AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
    }

    @Value("${amazonProperties.endpointUrl}")
    private String endPointUrl;
    @Value("${amazonProperties.bucketName}")
    private String bucketName;


    /**
     * uploading original  file to AWS and return ImageFile object(Size, Url).
     * @param multipartFile
     * @return ImageFile
     */

    @Override
    public ImageFile upload(MultipartFile multipartFile) {

        String fileUrl = "";
        String fileSize=" ";
        try {
            File file = convertMultiPartToFile(multipartFile);
            String fileName = generateFileName(multipartFile);
            fileUrl = endPointUrl + "/" + bucketName + "/" + fileName;

            fileSize = getFileSize(FileUtils.sizeOf(file));

            uploadFileToBucket(fileName, file);

            file.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ImageFile(fileSize,fileUrl);
    }

    /**
     * Returning file size in human readable format.
     * @param size
     * @return string
     */
    public static String getFileSize(long size) {

        DecimalFormat df = new DecimalFormat("0.00");

        float sizeKb = 1024.0f;
        float sizeMb = sizeKb * sizeKb;
        float sizeGb = sizeMb * sizeKb;
        float sizeTerra = sizeGb * sizeKb;


        if(size < sizeMb)
            return df.format(size / sizeKb)+ " Kb";
        else if(size < sizeGb)
            return df.format(size / sizeMb) + " Mb";
        else if(size < sizeTerra)
            return df.format(size / sizeGb) + " Gb";

        return "";
    }

    /**
     * this method uploading local file to AWS S3 bucket.
     * @param fileName
     * @param file
     */

    private void uploadFileToBucket(String fileName, File file ) {

        amazonS3.putObject(new PutObjectRequest(bucketName, fileName, file)
                .withCannedAcl(CannedAccessControlList.PublicRead));

    }

    /**
     * this method converting MultiPartFile to file
     * @param file
     * @return
     * @throws IOException
     */
    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }

    /**
     * this method generating unique file name.
     * @param multiPart
     * @return string
     */
    private String generateFileName(MultipartFile multiPart) {
        return new Date().getTime() + "-" + multiPart.getOriginalFilename().replace(" ", "_");
    }


    /**
     * this method reducing file size as target size and return ImageFile object(size,url).
     * @param multipartFile
     * @param targetSize
     * @return ImageFile
     */
    public ImageFile reduceSize(MultipartFile multipartFile,int targetSize){


        String fileUrl = "";
        String fileSize ="";

        try {

            BufferedImage thumbImg = null;
            BufferedImage img = ImageIO.read(multipartFile.getInputStream());
            thumbImg = Scalr.resize(img, Scalr.Method.AUTOMATIC,  targetSize, Scalr.OP_ANTIALIAS);

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ImageIO.write(thumbImg, "jpg", out);

            byte[] imageByte = out.toByteArray();

            MultipartFile imagefile = new ConvertToMultipartFile(imageByte, targetSize+"_"+multipartFile.getOriginalFilename(), multipartFile.getOriginalFilename(), "jpg", imageByte.length);

            File  newfile = convertMultiPartToFile(imagefile);

            String fileName = generateFileName(imagefile);
            fileUrl = endPointUrl + "/" + bucketName + "/" + fileName;

            fileSize = getFileSize(FileUtils.sizeOf(newfile));

            uploadFileToBucket(fileName, newfile);
            newfile.delete();
        }

        catch (Exception e){
            e.printStackTrace();
        }

        return new ImageFile(fileSize,fileUrl);


    }





}

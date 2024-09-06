package com.houseclay.zebra.CloudOperations;


import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.houseclay.zebra.model.rental.Images;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;



@Builder
public class CloudOperations {


    private AmazonS3 s3Client;
    private String bucketName;

    public CloudOperations(AmazonS3 s3Client, String bucketName) {
        this.s3Client = s3Client;
        this.bucketName = bucketName;
    }


    public List<String> uploadImagesToFolder(List<Images> images, String folderName){
        List<String> imagesUrls = new ArrayList<>();
        // have to handle exceptions(use try catch block)
        for(Images image : images){
            String fileName=System.currentTimeMillis()+"_"+UUID.randomUUID()+"_"+image.getName();
            File file=new File(fileName);
            try(OutputStream os=new FileOutputStream(file)){
                os.write(image.getImage_data());
            }
            catch(Exception e){
                e.printStackTrace();
            }

            s3Client.putObject(bucketName+"/"+folderName, fileName, file);
            String imageUrl = "https://" + bucketName + ".s3.amazonaws.com/" + folderName + "/" + fileName;

            imagesUrls.add(imageUrl);
            image.setImageNameInCloud(fileName);
            file.delete();
        }
        return imagesUrls;

    }


    public void deleteImagesFromFolder(String fileName, String folderName){
        s3Client.deleteObject(bucketName+"/"+folderName, fileName);
        System.out.println("Deleting file with key: " + fileName);

    }



    public  String createFolder(String folderName) {
        s3Client.putObject(bucketName, folderName + "/", new ByteArrayInputStream(new byte[0]), new ObjectMetadata());
        String folderUrl = "https://" + bucketName + ".s3.amazonaws.com/" + folderName + "/";
        return folderUrl;

    }

    public void deleteAllImages(String folderName, List<Images>oldImages){
        for(Images images : oldImages) {
            deleteImagesFromFolder(images.getImageNameInCloud(), folderName);
            System.out.println("folder name : "+folderName);
            System.out.println("image name in cloud : "+images.getImageNameInCloud());
        }
    }

    public void deleteFolder(String folderName) {
        if (s3Client.doesBucketExist(bucketName)) {
            ListObjectsRequest listObjectsRequest = new ListObjectsRequest()
                    .withBucketName(bucketName)
                    .withPrefix(folderName);

            ObjectListing objectListing = s3Client.listObjects(listObjectsRequest);

            while (true) {
                for (S3ObjectSummary objectSummary : objectListing.getObjectSummaries()) {
                    s3Client.deleteObject(bucketName, objectSummary.getKey());
                }
                if (objectListing.isTruncated()) {
                    objectListing = s3Client.listNextBatchOfObjects(objectListing);
                } else {
                    break;
                }
            }
        }
        System.out.println("folder name is being deleted : "+folderName);

    }
}

package com.fypp.Ethics_Management_System.Submission;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
@Service
public class AWSFileStorageService {
    private final AmazonS3 s3Client;
    private final String bucketName = "easmsfyp";

    @Autowired
    public AWSFileStorageService(AmazonS3 s3Client) {
        this.s3Client = s3Client;
    }

    public String storeFile(MultipartFile file) throws IOException {
        // Generate the unique key for the file in the S3 bucket
        String keyName = System.currentTimeMillis() + "-" + file.getOriginalFilename();

        // Create metadata for the file
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        metadata.setContentType(file.getContentType());

        // Upload the file to S3 with the set metadata
        s3Client.putObject(new PutObjectRequest(bucketName, keyName, file.getInputStream(), metadata));

        // Construct the file URL
        // Note to self : This URL generation is for a public bucket.
        // remember to  use S3's SDK to generate a pre-signed URL after changing the bucket to private
        String fileUrl = "https://" + bucketName + ".s3." + Regions.DEFAULT_REGION.getName() + ".amazonaws.com/" + keyName;

        return fileUrl;
    }
}

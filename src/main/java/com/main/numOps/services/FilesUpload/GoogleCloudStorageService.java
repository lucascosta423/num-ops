package com.main.numOps.services.FilesUpload;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.storage.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
public class GoogleCloudStorageService {

    private final Storage storage;

    @Value("${gcp.bucket-name}")
    private String bucketName;

    @Value("${gcp.credentials.location}")
    private InputStream credentials;

    public GoogleCloudStorageService() throws IOException {
        InputStream credStream = credentials;
        assert credStream != null;
        storage = StorageOptions.newBuilder()
                .setCredentials(ServiceAccountCredentials.fromStream(credStream))
                .build()
                .getService();
    }

    public String uploadFile(MultipartFile file) throws IOException {
        String filename = UUID.randomUUID().toString().substring(0, 9) + file.getOriginalFilename();
        
        String objectName = "faturas/" + filename;

        BlobId blobId = BlobId.of(bucketName, objectName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType(file.getContentType()).build();

        storage.create(blobInfo, file.getBytes());

        return String.format("https://storage.googleapis.com/%s/%s", bucketName, objectName);
    }
}

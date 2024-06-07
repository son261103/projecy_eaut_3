package com.example.admingiadien.Service;

import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
public class FilebaseService {

    private static final String BUCKET_NAME = "eaut-c8d1f.appspot.com";

    @Autowired
    private Storage storage;

    public String uploadFile(MultipartFile file) throws IOException {
        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        BlobId blobId = BlobId.of(BUCKET_NAME, "products/" + fileName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
                .setContentType(file.getContentType())
                .build();

        try (InputStream inputStream = new ByteArrayInputStream(file.getBytes())) {
            storage.create(blobInfo, inputStream);
        }

        // Trả về đường dẫn chuẩn từ Firebase Storage
        return "https://firebasestorage.googleapis.com/v0/b/" + BUCKET_NAME + "/o/products%2F" + fileName + "?alt=media";
    }
}

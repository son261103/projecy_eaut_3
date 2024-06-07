package com.example.admingiadien.Config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;
import java.io.IOException;

@Configuration
public class FilebaseConfig {

    @Bean
    public Storage firebaseStorage() throws IOException {
        GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream("src/main/resources/firebase-config.json"));
        return StorageOptions.newBuilder().setCredentials(credentials).build().getService();
    }
}

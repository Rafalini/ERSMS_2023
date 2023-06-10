package com.ersms.app.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;
import java.io.IOException;

@Configuration
public class CloudStorageConfig {

    @Value("classpath:silken-forest-388009-f6bb8da3a731.json")
    private String credentialsPath;

    @Bean
    public Storage storage() throws IOException {
        // Load the Google Cloud credentials
        FileInputStream credentialsStream = new FileInputStream(credentialsPath);
        GoogleCredentials credentials = GoogleCredentials.fromStream(credentialsStream);

        // Build the Google Cloud Storage client
        StorageOptions options = StorageOptions.newBuilder().setCredentials(credentials).build();
        return options.getService();
    }
}


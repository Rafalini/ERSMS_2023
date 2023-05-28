package com.ersms.app.service;

import com.google.api.gax.paging.Page;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StorageService {

    private final Storage storage;

    @Autowired
    public StorageService(Storage storage) {
        this.storage = storage;
    }

    public List<String> listAllImages() {
        List<String> urls = new ArrayList<>();

        String bucketName = "wiadro-na-dane";
        Bucket bucket = storage.get(bucketName);
        Page<Blob> blobs = bucket.list();

        for (Blob blob : blobs.iterateAll()) {
            String url = "https://storage.googleapis.com/" + bucketName + "/" + blob.getName();
            urls.add(url);
        }

        return urls;
    }
}

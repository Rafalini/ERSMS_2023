package com.ersms.app.service;
import com.google.api.gax.paging.Page;
import com.google.cloud.storage.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class StorageService {
    private final String bucketName = "wiadro-na-dane";
    @Autowired
    private Storage storage;

    public List<String> listAllImages() {
        List<String> urls = new ArrayList<>();

        Bucket bucket = storage.get(this.bucketName);
        Page<Blob> blobs = bucket.list();

        for (Blob blob : blobs.iterateAll()) {
            String url = "https://storage.googleapis.com/" + this.bucketName + "/" + blob.getName();
            urls.add(url);
        }

        return urls;
    }
}

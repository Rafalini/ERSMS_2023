package com.ersms.app;

import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@RestController
@RequestMapping("/gcp")
public class GCPStorage {
    private final Storage storage;

    @Autowired
    public GCPStorage(Storage storage) {
        this.storage = storage;
    }

    @GetMapping("/send-data")
    public String sendData() throws IOException {
        BlobId id = BlobId.of("wiadro-na-dane", "santa.jpg");
        BlobInfo info = BlobInfo.newBuilder(id).build();
        File file = new File("santa.jpg");
        byte[] arr = Files.readAllBytes(Paths.get(file.toURI()));
        storage.create(info, arr);
        return "Success!";
    }
}

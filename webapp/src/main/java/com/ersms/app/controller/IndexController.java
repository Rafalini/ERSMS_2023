package com.ersms.app.controller;

import com.ersms.app.service.StorageService;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.model.IModel;

import java.io.IOException;

@Controller
public class IndexController {
    @Autowired
    private Storage storage;
    @Autowired
    private StorageService storageService;

    @GetMapping(value = "/login")
    public String login() {
        return "login";
    }

    @GetMapping(value = "/index")
    public String index(Model model) {
        model.addAttribute("imageUrls", storageService.listAllImages());
        return "index";
    }

    @GetMapping(value = "/upload")
    public String upload() {
        return "upload";
    }

    @PostMapping(value = "/upload")
    public String upload(@RequestParam("file") MultipartFile file){
        try {
            if (!file.isEmpty()) {
                String filename = file.getOriginalFilename();
                BlobInfo blobInfo = storage.create(
                        BlobInfo.newBuilder("wiadro-na-dane", filename).build(),
                        file.getBytes()
                );
                System.out.println("File uploaded to: " + blobInfo.getMediaLink());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "redirect:/index";
    }

}

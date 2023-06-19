package com.ersms.app.controller;

import com.ersms.app.data.Photo;
import com.ersms.app.service.StorageService;
import com.ersms.app.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.HttpHeaders;
import org.thymeleaf.model.IModel;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Controller
public class IndexController {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private Storage storage;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private StorageService storageService;
    @Autowired
    private UserService userService;

    @GetMapping(value = "/{username}/myAccount")
    @PreAuthorize("@userService.isUserEmailMatchesUsername(#username)")
    public String myAccount(@PathVariable String username, Model model) {
        String email = userService.getEmailAddresOfLoggedInUser();
        model.addAttribute("email", email);
        model.addAttribute(username);
        return "myAccount";
    }

    @GetMapping(value = {"/", "/index"})
    public String index(Model model) throws JsonProcessingException {
        String email = userService.getEmailAddresOfLoggedInUser();

        String url = "http://localhost:8083/api/v1/images";
        String result = restTemplate.getForObject(url, String.class);
        List<Photo> photos = objectMapper.readValue(result, new TypeReference<>() {});
        Collections.reverse(photos);

        model.addAttribute("photos", photos);
        model.addAttribute("email", email);
        return "index";
    }

    @GetMapping(value = "/upload")
    public String upload(Model model) {
        String email = userService.getEmailAddresOfLoggedInUser();
        model.addAttribute("email", email);
        return "upload";
    }

    @PostMapping(value = "/upload")
    public String upload(@ModelAttribute Photo photo, Model model) {
        String email = userService.getEmailAddresOfLoggedInUser();
        model.addAttribute("email", email);
        try {
            if (!photo.getFile().isEmpty()) {
                String filename = photo.getFile().getOriginalFilename();
                assert filename != null;
                BlobInfo blobInfo = storage.create(
                        BlobInfo.newBuilder("wiadro-na-dane", filename).build(),
                        photo.getFile().getBytes()
                );
                String mediaLink = blobInfo.getMediaLink();
                System.out.println("File uploaded to: " + mediaLink);
                String url = "http://localhost:8083/api/v1/images";
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                String requestBody = """
                         {
                            "url": "%s",
                            "name": "%s",
                            "description": "%s"
                         }
                        """.formatted(mediaLink, photo.getName(), photo.getDescription());
                HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
                restTemplate.exchange(url, HttpMethod.POST, requestEntity, Void.class);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "redirect:/index";
    }

}

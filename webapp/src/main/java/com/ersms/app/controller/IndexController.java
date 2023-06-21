package com.ersms.app.controller;

import com.ersms.app.data.Metadata;
import com.ersms.app.data.Photo;
import com.ersms.app.service.StorageService;
import com.ersms.app.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import jakarta.servlet.http.HttpServletRequest;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
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
    public String myAccount(@PathVariable String username, Model model) throws JsonProcessingException {
        String email = userService.getEmailAddresOfLoggedInUser();
        model.addAttribute("email", email);
        model.addAttribute(username);

        String url = "http://localhost:8083/api/v1/users/" + username + "/images";
        String result = "";
        try {
            result = restTemplate.getForObject(url, String.class);
            List<Photo> photos = objectMapper.readValue(result, new TypeReference<>() {});
            Collections.reverse(photos);

            model.addAttribute("photos", photos);
        } catch (Exception ignored){
        }

        model.addAttribute("email", email);
        model.addAttribute("myAccount", username);

        return "index";
    }

    @GetMapping(value = { "/account/{name}", "/account/{name}/{email}" })
    public String myAccount(@PathVariable(required = false) String name, @PathVariable(required = false) String email, HttpServletRequest request) {
        return "myAccount";
    }

    @GetMapping(value = "/photos/{userEmail}")
    public String userPhotos(@PathVariable String userEmail, Model model) throws JsonProcessingException {
        String email = userService.getEmailAddresOfLoggedInUser();
        String url = "http://localhost:8083/api/v1/users/" + userEmail + "/images";
//        String url = "http://localhost:8083/api/v1/images";

        String result = restTemplate.getForObject(url, String.class);
        List<Photo> photos = objectMapper.readValue(result, new TypeReference<>() {});
        Collections.reverse(photos);

        model.addAttribute("photos", photos);
        model.addAttribute("email", email);
        model.addAttribute("searchedByMail", userEmail);

        return "index";
    }

    @PostMapping("/search")
    public String handleForm(@RequestParam String tags) {
        return "redirect:/search/" + tags;
    }
    @GetMapping(value = "/search/{tags}")
    public String search(@PathVariable String tags, Model model) throws JsonProcessingException {
        String email = userService.getEmailAddresOfLoggedInUser();
        String url = "http://localhost:8083/api/v1/images/tags?tags=" + tags;
        String result = restTemplate.getForObject(url, String.class);
        List<Photo> photos = objectMapper.readValue(result, new TypeReference<>() {});
        Collections.reverse(photos);

        model.addAttribute("photos", photos);
        model.addAttribute("email", email);
        model.addAttribute("tags", tags);
        return "index";
    }

    // Read all images
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

    // Read one image
    @GetMapping(value = "/index/{imageId}")
    public String getPhoto(@PathVariable("imageId") Long imageId, Model model) throws JsonProcessingException {
        String url = "http://localhost:8083/api/v1/images/" + imageId;
        String result = restTemplate.getForObject(url, String.class);
        Photo photo = objectMapper.readValue(result, new TypeReference<>() {});
        model.addAttribute("photo", photo);
        return "index";
    }

    // Create an image
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
                            "description": "%s",
                            "userEmail": "%s"
                         }
                        """.formatted(mediaLink, photo.getName(), photo.getDescription(), email);
                HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
                restTemplate.exchange(url, HttpMethod.POST, requestEntity, Void.class);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "redirect:/index";
    }

    //Create image's metadata
    @PostMapping(value = "/upload/{imageId}/metadata")
    public String createMetadata(@PathVariable("imageId") Long imageId, @ModelAttribute Metadata metadata) {
        String url = "http://localhost:8083/api/v1/images/" + imageId + "/metadata";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String requestBody = String.format("""
        {
            "cameraModel": "%s",
            "aperture": "%f",
            "pixelsVertically": "%d",
            "pixelsHorizontally": "%d",
            "focalLength": "%f",
            "shutterSpeed": "%d",
            "location": "%s",
            "dateTime": "%s"
        }
        """,
                metadata.getCameraModel(),
                metadata.getAperture(),
                metadata.getPixelsVertically(),
                metadata.getPixelsHorizontally(),
                metadata.getFocalLength(),
                metadata.getShutterSpeed(),
                metadata.getLocation(),
                metadata.getDateTime()
        );

        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
        restTemplate.exchange(url, HttpMethod.POST, requestEntity, Void.class);

        return "redirect:/index";
    }

    //Update an image
    @PostMapping(value = "/update/{imageId}")
    public String updateImage(@PathVariable("imageId") Long imageId, @ModelAttribute Photo photo) {
        try {
            String url = "http://localhost:8083/api/v1/images/" + imageId;
            String result = restTemplate.getForObject(url, String.class);
            Photo oldPhoto = objectMapper.readValue(result, new TypeReference<>() {});
            String email = oldPhoto.getUserEmail();
            if(!userService.isUserEmailMatchesUsername(email) && !userService.isAdmin())
                return "redirect:/index"; // to do unauthorized page

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            String requestBody = """
            {
                "name": "%s",
                "description": "%s"
            }
            """.formatted(photo.getName(), photo.getDescription());
            HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
            restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Void.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/index";
    }

    @GetMapping(value = "/update/{imageId}")
    public String updateImageView(@PathVariable("imageId") Long imageId, Model model) throws JsonProcessingException {
        String url = "http://localhost:8083/api/v1/images/" + imageId;
        String result = restTemplate.getForObject(url, String.class);
        Photo photo = objectMapper.readValue(result, new TypeReference<>() {});
        model.addAttribute("photo", photo);
        return "update";
    }

    //Update image metadata
    @PutMapping(value = "/update/{imageId}/metadata")
    public String updateImageMetadata(@PathVariable("imageId") Long imageId, @ModelAttribute Metadata metadata) {
        try {
            String url = "http://localhost:8083/api/v1/images/" + imageId + "/metadata";
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            String requestBody = """
            {
                "cameraModel": "%s",
                "aperture": "%f",
                "pixelsVertically": "%d",
                "pixelsHorizontally": "%d",
                "focalLength": "%f",
                "shutterSpeed": "%d",
                "location": "%s",
                "dateTime": "%s"
            }
            """.formatted(
                    metadata.getCameraModel(),
                    metadata.getAperture(),
                    metadata.getPixelsVertically(),
                    metadata.getPixelsHorizontally(),
                    metadata.getFocalLength(),
                    metadata.getShutterSpeed(),
                    metadata.getLocation(),
                    metadata.getDateTime()
            );

            HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
            restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Void.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/index";
    }

    //Delete an image and its metadata
    @GetMapping(value = "/delete/{imageId}")
    public String delete(@PathVariable("imageId") Long imageId) {

        String url = "http://localhost:8083/api/v1/images/" + imageId;
        String result = restTemplate.getForObject(url, String.class);
        Photo photo = null;
        try {
            photo = objectMapper.readValue(result, new TypeReference<>() {});
            String email = photo.getUserEmail();
            if (!userService.isUserEmailMatchesUsername(email) && !userService.isAdmin())
                return "redirect:/index"; // to do unauthorize page
        } catch (Exception e) {
            e.printStackTrace();
        }

        restTemplate.exchange(url, HttpMethod.DELETE, null, Void.class);
        return "redirect:/index";
    }

}

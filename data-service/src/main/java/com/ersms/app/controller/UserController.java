package com.ersms.app.controller;

import com.ersms.app.model.response.ImageDto;
import com.ersms.app.model.response.UserDto;
import com.ersms.app.service.ImageService;
import com.ersms.app.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final ImageService imageService;
    private final UserService userService;


    //Get user's role
    @GetMapping("/{userMail}/role")
    public String getUserRole(@PathVariable String userMail) {
        return userService.getUserRole(userMail);
    }

    //Get user's details
    @GetMapping("/{userMail}/details")
    public UserDto getUserDetails(@PathVariable String userMail) {
        return userService.getUser(userMail);
    }

    //Read user's images
    @GetMapping("/{userMail}/images")
    public List<ImageDto> getUserImages(@PathVariable String userMail) {
        return imageService.getUserImages(userMail);
    }
}

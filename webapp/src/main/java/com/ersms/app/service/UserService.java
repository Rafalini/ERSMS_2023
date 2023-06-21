package com.ersms.app.service;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Service
public class UserService {
    public String getEmailAddresOfLoggedInUser(){
        String email = "";
        try {
            Authentication authentication2 = SecurityContextHolder.getContext().getAuthentication();
            OAuth2User oauth2User = (OAuth2User) authentication2.getPrincipal();
            email = oauth2User.getAttribute("email");
        } catch (Exception e ){
            email = "anon";
        }
        return email;
    }
    public boolean isUserEmailMatchesUsername(String username) {
        String email = "";
        try {
            Authentication authentication2 = SecurityContextHolder.getContext().getAuthentication();
            OAuth2User oauth2User = (OAuth2User) authentication2.getPrincipal();
            email = oauth2User.getAttribute("email");
        } catch (Exception e ){
            email = "";
        }
        return Objects.equals(email, username);
    }

    public boolean isAdmin() {
        String username = getEmailAddresOfLoggedInUser();
        String roleUrl = "http://localhost:8083/api/v1/users/" + username + "/role";

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(roleUrl, String.class);
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            String role = responseEntity.getBody();
            return "ADMIN".equals(role);
        }
        return Objects.equals("ADMIN", username);
    }
}


package com.ersms.app.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

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
}


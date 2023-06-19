package com.ersms.app.authentication;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, org.springframework.security.core.Authentication authentication) throws IOException, ServletException {

        Authentication authentication2 = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication2.isAuthenticated()) {
            Object principal = authentication2.getPrincipal();

            if (principal instanceof OAuth2User) {
                OAuth2User oauth2User = (OAuth2User) principal;
                String name = oauth2User.getAttribute("given_name");
                String email = oauth2User.getAttribute("email");
                redirectStrategy.sendRedirect(request, response, "http://localhost:8081/" + email + "/myAccount");
                return;
            }
        }

        redirectStrategy.sendRedirect(request, response, "http://localhost:8081/myAccount");
    }
}


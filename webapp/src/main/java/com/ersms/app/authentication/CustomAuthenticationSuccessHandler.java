package com.ersms.app.authentication;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
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

//        Authentication authentication2 = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication != null && authentication2.isAuthenticated()) {
//            Object principal = authentication2.getPrincipal();
//            if (principal instanceof OAuth2User) {
//                OAuth2User oauth2User = (OAuth2User) principal;
//                String name = oauth2User.getAttribute("given_name");
//                String email = oauth2User.getAttribute("email");
//                String token = ((DefaultOidcUser) oauth2User).getIdToken().getTokenValue();
//                Cookie authCookie = new Cookie("Authorization", token);
//                authCookie.setHttpOnly(true); // Bezpieczeństwo: ciasteczko nie jest dostępne dla skryptów JS
//                authCookie.setSecure(true); // Bezpieczeństwo: ciasteczko jest wysyłane tylko przez HTTPS
//                authCookie.setPath("/"); // Ciasteczko jest dostępne dla całej strony
//                authCookie.setMaxAge(24 * 60 * 60); // Ustalenie czasu wygaśnięcia ciasteczka na 24 godziny
//                response.addCookie(authCookie);
                redirectStrategy.sendRedirect(request, response, "http://localhost:8081/index");
//            }
//        }
    }
}


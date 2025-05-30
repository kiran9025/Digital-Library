package org.digitalLibrary.Controller;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class LibraryController {

    @GetMapping("/")
    public String welcomeMessage(OAuth2AuthenticationToken authentication, HttpServletRequest request) {
        Map<String, Object> attributes = authentication.getPrincipal().getAttributes();
        System.out.println("GitHub attributes: " + attributes);

        String username = attributes.get("name") != null
                ? (String) attributes.get("name")
                : (String) attributes.get("login");

        return "Hi " + username + "  Welcome to DigitalLibrary — Session ID: " + request.getSession().getId();
    }
}

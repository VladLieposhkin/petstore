package com.chtrembl.petstoreapp.controller;

import com.chtrembl.petstoreapp.model.ContainerEnvironment;
import com.chtrembl.petstoreapp.model.User;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.reflect.Field;
import java.net.URISyntaxException;
import java.util.List;

/**
 * Controller for home page and authentication-related routes.
 * Handles landing pages, login, and general navigation.
 */
@Controller
@Slf4j
public class HomeController extends BaseController {

    private static final String VIEW_HOME = "home";
    private static final String VIEW_LOGIN = "login";

    @Autowired
    private ClientRegistrationRepository clientRegistrationRepository;

    @PostConstruct
    public void logRegistrations() {
        if (clientRegistrationRepository instanceof Iterable) {
            System.out.println("=== Registered OAuth2 Clients ===");
            for (ClientRegistration registration : (Iterable<ClientRegistration>) clientRegistrationRepository) {
                System.out.println("registrationId = " + registration.getRegistrationId());
                System.out.println("redirectUri    = " + registration.getRedirectUri());
                System.out.println("----------------------------------");
            }
        } else {
            System.out.println("ClientRegistrationRepository is not iterable.");
        }
    }


    public HomeController(ContainerEnvironment containerEnvironment,
                          User sessionUser,
                          CacheManager currentUsersCacheManager) {
        super(containerEnvironment, sessionUser, currentUsersCacheManager);
    }

    /**
     * Landing page - main entry point of the application.
     * Supports multiple URL patterns for flexibility.
     */
    @GetMapping(value = {"/", "/home.htm*", "/index.htm*"})
    public String landing(Model model, OAuth2AuthenticationToken token, HttpServletRequest request)
            throws URISyntaxException {

        log.debug("PetStoreApp landing page requested by user: {}", sessionUser.getName());

        trackPageView(request, "landing");

        return VIEW_HOME;
    }

    /**
     * Login page - authentication entry point.
     * Displays login options and authentication status.
     */
    @GetMapping(value = "/login")
    public String login(Model model, HttpServletRequest request) throws URISyntaxException {

        log.debug("PetStoreApp /login requested, routing to login view...");

        trackPageView(request, "login");

        return VIEW_LOGIN;
    }

    /**
     * Test endpoint for error handling.
     * Used for testing error pages and exception handling.
     */
    @GetMapping("/fail")
    public String fail() {
        log.warn("Test 500 error endpoint accessed");
        throw new RuntimeException("Test 500 error");
    }

    @GetMapping("/secure")
    @ResponseBody
    public String secure(@AuthenticationPrincipal OAuth2User user) {
        return "Hello, " + user.getAttribute("name");
    }
}
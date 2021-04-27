package com.example.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.MultiValueMapAdapter;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final Logger logger = LoggerFactory.getLogger(AuthController.class);
    private final String GITHUB_ACCESS_TOKEN_URL = "https://github.com/login/oauth/access_token";

    private final Environment environment;

    public AuthController(Environment environment) {
        this.environment = environment;
    }

    @GetMapping
    public GithubAccessTokenResponse auth(String code) {
        RestTemplate gitHubRequest = new RestTemplate();
        String clientId = environment.getProperty("github.client.id");
        String clientSecret = environment.getProperty("github.client.secret");

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Accept", "application/json");

        HttpEntity<GithubAccessTokenRequest> request = new HttpEntity<>(
                new GithubAccessTokenRequest(clientId, clientSecret, code),
                httpHeaders
        );

        ResponseEntity<GithubAccessTokenResponse> response = gitHubRequest.postForEntity(
                GITHUB_ACCESS_TOKEN_URL,
                request,
                GithubAccessTokenResponse.class
        );

        logger.info("response : {}", response.getBody());
        return response.getBody();
    }
}

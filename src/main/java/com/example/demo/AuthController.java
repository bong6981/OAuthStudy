package com.example.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.MultiValueMapAdapter;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final Logger logger = LoggerFactory.getLogger(AuthController.class);
    private final URI GITHUB_ACCESS_TOKEN_URI = UriComponentsBuilder
            .fromUriString("https://github.com/login/oauth/access_token")
            .build()
            .toUri();

    private final URI GITHUB_USER_URI = UriComponentsBuilder
            .fromUriString("https://api.github.com/user")
            .build()
            .toUri();

    private final Environment environment;

    public AuthController(Environment environment) {
        this.environment = environment;
    }

    @GetMapping
    public User auth(String code) {
        RestTemplate gitHubRequest = new RestTemplate();
        String clientId = environment.getProperty("github.client.id");
        String clientSecret = environment.getProperty("github.client.secret");

        RequestEntity<GithubAccessTokenRequest> requestEntity = RequestEntity
                .post(GITHUB_ACCESS_TOKEN_URI)
                .header("Accept", "application/json")
                .body(new GithubAccessTokenRequest(clientId, clientSecret, code));

        ResponseEntity<GithubAccessTokenResponse> response = gitHubRequest
                .exchange(requestEntity, GithubAccessTokenResponse.class);

        GithubAccessTokenResponse accessToken = response.getBody();

        logger.info("token : {}", accessToken.getAccessToken());

        RequestEntity<Void> userRequestEntity = RequestEntity
                .get(GITHUB_USER_URI)
                .header("Authorization", "token " + accessToken.getAccessToken())
                .build();

        ResponseEntity<User> userResponse = gitHubRequest.exchange(userRequestEntity, User.class);

        logger.info("response : {}", userResponse.getBody());
        return userResponse.getBody();
    }
}

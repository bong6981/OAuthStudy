package com.bongf.oauthPractice.controller;

import com.bongf.oauthPractice.OAuthToken;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class OauthController {
    Logger logger = LoggerFactory.getLogger(OauthController.class);
    private String code;
    private final Environment environment;

    public OauthController(Environment environment) {
        this.environment = environment;
    }

    @GetMapping("/login/oauth2/code/github")
    public String code(String code) {
        this.code = code;
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("client_id", environment.getProperty("client.id"));
        params.add("client_secret", environment.getProperty("client.secret"));
        params.add("code", code);
        params.add("redirect_url", "http://localhost:8080/login/oauth2/code/github");

        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", "application/json");
        HttpEntity<MultiValueMap<String, String>> tokenRequest = new HttpEntity<>(params, headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange("https://github.com/login/oauth/access_token",
        HttpMethod.POST,
        tokenRequest,
        String.class);

        System.out.println(response.getStatusCode());
        System.out.println(response.getBody());

        ObjectMapper objectMapper = new ObjectMapper();
        OAuthToken oAuthToken;
        try {
            oAuthToken = objectMapper.readValue(response.getBody(), OAuthToken.class);
            System.out.println(oAuthToken.getAccesToken());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return "hello";
    }
}

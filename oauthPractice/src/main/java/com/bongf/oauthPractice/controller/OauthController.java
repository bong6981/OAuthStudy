package com.bongf.oauthPractice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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


    @GetMapping("/login/oauth2/code/github")
    public String code(String code) {
        this.code = code;
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("client_id", "5c74787053b981c45a96");
        params.add("client_secret", "");
        params.add("code", code);
        params.add("redirect_url", "http://localhost:8080/login/oauth2/code/github");

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
        HttpEntity<MultiValueMap<String, String>> tokenRequest = new HttpEntity<>(params, headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange("https://github.com/login/oauth/access_token",
        HttpMethod.POST,
        tokenRequest,
        String.class);

        System.out.println(response.getStatusCode());
        System.out.println(response.getBody());

        return "hello";
    }
}

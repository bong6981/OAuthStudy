package com.bongf.oauthPractice.controller;

import com.bongf.oauthPractice.GithubProfile;
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
    private final String REDIRECT_URL = "http://localhost:8080/login/oauth2/code/github";
    Logger logger = LoggerFactory.getLogger(OauthController.class);

    private final Environment environment;

    public OauthController(Environment environment) {
        this.environment = environment;
    }

    @GetMapping("/login/oauth2/code/github")
    public String githubLogin(String code) throws JsonProcessingException {
        OAuthToken oAuthToken = getOAuthToken(code);
        GithubProfile githubProfile = getGithubProfile(oAuthToken);
        return githubProfile.toString();
    }

    private GithubProfile getGithubProfile(OAuthToken oAuthToken) throws JsonProcessingException {
        RestTemplate profileRequestTemplate = new RestTemplate();
        ResponseEntity<String> profileResponse = profileRequestTemplate.exchange(
                REDIRECT_URL,
                HttpMethod.GET,
                getProfileRequestEntity(oAuthToken),
                String.class
        );

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(profileResponse.getBody(), GithubProfile.class);
    }

    private HttpEntity<MultiValueMap<String, String>> getProfileRequestEntity(OAuthToken oAuthToken) {
        HttpHeaders infoRequestHeaders = new HttpHeaders();
        infoRequestHeaders.add("Authorization", "token " + oAuthToken.getAccesToken());
        return new HttpEntity<>(infoRequestHeaders);
    }

    private OAuthToken getOAuthToken(String code) throws JsonProcessingException {
        HttpEntity<MultiValueMap<String, String>> codeRequestHttpEntity = getCodeRequestHttpEntity(code);
        RestTemplate tokenRequestTemplate = new RestTemplate();
        ResponseEntity<String> response = tokenRequestTemplate.exchange("https://github.com/login/oauth/access_token",
                HttpMethod.POST,
                getCodeRequestHttpEntity(code),
                String.class);

        ObjectMapper objectMapper = new ObjectMapper();
        OAuthToken oAuthToken = null;
        return objectMapper.readValue(response.getBody(), OAuthToken.class);
    }

    private HttpEntity<MultiValueMap<String, String>> getCodeRequestHttpEntity(String code) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("client_id", environment.getProperty("client.id"));
        params.add("client_secret", environment.getProperty("client.secret"));
        params.add("code", code);
        params.add("redirect_url", REDIRECT_URL);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", "application/json");
        return new HttpEntity<>(params, headers);
    }
}

package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpSession;
import java.net.URI;
import java.util.Optional;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private final String GITHUB_ACCESS_TOKEN_URI = "https://github.com/login/oauth/access_token";
    private final String GITHUB_USER_URI = "https://api.github.com/user";

    private final String CLIENT_ID;
    private final String CLIENT_SECRET;

    //private final Environment environment;

    public AuthController(Environment environment) {
        //this.environment = environment;
        CLIENT_ID = environment.getProperty("github.client.id");
        CLIENT_SECRET = environment.getProperty("github.client.secret");
    }

    @GetMapping
    public String auth(String code, HttpSession session) {
        RestTemplate gitHubRequest = new RestTemplate();
        GithubAccessTokenResponse accessToken = getAccessToken(code, gitHubRequest)
                .orElseThrow(() -> new RuntimeException("바디 없음"));

        User user = getUserFromGitHub(accessToken, gitHubRequest)
                .orElseThrow(() -> new RuntimeException("바디 없음"));

        session.setAttribute("user", user);

        return "redirect:/";
    }

    private Optional<User> getUserFromGitHub(GithubAccessTokenResponse accessToken, RestTemplate gitHubRequest) {
        RequestEntity<Void> request = RequestEntity
                .get(GITHUB_USER_URI)
                .header("Accept", "application/json")
                .header("Authorization", "token " + accessToken.getAccessToken())
                .build();

        ResponseEntity<User> response = gitHubRequest
                .exchange(request, User.class);

        return Optional.ofNullable(response.getBody());
    }

    private Optional<GithubAccessTokenResponse> getAccessToken(String code, RestTemplate gitHubRequest) {
        RequestEntity<GithubAccessTokenRequest> request = RequestEntity
                .post(GITHUB_ACCESS_TOKEN_URI)
                .header("Accept", "application/json")
                .body(new GithubAccessTokenRequest(CLIENT_ID, CLIENT_SECRET, code));

        ResponseEntity<GithubAccessTokenResponse> response = gitHubRequest
                .exchange(request, GithubAccessTokenResponse.class);

        return Optional.ofNullable(response.getBody());
    }
}

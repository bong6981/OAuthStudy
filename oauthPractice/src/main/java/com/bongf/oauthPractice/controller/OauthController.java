package com.bongf.oauthPractice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OauthController {
    Logger logger = LoggerFactory.getLogger(OauthController.class);
    private String code;


    @GetMapping("/login/oauth2/code/github")
    public String code(String code) {
        this.code = code;
        return "hello";
    }
}

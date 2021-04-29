package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/personal")
public class LoggedInUserCanSeeController {

    @GetMapping
    public String personal() {
        return "개인 정보";
    }
}

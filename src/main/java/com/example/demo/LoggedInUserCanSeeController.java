package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/personal")
public class LoggedInUserCanSeeController {

    @GetMapping
    public User personal(HttpServletRequest request) {
        User user = new User();
        user.setLogin((String) request.getAttribute("login"));
        user.setName((String) request.getAttribute("name"));
        return user;
    }
}

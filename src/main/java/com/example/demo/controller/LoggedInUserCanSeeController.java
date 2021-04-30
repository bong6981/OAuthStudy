package com.example.demo.controller;

import com.example.demo.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/personal")
public class LoggedInUserCanSeeController {

    @GetMapping
    public User personal(@RequestAttribute User user) {
        return user;
    }
}

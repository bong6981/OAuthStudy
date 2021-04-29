package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
public class HomeController {

    @GetMapping
    public String home(Model model, HttpSession session) {
        model.addAttribute("user", session.getAttribute("accessToken"));
        return "index";
    }
}

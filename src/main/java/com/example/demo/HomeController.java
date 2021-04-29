package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
public class HomeController {

    private final String ACCESS_TOKEN = "accessToken";
    private final String USER = "user";

    @GetMapping
    public String home(Model model, HttpSession session) {
        if (session.getAttribute(ACCESS_TOKEN) != null) {
            model.addAttribute(USER, USER);
        }
        return "index";
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute(ACCESS_TOKEN);
        return "redirect:/";
    }
}

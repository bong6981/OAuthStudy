package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
public class HomeController {

    private final String USER = "user";

    @GetMapping
    public String home(Model model, HttpSession session) {
        User user = (User) session.getAttribute(USER);
        if (user != null) {
            model.addAttribute(USER, user);
        }
        return "index";
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute(USER);
        return "redirect:/";
    }
}

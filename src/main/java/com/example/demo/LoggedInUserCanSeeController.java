package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/personal")
public class LoggedInUserCanSeeController {

    @GetMapping
    public String personal(Model model, HttpSession session) {
        model.addAttribute("user", session.getAttribute("user"));
        return "personal";
    }
}

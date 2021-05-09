package com.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class IndexController {
    @GetMapping("/")
    public String index(Principal principal, Model model) {
        return principal != null ? "redirect:table" : "redirect:login";
    }

    @GetMapping("/login")
    public String login(Principal principal, Model model) {
        return principal != null ? "redirect:table" : "login.html";
    }
}

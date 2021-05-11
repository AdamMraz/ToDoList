package com.controller;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
public class IndexController {
    @GetMapping("/")
    public String index (Principal principal) {
        return principal != null ? "redirect:table" : "redirect:login";
    }

    @GetMapping("/login")
    public String login (Principal principal) {
        System.out.println("Login");
        return principal != null ? "redirect:table" : "login.html";
    }

    @GetMapping(value = "/login", params = "error")
    public String login (@RequestParam String error, Principal principal, Model model) {
        model.addAttribute("errorText", "Неправильный логин или пароль");
        return principal != null ? "redirect:table" : "login.html";
    }
}

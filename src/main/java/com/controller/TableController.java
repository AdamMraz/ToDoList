package com.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class TableController {

    @GetMapping("/table")
    public String table(Model model) throws Exception {
        return "table.html";
    }
}

package com.controller;

import com.model.Case;
import com.model.User;
import com.service.UserManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class TableController {
    private final UserManagerService userManagerService;

    @GetMapping("/table")
    public String table(Principal principal, Model model) throws Exception {
        User user = userManagerService.findByUsername(principal.getName());
        List<Case> caseList = new ArrayList<>();
        caseList.addAll(user.getCases());
        model.addAttribute("caseList", caseList);
        return "table.html";
    }
}

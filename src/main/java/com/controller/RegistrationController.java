package com.controller;

import com.dto.UserDTO;
import com.model.Role;
import com.model.User;
import com.service.UserManagerService;
import com.service.converters.ConvertUserDtoToModel;
import com.service.exceptions.UserIsException;
import com.service.responces.annotations.UserExceptionHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping("/registration")
@RequiredArgsConstructor
@UserExceptionHandler
public class RegistrationController {
    private final UserManagerService userManagerService;

    @GetMapping
    public String registration (Principal principal, Model model) {
         return principal != null ? "redirect:/table" : "registration.html";
    }

    @PostMapping
    public String addUser(UserDTO userForm, Principal principal, Model model) throws UserIsException {
        if (principal != null) {
            return "redirect:/table";
        }
        User user = new User();
        if (userForm.getPassword().equals(userForm.getConfirmPassword())) {
            user = ConvertUserDtoToModel.ConverterDtoToModel(userForm);
        }
        else {
            model.addAttribute("errorText", "Пароли не совпадают");
            return "registration.html";
        }
        StringBuilder apiKey = new StringBuilder();
        for (int i = 0; i < 15; i++) {
            switch ((int)(Math.random() * 10) % 3) {
                case 0:
                    apiKey.append((char) (48 + (int)(Math.random() * 10)));
                    break;
                case 1:
                    apiKey.append((char) (65 + (int)(Math.random() * 100) % 26));
                    break;
                case 2:
                    apiKey.append((char)(97 + (int)(Math.random() * 100) % 26));
                    break;
            }
        }
        user.setApiKey(apiKey.toString());
        Role role = new Role();
        role.setName("ROLE_USER");
        role.setId(2);
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(role);
        user.setRoles(roleSet);
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        try {
            userManagerService.saveUser(user);
        }
        catch (UserIsException e) {
            model.addAttribute("errorText", "Пользователь с таким логином уже существует");
            return "registration.html";
        }
        return "redirect:/";
    }
}

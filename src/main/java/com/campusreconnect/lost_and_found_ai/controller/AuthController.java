package com.campusreconnect.lost_and_found_ai.controller;

import com.campusreconnect.lost_and_found_ai.entity.Role;
import com.campusreconnect.lost_and_found_ai.entity.User;
import com.campusreconnect.lost_and_found_ai.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user) {
        user.setRole(Role.USER);
        userService.register(user);
        return "redirect:/login";
    }


    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }
}


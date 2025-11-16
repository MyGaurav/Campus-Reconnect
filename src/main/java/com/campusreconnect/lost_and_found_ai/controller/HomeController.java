package com.campusreconnect.lost_and_found_ai.controller;
import com.campusreconnect.lost_and_found_ai.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @Autowired
    private PostService postService;

    @GetMapping("/home")
    public String homePage() {
        return "home";
    }
}



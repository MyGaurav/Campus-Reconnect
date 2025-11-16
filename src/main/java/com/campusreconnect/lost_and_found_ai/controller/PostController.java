package com.campusreconnect.lost_and_found_ai.controller;

import com.campusreconnect.lost_and_found_ai.entity.Post;
import com.campusreconnect.lost_and_found_ai.entity.Type;
import com.campusreconnect.lost_and_found_ai.entity.User;
import com.campusreconnect.lost_and_found_ai.service.PostService;
import com.campusreconnect.lost_and_found_ai.service.UserService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/post")
public class PostController {

    private final PostService postService;
    private final UserService userService;
    public PostController(PostService postService, UserService userService){
        this.postService = postService;
        this.userService = userService;
    }

    @GetMapping("/create")
    public String createPostPage() {
        return "create_post";
    }

    @PostMapping("/save")
    public String savePost(@RequestParam String title,
                           @RequestParam String postDesc,
                           @RequestParam Type type) {

        User loggedUser = userService.getLoggedInUser();

        Post post = new Post();
        post.setTitle(title);
        post.setPostDesc(postDesc);
        post.setType(type);
        post.setUser(loggedUser);

        postService.savePost(post);

        return "redirect:/home";
    }
}

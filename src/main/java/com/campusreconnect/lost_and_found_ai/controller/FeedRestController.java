package com.campusreconnect.lost_and_found_ai.controller;

import com.campusreconnect.lost_and_found_ai.dto.PostDTO;
import com.campusreconnect.lost_and_found_ai.entity.Post;
import com.campusreconnect.lost_and_found_ai.entity.User;
import com.campusreconnect.lost_and_found_ai.service.PostService;
import com.campusreconnect.lost_and_found_ai.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class FeedRestController {

    private final PostService postService;
    private final UserService userService;

    public FeedRestController(PostService postService, UserService userService) {
        this.postService = postService;
        this.userService = userService;
    }

    @GetMapping("/feed")
    public Map<String, Object> getFeed(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        User loggedUser = userService.getLoggedInUser();
        Page<Post> postPage = postService.getAllPostsExcludingUser(loggedUser.getId(), page, size);

        Page<PostDTO> dtoPage = postPage.map(post -> new PostDTO(
                post.getId(),
                post.getTitle(),
                post.getPostDesc(),
                post.getType().name(),
                post.getUser().getUsername(),
                post.getCreatedAt()
        ));

        Map<String, Object> response = new HashMap<>();
        response.put("content", dtoPage.getContent());
        response.put("currentPage", dtoPage.getNumber());
        response.put("totalPages", dtoPage.getTotalPages());
        response.put("last", dtoPage.isLast());

        return response;
    }


}
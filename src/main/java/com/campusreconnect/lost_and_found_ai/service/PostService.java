package com.campusreconnect.lost_and_found_ai.service;

import com.campusreconnect.lost_and_found_ai.dto.MatchedPostDTO;
import com.campusreconnect.lost_and_found_ai.entity.Post;
import com.campusreconnect.lost_and_found_ai.entity.Type;
import com.campusreconnect.lost_and_found_ai.entity.User;
import com.campusreconnect.lost_and_found_ai.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserService userService;

    public PostService(PostRepository postRepository, UserService userService) {
        this.postRepository = postRepository;
        this.userService = userService;
    }

    @Autowired
    RestTemplate restTemplate;


    public void savePost(Post post) {
        User currentUser = userService.getLoggedInUser();
        post.setUser(currentUser);
        postRepository.save(post);

    }


    public Page<Post> getAllPosts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return postRepository.findAll(pageable);
    }


    public Page<Post> getAllPostsExcludingUser(long userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return postRepository.findAllExceptUser(userId, pageable);
    }


    public Post getUsersMostRecentLostPost() {
        User user = userService.getLoggedInUser();
        return postRepository.findLatestLostPostByUserId(user.getId());
    }


    public List<Post> getFoundPosts() {
        Long userId = getCurrentUserId();
        return postRepository.findByTypeAndUserIdNot(Type.FOUND, userId);
    }


    private Long getCurrentUserId() {
        return userService.getLoggedInUser().getId();
    }
    public List<Double> getMatchScores(String lostDesc, List<String> foundDescs) {
        String url = "http://localhost:5000/compare";

        Map<String, Object> body = new HashMap<>();
        body.put("lost_text", lostDesc);
        body.put("found_texts", foundDescs);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(url, request, Map.class);

        List<Double> matches = (List<Double>) response.getBody().get("matches");
        return matches != null ? matches : List.of();
    }
    public List<MatchedPostDTO> getMatchedFoundPosts(Post lostPost) {
        List<Post> foundPosts = getFoundPosts();
        List<MatchedPostDTO> result = new ArrayList<>();

        for (Post found : foundPosts) {
            double score = computeSimilarity(lostPost, found);
            result.add(new MatchedPostDTO(found, score));
        }


        result.sort((a, b) -> Double.compare(b.getScore(), a.getScore()));

        return result;
    }


    private double computeSimilarity(Post lostPost, Post foundPost) {
        try {
            String url = "http://localhost:5000/compare";


            Map<String, Object> body = new HashMap<>();
            body.put("lost_text", lostPost.getTitle() + " " + lostPost.getPostDesc());
            body.put("found_texts", List.of(foundPost.getTitle() + " " + foundPost.getPostDesc()));

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);


            ResponseEntity<Map> response = restTemplate.postForEntity(url, request, Map.class);


            List<Double> matches = (List<Double>) response.getBody().get("matches");

            if (matches != null && !matches.isEmpty()) {
                return matches.get(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0.0;
    }


}




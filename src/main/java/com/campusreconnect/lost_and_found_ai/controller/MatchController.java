    package com.campusreconnect.lost_and_found_ai.controller;

    import com.campusreconnect.lost_and_found_ai.dto.MatchedPostDTO;
    import com.campusreconnect.lost_and_found_ai.entity.Post;
    import com.campusreconnect.lost_and_found_ai.service.PostService;
    import org.springframework.stereotype.Controller;
    import org.springframework.ui.Model;
    import org.springframework.web.bind.annotation.GetMapping;
    import java.util.List;
    import java.util.stream.Collectors;


    @Controller
    public class MatchController {

        private final PostService postService;

        public MatchController(PostService postService) {
            this.postService = postService;
        }

        @GetMapping("/match")
        public String matchPage(Model model) {
            Post lostPost = postService.getUsersMostRecentLostPost();

            if (lostPost == null) {
                model.addAttribute("error", "You haven’t created any LOST post yet.");
                return "match_page";
            }

            List<MatchedPostDTO> matchedPosts = postService.getMatchedFoundPosts(lostPost);

            // Separate matched and non-matched posts
            List<MatchedPostDTO> strongMatches = matchedPosts.stream()
                    .filter(p -> p.getScore() >= 0.7)
                    .collect(Collectors.toList());

            List<MatchedPostDTO> otherPosts = matchedPosts.stream()
                    .filter(p -> p.getScore() < 0.7)
                    .collect(Collectors.toList());

            model.addAttribute("lostPost", lostPost);
            model.addAttribute("strongMatches", strongMatches);
            model.addAttribute("otherPosts", otherPosts);

            return "match_page";
        }
    }



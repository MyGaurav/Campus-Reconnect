package com.campusreconnect.lost_and_found_ai.dto;
import com.campusreconnect.lost_and_found_ai.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MatchedPostDTO {
    private Post post;
    private double score;
}

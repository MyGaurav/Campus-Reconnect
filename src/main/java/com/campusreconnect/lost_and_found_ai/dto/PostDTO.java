package com.campusreconnect.lost_and_found_ai.dto;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@Data
public class PostDTO {
    private Long id;
    private String title;
    private String postDesc;
    private String type;
    private String username;
    private LocalDateTime createdAt;

    public PostDTO(Long id, String title, String postDesc, String type, String username, LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.postDesc = postDesc;
        this.type = type;
        this.username = username;
        this.createdAt = createdAt;
    }

}


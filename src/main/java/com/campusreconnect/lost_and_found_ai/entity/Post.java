package com.campusreconnect.lost_and_found_ai.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Table(name = "posts")
@NoArgsConstructor
@Getter
@Setter
public class  Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String title;
    @Column(length = 2000)
    private String postDesc;
    @Enumerated(EnumType.STRING)
    private Type type;

    private LocalDateTime createdAt = LocalDateTime.now();

    @Transient
    private Double score;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;


}

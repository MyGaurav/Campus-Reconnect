package com.campusreconnect.lost_and_found_ai.repository;

import com.campusreconnect.lost_and_found_ai.entity.Post;
import com.campusreconnect.lost_and_found_ai.entity.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("SELECT p FROM Post p WHERE p.user.id <> :userId ORDER BY p.createdAt DESC")
    Page<Post> findAllExceptUser(@Param("userId") Long userId, Pageable pageable);

    @Query("SELECT p FROM Post p WHERE p.user.id = :userId AND p.type = com.campusreconnect.lost_and_found_ai.entity.Type.LOST ORDER BY p.createdAt DESC LIMIT 1")
    Post findLatestLostPostByUserId(@Param("userId") Long userId);

    List<Post> findByTypeAndUserIdNot(Type type, Long userId);

}


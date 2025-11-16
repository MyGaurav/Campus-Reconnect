package com.campusreconnect.lost_and_found_ai.repository;

import com.campusreconnect.lost_and_found_ai.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
     Optional<User> findByEmail(String email);

}

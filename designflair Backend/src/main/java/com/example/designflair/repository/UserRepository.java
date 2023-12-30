package com.example.designflair.repository;


import com.example.designflair.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsernameAndPassword(String username, String password);
    Boolean existsByUsername(String username);

    Optional<User> findByUsername(String username);
}

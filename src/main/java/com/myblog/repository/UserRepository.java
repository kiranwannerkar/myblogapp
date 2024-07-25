package com.myblog.repository;

import com.myblog.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    // below all are Custom method work like query
    Optional<User> findByEmail(String email);// this will search data in database with help of email id
    Optional<User> findByUsernameOrEmail(String username,String email);
    Optional<User> findByUsername(String username);
    Boolean existsByUsername(String username);// this ,method will check does user exist
    Boolean existsByEmail(String email);
}

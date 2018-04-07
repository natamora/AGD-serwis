package com.ksundaysky.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ksundaysky.model.User;

@Repository("userRepository")
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
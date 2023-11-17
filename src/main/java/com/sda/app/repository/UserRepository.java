package com.sda.app.repository;

import com.sda.app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> getByUsername(String username);

    Optional<User> getByEmail(String email);
}

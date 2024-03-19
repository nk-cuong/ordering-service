package com.esoft.orderingservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.esoft.orderingservice.entity.User;

public interface UserRepo extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.roles r WHERE u.username = :username")
    Optional<User> findUserAndRolesByUsername(String username);
}

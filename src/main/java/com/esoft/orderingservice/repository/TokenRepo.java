package com.esoft.orderingservice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.esoft.orderingservice.entity.Token;

public interface TokenRepo extends JpaRepository<Token, Long> {

    Optional<Token> findByToken(String token);

    List<Token> findAllTokensByUserUsername(String username);
    
}

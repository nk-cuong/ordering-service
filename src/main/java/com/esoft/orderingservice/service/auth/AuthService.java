package com.esoft.orderingservice.service.auth;

import java.util.List;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.esoft.orderingservice.dto.LoginRequest;
import com.esoft.orderingservice.dto.LoginResponse;
import com.esoft.orderingservice.entity.Token;
import com.esoft.orderingservice.entity.User;
import com.esoft.orderingservice.repository.TokenRepo;
import com.esoft.orderingservice.repository.UserRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtService jwtService;
    private final UserRepo userRepo;
    private final TokenRepo tokenRepo;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public LoginResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        
        String token = jwtService.generateToken(userDetails);
        this.revokeAllTokenByUser(userDetails);
        this.saveToken(token, userDetails);
        return new LoginResponse(token);
    }

    private void revokeAllTokenByUser(UserDetails userDetails) {
        List<Token> tokens = tokenRepo.findAllTokensByUserUsername(userDetails.getUsername());
        if (CollectionUtils.isEmpty(tokens)) {
            return;
        }

        tokens.forEach(t -> {
            t.setLoggedOut(true);
        });

        tokenRepo.saveAll(tokens);
    }

    private void saveToken(String jwt, UserDetails userDetails) {
        User user = userRepo.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException(
                        "No user found with username: " + userDetails.getUsername()));

        Token token = new Token();
        token.setToken(jwt);
        token.setLoggedOut(false);
        token.setUser(user);
        tokenRepo.save(token);
    }
}

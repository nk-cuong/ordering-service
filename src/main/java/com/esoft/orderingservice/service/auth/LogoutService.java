package com.esoft.orderingservice.service.auth;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

import com.esoft.orderingservice.repository.TokenRepo;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Service("logoutHandler")
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {

    private final TokenRepo tokenRepo;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }

        String token = authHeader.substring(7);
        tokenRepo.findByToken(token).ifPresent(t -> {
            t.setLoggedOut(true);
            tokenRepo.save(t);
        });
    }

}

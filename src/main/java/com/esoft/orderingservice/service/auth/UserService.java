package com.esoft.orderingservice.service.auth;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.esoft.orderingservice.entity.User;
import com.esoft.orderingservice.repository.UserRepo;

import lombok.RequiredArgsConstructor;

@Service("userDetailsService")
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepo.findUserAndRolesByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("No user found with username: " + username));

        return org.springframework.security.core.userdetails.User.builder()
                .username(username)
                .password(user.getPassword())
                .roles(getRoles(user))
                .build();
    }

    private String[] getRoles(User user) {
        return user.getRoles()
                .stream()
                .map(role -> role.getRoleName().name())
                .toArray(String[]::new);
    }
}

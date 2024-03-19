package com.esoft.orderingservice.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.web.servlet.HandlerExceptionResolver;

import com.esoft.orderingservice.constant.RoleName;

import lombok.RequiredArgsConstructor;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtAuthFilter jwtAuthenFilter;
    private final LogoutHandler logoutHandler;
    private final UserDetailsService userDetailsService;

    @Qualifier("handlerExceptionResolver")
    private final HandlerExceptionResolver handlerExceptionResolver;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.csrf(AbstractHttpConfigurer::disable);

        httpSecurity.authorizeHttpRequests(
                request -> request.requestMatchers("/v1/auth/login", "/actuator/**", "/api-docs/**").permitAll()
                        .requestMatchers("/v1/admin/**").hasRole(RoleName.ADMIN.name())
                        .requestMatchers("/v1/customer/**").hasRole(RoleName.CUSTOMER.name())
                        .anyRequest().authenticated());

        httpSecurity.sessionManagement(
                sessionConfig -> sessionConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        httpSecurity.addFilterBefore(this.jwtAuthenFilter, UsernamePasswordAuthenticationFilter.class);

        httpSecurity.exceptionHandling(
                exception -> exception.authenticationEntryPoint(authenticationEntryPoint()));

        httpSecurity.logout(
                logout -> logout.logoutUrl("/v1/auth/logout")
                        .addLogoutHandler(logoutHandler)
                        .logoutSuccessHandler(
                                (request, response, authentication) -> SecurityContextHolder.clearContext()));

        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return (request, response, authException) -> this.handlerExceptionResolver.resolveException(request, response,
                null,
                authException);
    }
}

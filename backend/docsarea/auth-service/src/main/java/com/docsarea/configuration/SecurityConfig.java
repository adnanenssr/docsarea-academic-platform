package com.docsarea.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain (HttpSecurity http) throws Exception {
        return http
                .csrf(customizer -> customizer.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/register" , "/auth/login").permitAll()
                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer( auth -> auth.jwt(Customizer.withDefaults()))
                .build() ;


    }





}

package com.docsarea.configuration;

import feign.Contract;
import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.cloud.openfeign.support.SpringMvcContract;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    JwtFilter jwtFilter ;



    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
        configuration.setAllowedMethods(Arrays.asList("GET","POST","PUT","PATCH","DELETE","OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*")); // Allow all headers
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L); // Cache preflight for 1 hour

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }


    @Bean
    public SecurityFilterChain filterChain (HttpSecurity http ) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/register" , "/get/recommendations", "/group/*/get/avatar" , "/group/*/get/cover" , "/api/get/cover" , "/api/get/avatar" , "/api/get/cover/*" , "/api/get/avatar/*" , "/thumbnail/*", "/user/*" , "/group/*" ,  "/login" ,"/file/**" , "/get/user/*/files/published" , "/get/group/*/files/published" , "/search").permitAll()
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll() // Allow all OPTIONS requests
                        .anyRequest().authenticated()
                )
                .httpBasic(basic -> basic.disable())
                .formLogin(form -> form.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtFilter , UsernamePasswordAuthenticationFilter.class)
                .oauth2ResourceServer( auth -> auth.jwt(Customizer.withDefaults()))
                .exceptionHandling(exp -> exp
                                .accessDeniedHandler((request , response , accessDeniedException) -> {
                                    response.setContentType("application/json");
                                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                                    response.getWriter().write("{\"message\":\"You don’t have permission to access this resource.\"}"
                                    ) ;
                                })
                                .authenticationEntryPoint((request , response , authException) -> {
                                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                                    response.setContentType("application/json");
                                    response.getWriter().write("{\"message\":\"Your session expired. Please log in again.\"}"
                                    );

                                }))
                .build() ;







    }




}

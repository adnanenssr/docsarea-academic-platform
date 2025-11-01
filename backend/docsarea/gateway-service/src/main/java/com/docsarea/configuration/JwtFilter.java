package com.docsarea.configuration;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
public class JwtFilter extends OncePerRequestFilter {



    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        Cookie [] cookies = request.getCookies() ;

        String path = request.getRequestURI() ;

        if(path.equals("/login") || path.equals("/register")){
            filterChain.doFilter(request , response );
        }




        // Consider a Cookie does not exist exception
        if(cookies != null ){
            for (Cookie cookie : cookies ){
                if(cookie.getName().equals("bearer")  && !cookie.getValue().isEmpty()){
                        final String token = cookie.getValue() ;
                        request = new HttpServletRequestWrapper(request) {
                            @Override
                            public String getHeader(String name) {
                                if( name.equals("Authorization")){
                                    return "Bearer " + token ;
                                }
                                return super.getHeader(name);
                            }
                        } ;

                }

            }

        }
        System.out.println("we are at the doFileter in jwtFilter");
        filterChain.doFilter(request , response);





    }
}
























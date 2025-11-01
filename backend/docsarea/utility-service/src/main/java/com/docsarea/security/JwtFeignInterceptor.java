package com.docsarea.security;


import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

@Configuration
public class JwtFeignInterceptor  {

    @Bean
    public RequestInterceptor jwtInterceptor () {
         return requestTemplate -> {



                Authentication auth = SecurityContextHolder.getContext().getAuthentication() ;
                if(auth instanceof JwtAuthenticationToken jwtAuth){
                    System.out.println("we are here") ;
                    String token = jwtAuth.getToken().getTokenValue() ;
                    System.out.println(token);
                    if (requestTemplate.headers().get("Authorization") != null ) return ;
                    requestTemplate.header("Authorization" , "Bearer " + token ) ;
                    System.out.println(requestTemplate.headers());
                    return ;
                }




        } ;
    }


}

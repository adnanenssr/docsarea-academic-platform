package com.docsarea.service;

import com.docsarea.controller.AuthFeignController;
import com.docsarea.dtos.user.LoginDto;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    @Autowired
    AuthFeignController authController ;

    public String doLogin (LoginDto dto ){
        return authController.doLogin(dto) ;
    }

    public void createCookie(String accessToken , HttpServletResponse response ){
        ResponseCookie cookie = ResponseCookie.from("bearer" , accessToken)
                .secure(true)
                .httpOnly(true)
                .sameSite("none")
                .maxAge(60 * 60 * 24 * 7)
                .build() ;
        response.addHeader("set-Cookie" , cookie.toString() );

    }

}

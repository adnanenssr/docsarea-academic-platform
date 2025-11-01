package com.docsarea.service;

import com.docsarea.controller.AuthFeignController;
import com.docsarea.dtos.user.LoginDto;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;

@Service
public class Logout {
    @Autowired
    AuthFeignController authController ;

    public void doLogout (){
         authController.logout() ;
    }

    public void invalidateCookie(HttpServletResponse response ){
        ResponseCookie cookie = ResponseCookie.from("bearer" , "")
                .secure(true)
                .path("/")
                .httpOnly(true)
                .sameSite("none")
                .maxAge(0)
                .build() ;
        response.addHeader("set-Cookie" , cookie.toString() );

    }
}

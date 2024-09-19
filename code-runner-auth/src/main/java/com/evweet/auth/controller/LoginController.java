package com.evweet.auth.controller;

import com.evweet.auth.enums.AuthType;
import com.evweet.auth.model.dto.LoginDto;
import com.evweet.auth.service.OAuth2Service;
import com.evweet.auth.service.UsernamePasswordService;
import com.evweet.common.response.Response;
import com.evweet.common.result.AuthenticationResult;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.web.authentication.rememberme.CookieTheftException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class LoginController {
    @Autowired
    UsernamePasswordService usernamePasswordService;

    @PostMapping("/authenticate")
    public Response<String> login(@ModelAttribute LoginDto loginDto, HttpServletResponse response) {
        // return a JWT token if the authentication is successful
        String jwt = usernamePasswordService.authenticate(loginDto);
        // cookie exists in the same domain as the server
        Cookie cookie = new Cookie("jwt", jwt);
        cookie.setMaxAge(60 * 60 * 24);
        cookie.setHttpOnly(true);
        cookie.setPath("/");

        response.addCookie(cookie);
        return Response.success();
    }

    @PostMapping("/logout")
    public Response logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("jwt", null);
        cookie.setMaxAge(0);
        cookie.setHttpOnly(true);
        cookie.setPath("/");

        response.addCookie(cookie);
        return Response.success();
    }
}

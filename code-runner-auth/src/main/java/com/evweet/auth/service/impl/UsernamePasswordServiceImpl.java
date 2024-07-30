package com.evweet.auth.service.impl;

import com.evweet.auth.dao.UserRepository;
import com.evweet.auth.enums.AuthType;
import com.evweet.auth.model.dto.LoginDto;
import com.evweet.auth.model.entity.RcUser;
import com.evweet.auth.service.UsernamePasswordService;
import com.evweet.common.exception.AuthException;
import com.evweet.common.result.AuthenticationResult;
import com.evweet.common.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Optional;

@Service
public class UsernamePasswordServiceImpl implements UsernamePasswordService {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Override
    public String authenticate(LoginDto loginDto) {
        RcUser rcUser = null;
        String usernameEntered = loginDto.getUsername();
        String passwordEntered = loginDto.getPassword();
        AuthType authType = AuthType.valueOf(loginDto.getAuthType());
        switch (authType) {
            case LOCAL:
                rcUser = userRepository.findByUsername(usernameEntered).orElseThrow(() -> new RuntimeException("User not found"));
                break;
            case GITHUB:
                // if the user logs in with GitHub, the username is the OAuth2 username and the password is the OAuth2 ID
                String oauth2Id = loginDto.getOAuth2Id();
                rcUser = userRepository.findByOauth2Id(oauth2Id).orElseThrow(() -> new RuntimeException("User not found"));
                usernameEntered = rcUser.getUsername();
                passwordEntered = oauth2Id;
                break;
            default:
                throw new RuntimeException("Unsupported authentication type");
        }

        // authenticate the user identity
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(usernameEntered, passwordEntered));
        } catch (AuthenticationException e) {
            throw new AuthException("Authentication failed. Please try again!");
        }

        // generate a JWT token and return it
        Long userId = rcUser.getId();
        return JwtUtil.generateToken(userId, new HashMap<>());
    }


}

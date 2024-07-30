package com.evweet.auth.controller;

import com.evweet.auth.enums.AuthType;
import com.evweet.auth.service.OAuth2Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping("/oauth2")
public class OAuth2Controller {
    @Autowired
    OAuth2Service oauth2Service;

    @GetMapping("/code/github")
    public RedirectView githubOAuth(String code, String state) {
        String redirectUrl = oauth2Service.authenticate(AuthType.GITHUB, code);
        return new RedirectView(redirectUrl);
    }
}

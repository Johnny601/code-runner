package com.evweet.auth.service;

import com.evweet.auth.enums.AuthType;

public interface OAuth2Service {
    String authenticate(AuthType authType, String code);
}

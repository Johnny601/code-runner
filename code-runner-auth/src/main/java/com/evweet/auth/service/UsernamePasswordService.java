package com.evweet.auth.service;

import com.evweet.auth.enums.AuthType;
import com.evweet.auth.model.dto.LoginDto;
import com.evweet.common.result.AuthenticationResult;

public interface UsernamePasswordService {
    String authenticate(LoginDto loginDto);
}

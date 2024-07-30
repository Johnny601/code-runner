package com.evweet.auth.model.dto;

import com.evweet.auth.enums.AuthType;
import lombok.Data;

@Data
public class LoginDto {
    String oAuth2Id;
    String username;
    String authType;
    String password;
}

package com.evweet.common.result;

import lombok.Data;

@Data
public class AuthenticationResult {
    Boolean success;
    String jwt;
}

package com.evweet.auth.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AuthType {
    LOCAL(""), GITHUB("github"), GOOGLE("google");

    private final String value;

}

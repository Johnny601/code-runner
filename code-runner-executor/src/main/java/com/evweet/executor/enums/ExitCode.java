package com.evweet.executor.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExitCode {
    SUCCESS(0),
    ERROR(1);

    private final int code;
}

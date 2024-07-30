package com.evweet.executor.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Driver;

@Getter
@AllArgsConstructor
public enum ExitCode {
    SUCCESS(0),
    ERROR(1);

    private final int code;


}

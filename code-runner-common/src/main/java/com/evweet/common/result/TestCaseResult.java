package com.evweet.common.result;

import lombok.Data;

@Data
public class TestCaseResult {
    Integer code;

    String inputArguments;

    // correct answer
    String expectedResult;

    String actualResult;
}

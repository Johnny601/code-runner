package com.evweet.common.result;

import lombok.Data;

@Data
public class TestCaseResult {
    Integer code;

    Boolean passed;

    String inputArguments;

    // correct answer
    String expectedResult;

    String executionResult;

    public TestCaseResult() {
        // set failed by default
        this.code = 0;
        this.passed = false;
    }


}

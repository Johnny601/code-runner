package com.evweet.common.result;

import lombok.Data;

import java.util.Arrays;
import java.util.List;

@Data
public class ProblemExecutionResult {
    Integer correctTestCases;
    List<TestCaseResult> testCaseResults;

    public void init(int size) {
        this.correctTestCases = 0;
        this.testCaseResults = Arrays.asList(new TestCaseResult[size]);
    }

    public void setTestCaseResult(int index, TestCaseResult testCaseResult) {
        this.testCaseResults.set(index, testCaseResult);
    }
}

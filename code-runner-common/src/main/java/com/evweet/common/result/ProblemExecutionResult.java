package com.evweet.common.result;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ProblemExecutionResult {
    Integer code;
    List<TestCaseResult> testCaseResults = new ArrayList<>();
}

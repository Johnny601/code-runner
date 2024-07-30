package com.evweet.executor.service;

import com.evweet.common.result.PlaygroundExecutionResult;
import com.evweet.common.result.ProblemExecutionResult;
import com.evweet.executor.enums.ProgLang;

public interface CodeExecutionService {
    PlaygroundExecutionResult runPlaygroundCode(ProgLang progLang, String codeToExecute);

    ProblemExecutionResult runProblemSetCode(Long problemId, ProgLang progLang, String codeToExecute);
}

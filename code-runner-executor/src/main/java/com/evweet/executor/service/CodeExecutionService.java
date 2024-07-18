package com.evweet.executor.service;

import com.evweet.common.result.PlaygroundExecutionResult;
import com.evweet.executor.enums.ProgLang;

public interface CodeExecutionService {
    PlaygroundExecutionResult runPlaygroundCode(ProgLang progLang, String codeToExecute);

    String runProblemSetCode(String problemName, ProgLang progLang, String codeToExecute);
}

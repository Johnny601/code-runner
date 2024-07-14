package com.evweet.executor.service;

import com.evweet.executor.enums.ProgLang;

import java.io.File;

public interface CodeExecutionService {
    public String runCode(ProgLang progLang, String codeToExecute);
}

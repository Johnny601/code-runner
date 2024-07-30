package com.evweet.executor.model.dto;

import lombok.Data;

@Data
public class RunProblemSetCodeDto {
    Long problemId;
    String progLang;
    String codeToExecute;
}

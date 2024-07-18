package com.evweet.executor.model.dto;

import lombok.Data;

@Data
public class RunProblemSetCodeDto {
    String problemName;
    String progLang;
    String codeToExecute;
}

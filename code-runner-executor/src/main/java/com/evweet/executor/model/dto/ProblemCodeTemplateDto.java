package com.evweet.executor.model.dto;

import com.evweet.executor.enums.ProgLang;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProblemCodeTemplateDto {
    private Long problemId;
    private String problemName;
    private String description;
    private ProgLang progLang;
    private String codeTemplate;
}

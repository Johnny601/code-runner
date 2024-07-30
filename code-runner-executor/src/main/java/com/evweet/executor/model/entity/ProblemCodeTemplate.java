package com.evweet.executor.model.entity;

import com.evweet.executor.enums.ProgLang;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "problem_code_template")
public class ProblemCodeTemplate {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long problemId;
    private ProgLang progLang;
    private String codeTemplate;
}

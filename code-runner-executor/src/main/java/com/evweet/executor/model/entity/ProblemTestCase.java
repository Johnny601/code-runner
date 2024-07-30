package com.evweet.executor.model.entity;

import com.evweet.executor.enums.ProgLang;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "problem_testcase")
public class ProblemTestCase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long problemId;
    private String testCaseString;
}

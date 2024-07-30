package com.evweet.executor.model.po;

import com.evweet.executor.enums.ProblemDifficulty;
import lombok.Data;

@Data
public class DemoProblem{
    private Long id;
    private String pathname;
    private String name;
    private ProblemDifficulty difficulty;
    private Boolean solved;

    public DemoProblem() {
        solved = false;
    }
}

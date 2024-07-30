package com.evweet.executor.service;

import com.evweet.executor.model.entity.Problem;
import com.evweet.executor.model.po.DemoProblem;

import java.util.List;

public interface ProblemService {
    List<DemoProblem> getDemoProblems(List<Long> Ids);
}

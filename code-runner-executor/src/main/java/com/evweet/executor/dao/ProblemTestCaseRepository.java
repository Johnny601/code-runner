package com.evweet.executor.dao;

import com.evweet.executor.model.entity.ProblemTestCase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProblemTestCaseRepository extends JpaRepository<ProblemTestCase, Long> {
    ProblemTestCase findByProblemId(Long problemId);
}

package com.evweet.executor.dao;

import com.evweet.executor.model.entity.Problem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProblemRepository extends JpaRepository<Problem, Long>{
    Problem findByName(String name);
}


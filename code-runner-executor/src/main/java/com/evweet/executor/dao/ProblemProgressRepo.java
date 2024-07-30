package com.evweet.executor.dao;

import com.evweet.executor.model.entity.ProblemProgress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface ProblemProgressRepo extends JpaRepository<ProblemProgress, Long>{
    Optional<ProblemProgress> findByUserId(Long userId);
}

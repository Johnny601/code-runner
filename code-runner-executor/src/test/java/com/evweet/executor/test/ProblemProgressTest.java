package com.evweet.executor.test;

import com.alibaba.fastjson2.JSON;
import com.evweet.executor.dao.ProblemProgressRepo;
import com.evweet.executor.model.entity.ProblemProgress;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class ProblemProgressTest {
    @Autowired
    ProblemProgressRepo problemProgressRepo;

    @Test
    public void testAddCompletedProblem() {
        ProblemProgress problemProgress = problemProgressRepo.findByUserId(1L).orElse(null);
        if (problemProgress == null) {
            problemProgress = new ProblemProgress();
            problemProgress.setUserId(1L);
            List<Long> completedProblemIds = new ArrayList<>();
            completedProblemIds.add(1L);
            problemProgress.setProblemIds(JSON.toJSONString(completedProblemIds));
            problemProgressRepo.save(problemProgress);
        } else {
            String problemIds = problemProgress.getProblemIds();
            List<Long> completedProblemIds = JSON.parseArray(problemIds, Long.class);
            completedProblemIds.add(2L);
            problemProgress.setProblemIds(JSON.toJSONString(completedProblemIds));
            problemProgressRepo.save(problemProgress);
        }
    }
}

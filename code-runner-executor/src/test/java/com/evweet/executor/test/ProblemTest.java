package com.evweet.executor.test;

import com.evweet.executor.dao.ProblemRepository;
import com.evweet.executor.model.entity.Problem;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ProblemTest {

    @Autowired
    private ProblemRepository problemRepository;

    @Test
    public void updateProblem() {
        Problem problem1 = problemRepository.findById(1L).orElseThrow(() -> new RuntimeException("Problem not found"));
        problem1.setDescription("Given two numbers, write a program to add them and return the result");
        problemRepository.save(problem1);

        Problem problem2 = problemRepository.findById(2L).orElseThrow(() -> new RuntimeException("Problem not found"));
        problem2.setDescription("Given an array of numbers, write a program to find the maximum number in the array");
        problemRepository.save(problem2);

        Problem problem3 = problemRepository.findById(3L).orElseThrow(() -> new RuntimeException("Problem not found"));
        problem3.setDescription("Given an array of numbers, write a program to sort the numbers in ascending order");
        problemRepository.save(problem3);
    }
}

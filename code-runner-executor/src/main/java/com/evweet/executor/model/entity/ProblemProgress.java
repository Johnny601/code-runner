package com.evweet.executor.model.entity;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "problem_progress")
public class ProblemProgress {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private String problemIds;
}

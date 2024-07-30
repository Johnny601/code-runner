package com.evweet.executor.model.entity;

import com.evweet.executor.enums.ProblemDifficulty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "problem")
public class Problem {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String pathname;
    private String name;
    private String description;
    private ProblemDifficulty difficulty;
}

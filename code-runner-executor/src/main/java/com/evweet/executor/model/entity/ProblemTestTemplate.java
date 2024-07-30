package com.evweet.executor.model.entity;

import com.evweet.executor.enums.ProgLang;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Blob;

@Data
@Entity
@Table(name = "problem_test_template")
public class ProblemTestTemplate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private ProgLang progLang;

    @Lob
    @Column(name = "test_template_file", length = 16777215)
    private byte[] testTemplateFile;
}

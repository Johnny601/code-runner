package com.evweet.executor.dao;

import com.evweet.executor.enums.ProgLang;
import com.evweet.executor.model.entity.ProblemTestTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProblemTestTemplateRepository extends JpaRepository<ProblemTestTemplate, Long> {
    ProblemTestTemplate findByProgLang(ProgLang progLang);
}

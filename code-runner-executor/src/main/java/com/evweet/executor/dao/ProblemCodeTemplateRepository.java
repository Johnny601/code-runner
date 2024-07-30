package com.evweet.executor.dao;

import com.evweet.executor.enums.ProgLang;
import com.evweet.executor.model.dto.ProblemCodeTemplateDto;
import com.evweet.executor.model.entity.ProblemCodeTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProblemCodeTemplateRepository extends JpaRepository<ProblemCodeTemplate, Long> {
    @Query("select new com.evweet.executor.model.dto.ProblemCodeTemplateDto(p.id, p.name, p.description, pc.progLang, pc.codeTemplate)" +
            "from Problem p inner join ProblemCodeTemplate pc on p.id = pc.problemId where p.pathname = :pathname and pc.progLang = :progLang"
           )
    ProblemCodeTemplateDto findByProblemNameAndProgLang(String pathname, ProgLang progLang);
}

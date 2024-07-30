package com.evweet.executor.service.impl;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.evweet.executor.dao.ProblemCodeTemplateRepository;
import com.evweet.executor.dao.ProblemProgressRepo;
import com.evweet.executor.dao.ProblemRepository;
import com.evweet.executor.enums.ProgLang;
import com.evweet.executor.model.dto.ProblemCodeTemplateDto;
import com.evweet.executor.model.entity.Problem;
import com.evweet.executor.model.entity.ProblemProgress;
import com.evweet.executor.model.po.DemoProblem;
import com.evweet.executor.service.ProblemService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProblemServiceImpl implements ProblemService {
    @Autowired
    ProblemRepository problemRepository;
    @Autowired
    ProblemProgressRepo problemProgressRepo;
    @Autowired
    ProblemCodeTemplateRepository problemCodeTemplateRepository;

    @Override
    public List<DemoProblem> getDemoProblems(List<Long> Ids) {
        Long userId = Long.parseLong((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());

        // copy the problem details to a return object
        List<Problem> problems = problemRepository.findAllById(Ids);
        List<DemoProblem> demoProblems = new ArrayList<>();
        for (Problem problem : problems) {
            DemoProblem demoProblem = new DemoProblem();
            BeanUtils.copyProperties(problem, demoProblem);
            demoProblems.add(demoProblem);
        }

        BeanUtils.copyProperties(problems, demoProblems);


        ProblemProgress problemProgress = problemProgressRepo.findByUserId(userId).orElse(null);
        if (problemProgress == null) {
            return demoProblems;
        }

        List<Long> completedProblemIds = JSON.parseArray(problemProgress.getProblemIds(), Long.class);
        demoProblems.forEach(problem -> {
            if (completedProblemIds.contains(problem.getId())) {
                problem.setSolved(true);
            }
        });

        return demoProblems;
    }

    public ProblemCodeTemplateDto getProblemCodeTemplate(String problemName, ProgLang progLang) {
        return problemCodeTemplateRepository.findByProblemNameAndProgLang(problemName, progLang);
    }
}

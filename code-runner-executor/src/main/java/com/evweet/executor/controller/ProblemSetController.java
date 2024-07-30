package com.evweet.executor.controller;

import com.evweet.common.response.Response;
import com.evweet.common.result.ProblemExecutionResult;
import com.evweet.executor.enums.ProgLang;
import com.evweet.executor.model.dto.ProblemCodeTemplateDto;
import com.evweet.executor.model.dto.RunProblemSetCodeDto;
import com.evweet.executor.model.entity.Problem;
import com.evweet.executor.model.po.DemoProblem;
import com.evweet.executor.service.CodeExecutionService;
import com.evweet.executor.service.impl.ProblemServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/problemset")
public class ProblemSetController {
    @Autowired
    CodeExecutionService codeExecutionService;

    @Autowired
    ProblemServiceImpl problemService;

    @PostMapping("/run")
    public Response<ProblemExecutionResult> runProblemSetCode(@ModelAttribute RunProblemSetCodeDto dto) {
        Long problemId = dto.getProblemId();
        ProgLang progLang = ProgLang.valueOf(dto.getProgLang()) ;
        String codeToExecute = dto.getCodeToExecute();

        ProblemExecutionResult result = codeExecutionService.runProblemSetCode(problemId, progLang, codeToExecute);
        return Response.success(result);
    }

    @GetMapping
    public Response<List<DemoProblem>> getDemoProblems() {
        List<Long> ids = Arrays.asList(1L, 2L, 3L);
        List<DemoProblem> demoProblems = problemService.getDemoProblems(ids);

        return Response.success(demoProblems);
    }



    @GetMapping("/{problemName}/{progLanguage}")
    public Response<ProblemCodeTemplateDto> getProblemCodeTemplate(@PathVariable String problemName, @PathVariable String progLanguage){
        ProgLang progLang = ProgLang.valueOf(progLanguage);
        ProblemCodeTemplateDto problemCodeTemplateDto = problemService.getProblemCodeTemplate(problemName, progLang);

        return Response.success(problemCodeTemplateDto);
    }
}

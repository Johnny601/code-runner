package com.evweet.executor.controller;

import com.evweet.common.response.Response;
import com.evweet.executor.enums.ProgLang;
import com.evweet.executor.model.dto.RunPlaygroundCodeDto;
import com.evweet.executor.model.dto.RunProblemSetCodeDto;
import com.evweet.executor.service.CodeExecutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/problemset")
public class ProblemSetController {
    @Autowired
    CodeExecutionService codeExecutionService;

    @PostMapping("/run")
    public Response<String> runPlaygroundCode(@ModelAttribute RunProblemSetCodeDto dto) {
        String problemName = dto.getProblemName();
        ProgLang progLang = ProgLang.valueOf(dto.getProgLang()) ;
        String codeToExecute = dto.getCodeToExecute();

        String result = codeExecutionService.runProblemSetCode(problemName, progLang, codeToExecute);
        return Response.success(result);
    }
}

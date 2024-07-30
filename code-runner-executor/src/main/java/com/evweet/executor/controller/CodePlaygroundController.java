package com.evweet.executor.controller;

import com.evweet.common.response.Response;
import com.evweet.common.result.PlaygroundExecutionResult;
import com.evweet.executor.enums.ProgLang;
import com.evweet.executor.model.dto.RunPlaygroundCodeDto;
import com.evweet.executor.service.CodeExecutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/playground")
public class CodePlaygroundController {
    @Autowired
    CodeExecutionService codeExecutionService;

    @PostMapping("/run")
    public Response<PlaygroundExecutionResult> runPlaygroundCode(@ModelAttribute RunPlaygroundCodeDto dto) {
        ProgLang progLang = ProgLang.valueOf(dto.getProgLang()) ;
        String codeToExecute = dto.getCodeToExecute();
        SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        PlaygroundExecutionResult result = codeExecutionService.runPlaygroundCode(progLang, codeToExecute);
        return Response.success(result);
    }
}

package com.evweet.executor.controller;

import com.evweet.executor.enums.ProgLang;
import com.evweet.executor.model.dto.RunPlaygroundCodeDto;
import com.evweet.executor.service.CodeExecutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentMap;

@RestController
@RequestMapping("/run")
public class CodePlaygroundController {
    @Autowired
    CodeExecutionService codeExecutionService;

    @PostMapping("/java")
    public String runPlaygroundCode(@ModelAttribute RunPlaygroundCodeDto dto) throws IOException {
        ProgLang progLang = ProgLang.valueOf(dto.getProgLang()) ;
        String codeToExecute = dto.getCodeToExecute();
//        List<String> arr = new ArrayList();
//        arr.add("Yes");
//        System.out.println(arr);
        return codeExecutionService.runCode(progLang, codeToExecute);
    }

    @PostMapping("/python")
    public String runPythonCode(String code) throws IOException {
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command("python", "--version");
        Process p = processBuilder.start();
        String output = new String(p.getInputStream().readAllBytes());
        return "Python, hello World!";
    }
}

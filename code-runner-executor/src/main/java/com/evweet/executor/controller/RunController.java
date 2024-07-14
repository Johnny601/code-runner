package com.evweet.executor.controller;

import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/run")
public class RunController {

    @PostMapping("/java")
    public String runJavaCode(String code) throws IOException {
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command("java", "--version");
        Process p = processBuilder.start();
        String output = new String(p.getInputStream().readAllBytes());
        return "Java, hello World!";
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

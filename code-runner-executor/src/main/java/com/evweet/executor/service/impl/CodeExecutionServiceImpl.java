package com.evweet.executor.service.impl;

import com.evweet.executor.enums.ProgLang;
import com.evweet.executor.service.CodeExecutionService;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class CodeExecutionServiceImpl implements CodeExecutionService {
    @Override
    public String runCode(ProgLang progLang, String codeToExecute) {
        String progLangName = progLang.getName();
        String executable = progLang.getExecutable();
        String fileExtension = progLang.getFileExtension();

        // create temporary file
        File tempFile = null;

        try {
            tempFile = File.createTempFile(progLangName, fileExtension);
        } catch (Exception e) {
            throw new Error("Failed to create temp file", e);
        }

        // write code to temporary file
        try (FileWriter fileWriter = new FileWriter(tempFile)) {
            fileWriter.write(codeToExecute);
            fileWriter.flush();
        } catch (Exception e) {
            throw new Error("Failed to write code to temp file", e);
        }

        // execute the code in the file
        List<String> command = new ArrayList<>();
        command.add(executable);
        command.add(tempFile.getAbsolutePath());
        ProcessBuilder processBuilder = new ProcessBuilder(command);
        processBuilder.redirectErrorStream(true);
        try {
            Process p = processBuilder.start();
            return new String(p.getInputStream().readAllBytes());
        } catch (IOException e) {
            throw new Error("Failed to run process", e);
        } finally {
            tempFile.delete();
        }
    }
}

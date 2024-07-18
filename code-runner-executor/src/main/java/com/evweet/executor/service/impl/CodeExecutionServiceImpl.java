package com.evweet.executor.service.impl;

import com.evweet.common.result.PlaygroundExecutionResult;
import com.evweet.executor.enums.ExitCode;
import com.evweet.executor.enums.ProgLang;
import com.evweet.executor.service.CodeExecutionService;
import com.evweet.executor.thread.ExecutorServiceHolder;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;

@Service
public class CodeExecutionServiceImpl implements CodeExecutionService {
    @Override
    public PlaygroundExecutionResult runPlaygroundCode(ProgLang progLang, String codeToExecute) {
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
            // read the data
            byte[] bytes = p.getInputStream().readAllBytes();

            if (p.exitValue() == ExitCode.SUCCESS.getCode()) {
                // execution succeeded
                return new PlaygroundExecutionResult(1, new String(bytes));
            } else {
                // execution failed
                return new PlaygroundExecutionResult(0, new String(bytes));
            }
        } catch (IOException e) {
            throw new Error("Failed to run process", e);
        } finally {
            tempFile.delete();
        }
    }

    @Override
    public String runProblemSetCode(String problemName, ProgLang progLang, String codeToExecute) {
        String progLangName = progLang.getName();
        String executable = progLang.getExecutable();
        String fileExtension = progLang.getFileExtension();

        String userId = "17723468";

        File fileToExecute = new File(String.format("C:\\Users\\Johnny\\Desktop\\Projects\\Personal\\code-runner\\code\\problemset-test\\problem\\%s\\%s\\user-answer\\%s%s", problemName, progLangName, userId, fileExtension));
        // provide code to test the user code
        File templateFile = new File(String.format("C:\\Users\\Johnny\\Desktop\\Projects\\Personal\\code-runner\\code\\problemset-test\\test-template\\%s-template-code%s", progLangName, fileExtension));
        try (
                FileWriter fileWriter = new FileWriter(fileToExecute);
                FileReader fileReader = new FileReader(templateFile)
        ) {
            // write user code to the file
            fileWriter.write(codeToExecute);

            // write test code to the file
            char[] buffer = new char[(int) templateFile.length()];
            fileReader.read(buffer);
            fileWriter.write("\n");
            fileWriter.write(buffer);

            fileWriter.flush();
        } catch (Exception e) {
            throw new Error("Failed to write code to the file", e);
        }

        String testCaseFolderPath = String.format("C:\\Users\\Johnny\\Desktop\\Projects\\Personal\\code-runner\\code\\problemset-test\\problem\\%s\\%s\\test-case", problemName, progLangName);
        File testCaseFolder = new File(testCaseFolderPath);
        // get all files in the test case folder
        String[] fileList = testCaseFolder.list();
        if (fileList == null || fileList.length == 0) {
            throw new Error("Failed to get test case files");
        }
        List<String> loopFileList = Arrays.asList(fileList);
        // get the thread pool
        ExecutorService executor = ExecutorServiceHolder.getExecutor();

        loopFileList.forEach((testCaseFile -> {
            executor.execute(() -> {
                int strLength = testCaseFile.length();
                char lastChar = testCaseFile.charAt(strLength - 1);
                System.out.println(Thread.currentThread().getId() + " is running test case " + lastChar);

                File file = new File(testCaseFolderPath + "\\test-case-" + lastChar);
                try {
                    BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
                    System.out.println(Thread.currentThread().getId() + " is running test case " + lastChar + ": " + bufferedReader.readLine());
                    System.out.println(Thread.currentThread().getId() + " is running test case " + lastChar + ": " + bufferedReader.readLine());
                } catch (Exception e) {
                    throw new Error("Failed to run test case", e);
                }
            });
        }));






        // execute the code in the file
//        List<String> command = new ArrayList<>();
//        command.add(executable);
//        command.add(fileToExecute.getAbsolutePath());
//        command.add("2");
//
//
//        ProcessBuilder processBuilder = new ProcessBuilder(command);
//        processBuilder.redirectErrorStream(true);
//        try {
//            Process p = processBuilder.start();
//            InputStream inputStream = p.getInputStream();
//            // read the data
//            byte[] bytes = p.getInputStream().readAllBytes();
//
//            if (p.exitValue() == ExitCode.SUCCESS.getCode()) {
//                // execution succeeded
//                return new String(bytes);
//            } else {
//                // execution failed
//                return new String(bytes);
//            }
//        } catch (IOException e) {
//            throw new Error("Failed to run process", e);
//        }
        return null;
    }
}

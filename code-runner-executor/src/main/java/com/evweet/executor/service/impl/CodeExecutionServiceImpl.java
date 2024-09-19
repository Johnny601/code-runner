package com.evweet.executor.service.impl;

import com.alibaba.fastjson2.JSON;
import com.evweet.common.result.PlaygroundExecutionResult;
import com.evweet.common.result.ProblemExecutionResult;
import com.evweet.common.result.TestCaseResult;
import com.evweet.executor.dao.ProblemProgressRepo;
import com.evweet.executor.dao.ProblemTestCaseRepository;
import com.evweet.executor.dao.ProblemTestTemplateRepository;
import com.evweet.executor.enums.ExitCode;
import com.evweet.executor.enums.ProgLang;
import com.evweet.executor.json.TestCaseObject;
import com.evweet.executor.model.entity.ProblemProgress;
import com.evweet.executor.model.entity.ProblemTestCase;
import com.evweet.executor.model.entity.ProblemTestTemplate;
import com.evweet.executor.service.CodeExecutionService;
import com.evweet.executor.thread.ExecutorServiceHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class CodeExecutionServiceImpl implements CodeExecutionService {
    @Autowired
    ProblemTestTemplateRepository problemTestTemplateRepository;

    @Autowired
    ProblemTestCaseRepository problemTestCaseRepository;

    @Autowired
    ProblemProgressRepo problemProgressRepo;

    @Override
    public PlaygroundExecutionResult runPlaygroundCode(ProgLang progLang, String codeToExecute) {
        String progLangName = progLang.getName();
        String executable = progLang.getExecutable();
        String fileExtension = progLang.getFileExtension();

        // create temporary file
        File tempFile = null;

        try {
            tempFile = File.createTempFile(progLangName + "-", fileExtension);
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
            p.waitFor(4, TimeUnit.SECONDS);

            if (p.exitValue() == ExitCode.SUCCESS.getCode()) {
                // execution succeeded
                return new PlaygroundExecutionResult(1, new String(bytes));
            } else {
                // execution failed
                return new PlaygroundExecutionResult(0, new String(bytes));
            }
        } catch (Exception e) {
            throw new Error("Failed to run process", e);
        } finally {
            tempFile.delete();
        }
    }

    @Override
    public ProblemExecutionResult runProblemSetCode(Long problemId, ProgLang progLang, String codeToExecute) {
        String progLangName = progLang.getName();
        String executable = progLang.getExecutable();
        String fileExtension = progLang.getFileExtension();

        String userId = "17723468";

        // create temp file for code execution
        File fileToExecute;
        try {
            fileToExecute = File.createTempFile(progLangName + "-", fileExtension);
        } catch (Exception e) {
            throw new Error("Failed to create temp file", e);
        }

        // write code to the execution temp file
        handleFileBeforeExecution(progLang, fileToExecute, codeToExecute);

        // get test case
        ProblemTestCase testCases = problemTestCaseRepository.findByProblemId(problemId);
        String testCaseString = testCases.getTestCaseString();
        List<TestCaseObject> testCaseObjects = JSON.parseArray(testCaseString, TestCaseObject.class);
        int testCaseCount = testCaseObjects.size();

        // get the thread pool
        ExecutorService executor = ExecutorServiceHolder.getExecutor();

        ProblemExecutionResult problemExecutionResult = new ProblemExecutionResult();
        problemExecutionResult.init(testCaseCount);
        // create a counter for thread
        CountDownLatch countDownLatch = new CountDownLatch(testCaseCount);
        testCaseObjects.forEach((testCaseObj -> {
            executor.execute(() -> {
                Integer index = testCaseObj.getIndex();
                System.out.println(Thread.currentThread().getId() + " is running test case " + index);

                try {
                    String[] inputArguments = testCaseObj.getInputArguments().split(" ");
                    String expectedResult = testCaseObj.getExpectedResult();

                    // construct the command to execute
                    List<String> command = new ArrayList<>();
                    command.add(executable);
                    command.add(fileToExecute.getAbsolutePath());
                    command.addAll(Arrays.asList(inputArguments));
                    // execute the command
                    ProcessBuilder processBuilder = new ProcessBuilder(command);
                    processBuilder.redirectErrorStream(true);

                    Process p = processBuilder.start();
                    // read the data
                    byte[] bytes = p.getInputStream().readAllBytes();
                    p.waitFor(4, TimeUnit.SECONDS);

                    TestCaseResult testCaseResult = new TestCaseResult();
                    testCaseResult.setInputArguments(String.join(" ", inputArguments));
                    testCaseResult.setExpectedResult(expectedResult);
                    // set the execution result of test case
                    String executionResult = new String(bytes);
                    if (p.exitValue() == ExitCode.SUCCESS.getCode()) {
                        // execution succeeded
                        testCaseResult.setCode(1);
                        executionResult = executionResult.replace("\r", "").replace("\n", "");
                        if (expectedResult.equals(executionResult))
                            // test case passed
                            testCaseResult.setPassed(true);
                    }
                    testCaseResult.setExecutionResult(executionResult);

                    synchronized (problemExecutionResult) {
                        if (testCaseResult.getPassed())
                            problemExecutionResult.setCorrectTestCases(problemExecutionResult.getCorrectTestCases() + 1);
                        problemExecutionResult.setTestCaseResult(index, testCaseResult);
                    }
                } catch (Exception e) {
                    throw new Error("Failed to run test case", e);
                } finally {
                    countDownLatch.countDown();
                }
            });
        }));

        try {
            countDownLatch.await(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new Error("Failed to wait for test cases to finish", e);
        }

        saveUserExecutionResult(problemId, problemExecutionResult);

        return problemExecutionResult;
    }

    private void handleFileBeforeExecution(ProgLang progLang, File fileToExecute, String codeToExecute) {
        try (
                FileWriter fileWriter = new FileWriter(fileToExecute);
        ) {
            ProblemTestTemplate testTemplate = problemTestTemplateRepository.findByProgLang(progLang);
            switch (progLang) {
                case PYTHON:
                    // write user code to the file
                    fileWriter.write(codeToExecute);
                    fileWriter.write("\n");
                    fileWriter.write(new String(testTemplate.getTestTemplateFile()));
                    fileWriter.flush();
                    break;
                case JAVA:
                    fileWriter.write(new String(testTemplate.getTestTemplateFile()));
                    fileWriter.write("\n");
                    // write user code to the file
                    fileWriter.write(codeToExecute);
                    fileWriter.flush();
                    break;
            }
        } catch (Exception e) {
            throw new Error("Failed to write code to the file", e);
        }
    }

    private void saveUserExecutionResult(Long problemId, ProblemExecutionResult problemExecutionResult) {
        Integer correctTestCases = problemExecutionResult.getCorrectTestCases();
        Integer totalTestCases = problemExecutionResult.getTestCaseResults().size();

        // all test cases passed
        if (correctTestCases.equals(totalTestCases)) {
            // save the user execution result
            Long userId = Long.parseLong((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
            ProblemProgress problemProgress = problemProgressRepo.findByUserId(userId).orElse(null);
            List<Long> completedProblemIds;
            if (problemProgress == null) {
                // create a new row in the table
                problemProgress = new ProblemProgress();
                problemProgress.setUserId(userId);
                completedProblemIds = new ArrayList<>();
                completedProblemIds.add(problemId);

            } else {
                String problemIds = problemProgress.getProblemIds();
                completedProblemIds = JSON.parseArray(problemIds, Long.class);
                completedProblemIds.add(problemId);
            }
            problemProgress.setProblemIds(JSON.toJSONString(completedProblemIds));
            problemProgressRepo.save(problemProgress);

        }
    }
}

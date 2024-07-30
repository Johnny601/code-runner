package com.evweet.executor.test;

import com.evweet.executor.dao.ProblemCodeTemplateRepository;
import com.evweet.executor.dao.ProblemRepository;
import com.evweet.executor.enums.ProblemDifficulty;
import com.evweet.executor.enums.ProgLang;
import com.evweet.executor.model.dto.ProblemCodeTemplateDto;
import com.evweet.executor.model.entity.Problem;
import com.evweet.executor.model.entity.ProblemCodeTemplate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
class ExecutorApplicationTests {
    @Autowired
    ProblemRepository problemRepository;
    @Autowired
    ProblemCodeTemplateRepository problemCodeTemplateRepository;

    @Test
    public void runJavaCode() throws IOException {
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command("java", "C:\\Users\\Johnny\\Desktop\\Projects\\Personal\\HelloWorld.java");
        Process p = processBuilder.start();
        String output = new String(p.getInputStream().readAllBytes());
        System.out.println(output);
    }

    @Test
    public void saveDemoProblem() {
        Problem problem = new Problem();
        problem.setPathname("sum-of-numbers");
        problem.setName("Sum of Numbers");
        problem.setDifficulty(ProblemDifficulty.EASY);

        Problem problem2 = new Problem();
        problem2.setPathname("maximum-number");
        problem2.setName("Maximum Number");
        problem2.setDifficulty(ProblemDifficulty.MEDIUM);

        Problem problem3 = new Problem();
        problem3.setPathname("sort-numbers");
        problem3.setName("Sort Numbers");
        problem3.setDifficulty(ProblemDifficulty.HARD);

        List<Problem> problems = new ArrayList<>();
        problems.add(problem);
        problems.add(problem2);
        problems.add(problem3);
        problemRepository.saveAll(problems);
    }

    @Test
    public void saveCodeTemplate() {
        List<Long> problemIds = Arrays.asList(1L, 2L, 3L);
        List<Problem> problems = problemRepository.findAllById(problemIds);

        Problem demoProblem1 = problems.get(0);
        ProblemCodeTemplate problemCodeTemplate1 = new ProblemCodeTemplate();
        problemCodeTemplate1.setProblemId(demoProblem1.getId());
        problemCodeTemplate1.setProgLang(ProgLang.PYTHON);
        problemCodeTemplate1.setCodeTemplate(
                "class Solution:\n" +
                        "    def sum_of_numbers(self, a: int, b: int) -> int:\n" +
                        "        return a + b");

        Problem demoProblem2 = problems.get(1);
        ProblemCodeTemplate problemCodeTemplate2 = new ProblemCodeTemplate();
        problemCodeTemplate2.setProblemId(demoProblem2.getId());
        problemCodeTemplate2.setProgLang(ProgLang.PYTHON);
        problemCodeTemplate2.setCodeTemplate(
                "class Solution:\n" +
                        "    def maximum_number(self, a: int, b: int) -> int:\n" +
                        "        return a + b");

        Problem demoProblem3 = problems.get(2);
        ProblemCodeTemplate problemCodeTemplate3 = new ProblemCodeTemplate();
        problemCodeTemplate3.setProblemId(demoProblem3.getId());
        problemCodeTemplate3.setProgLang(ProgLang.PYTHON);
        problemCodeTemplate3.setCodeTemplate(
                "class Solution:\n" +
                        "    def sort_numbers(self, a: int, b: int) -> int:\n" +
                        "        return a + b");

        List<ProblemCodeTemplate> problemCodeTemplates = new ArrayList<>();
        problemCodeTemplates.add(problemCodeTemplate1);
        problemCodeTemplates.add(problemCodeTemplate2);
        problemCodeTemplates.add(problemCodeTemplate3);
        problemCodeTemplateRepository.saveAll(problemCodeTemplates);

    }

    @Test
    public void saveCodeTemplate2() {
        List<Long> problemIds = Arrays.asList(1L, 2L, 3L);
        List<Problem> problems = problemRepository.findAllById(problemIds);

//        Problem demoProblem1 = problems.get(0);
//        ProblemCodeTemplate problemCodeTemplate1 = new ProblemCodeTemplate();
//        problemCodeTemplate1.setProblemId(demoProblem1.getId());
//        problemCodeTemplate1.setProgLang(ProgLang.JAVA);
//        problemCodeTemplate1.setCodeTemplate(
//                "class Solution {\n" +
//                        "    public int sumoOfNumbers(int a, int b) {\n" +
//                        "        return a + b;\n" +
//                        "    }\n" +
//                        "}");

        Problem demoProblem2 = problems.get(1);
        ProblemCodeTemplate problemCodeTemplate2 = new ProblemCodeTemplate();
        problemCodeTemplate2.setProblemId(demoProblem2.getId());
        problemCodeTemplate2.setProgLang(ProgLang.JAVA);
        problemCodeTemplate2.setCodeTemplate(
                "class Solution {\n" +
                        "    public int maximumNumber(int[] nums) {\n" +
                        "        int max = 0;\n" +
                        "        for (int num : nums) {\n" +
                        "            if (num > max) {\n" +
                        "                max = num;\n" +
                        "            }\n" +
                        "        }\n" +
                        "        return max;\n" +
                        "    }\n" +
                        "}");

        Problem demoProblem3 = problems.get(2);
        ProblemCodeTemplate problemCodeTemplate3 = new ProblemCodeTemplate();
        problemCodeTemplate3.setProblemId(demoProblem3.getId());
        problemCodeTemplate3.setProgLang(ProgLang.JAVA);
        problemCodeTemplate3.setCodeTemplate(
                "public int[] sortNumbers(int[] nums) {\n" +
                        "        int[] sorted = nums.clone();\n" +
                        "        Arrays.sort(sorted);\n" +
                        "        return sorted;\n" +
                        "    }");

        List<ProblemCodeTemplate> problemCodeTemplates = new ArrayList<>();
//        problemCodeTemplates.add(problemCodeTemplate1);
        problemCodeTemplates.add(problemCodeTemplate2);
        problemCodeTemplates.add(problemCodeTemplate3);
        problemCodeTemplateRepository.saveAll(problemCodeTemplates);

    }


    @Test
    public void findByProblemNameAndProgLang() {
        ProblemCodeTemplateDto problemCodeTemplateDto = problemCodeTemplateRepository.findByProblemNameAndProgLang("sum-of-numbers", ProgLang.PYTHON);
        System.out.println(problemCodeTemplateDto);
    }



}

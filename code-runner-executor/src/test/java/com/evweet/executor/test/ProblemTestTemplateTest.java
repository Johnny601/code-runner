package com.evweet.executor.test;

import com.evweet.executor.dao.ProblemCodeTemplateRepository;
import com.evweet.executor.dao.ProblemTestTemplateRepository;
import com.evweet.executor.enums.ProgLang;
import com.evweet.executor.model.entity.ProblemTestTemplate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

@SpringBootTest
public class ProblemTestTemplateTest {
    @Autowired
    ProblemTestTemplateRepository problemTestTemplateRepository;

    @Test
    public void saveTestTemplate() throws FileNotFoundException, IOException {
        ProblemTestTemplate problemTestTemplate = new ProblemTestTemplate();
        problemTestTemplate.setProgLang(ProgLang.PYTHON);

        File testTemplateFile = new File("C:\\Users\\Johnny\\Desktop\\Projects\\Personal\\code-runner\\code\\problemset-test\\test-template\\python-template-code.py");
        FileInputStream fileInputStream = new FileInputStream(testTemplateFile);
        problemTestTemplate.setTestTemplateFile(fileInputStream.readAllBytes());

        problemTestTemplateRepository.save(problemTestTemplate);
    }

    @Test
    public void saveTestTemplate2() throws FileNotFoundException, IOException {
        ProblemTestTemplate problemTestTemplate = new ProblemTestTemplate();
        problemTestTemplate.setProgLang(ProgLang.JAVA);

        File testTemplateFile = new File("C:\\Users\\Johnny\\Desktop\\Projects\\Personal\\code-runner\\code\\problemset-test\\test-template\\python-template-code.py");
        FileInputStream fileInputStream = new FileInputStream(testTemplateFile);
        problemTestTemplate.setTestTemplateFile(fileInputStream.readAllBytes());

        problemTestTemplateRepository.save(problemTestTemplate);
    }
}

package com.evweet.executor.test;

import com.alibaba.fastjson2.JSON;
import com.evweet.executor.dao.ProblemTestCaseRepository;
import com.evweet.executor.json.TestCaseObject;
import com.evweet.executor.model.entity.ProblemTestCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class ProblemTestCaseTest {
    @Autowired
    ProblemTestCaseRepository problemTestCaseRepository;

    @Test
    public void testParseJsonString() {
        ProblemTestCase testCase = problemTestCaseRepository.findByProblemId(1L);

        String testCaseString = testCase.getTestCaseString();

        List<TestCaseObject>testCaseObjects = JSON.parseArray(testCaseString, TestCaseObject.class);
        System.out.println(testCaseObjects);
    }

    @Test
    public void testSaveTestCase() {
        List<TestCaseObject> objects = new ArrayList<>();

        TestCaseObject object1 = new TestCaseObject();
        object1.setIndex(0);
        object1.setInputArguments("1 2");
        object1.setExpectedResult("3");

        TestCaseObject object2 = new TestCaseObject();
        object2.setIndex(1);
        object2.setInputArguments("3 4");
        object2.setExpectedResult("7");

        TestCaseObject object3 = new TestCaseObject();
        object3.setIndex(2);
        object3.setInputArguments("5 6");
        object3.setExpectedResult("11");

        objects.add(object1);
        objects.add(object2);
        objects.add(object3);

        String jsonString = JSON.toJSONString(objects);

        ProblemTestCase problemTestCase = new ProblemTestCase();
        problemTestCase.setProblemId(1L);
        problemTestCase.setTestCaseString(jsonString);
        problemTestCaseRepository.save(problemTestCase);


    }

    @Test
    public void testSaveTestCase2() {
        List<TestCaseObject> objects = new ArrayList<>();

        TestCaseObject object1 = new TestCaseObject();
        object1.setIndex(0);
        object1.setInputArguments("1,2,3");
        object1.setExpectedResult("3");

        TestCaseObject object2 = new TestCaseObject();
        object2.setIndex(1);
        object2.setInputArguments("3,2,1");
        object2.setExpectedResult("3");

        TestCaseObject object3 = new TestCaseObject();
        object3.setIndex(2);
        object3.setInputArguments("1,3,2");
        object3.setExpectedResult("3");

        objects.add(object1);
        objects.add(object2);
        objects.add(object3);

        String jsonString = JSON.toJSONString(objects);

        ProblemTestCase problemTestCase = new ProblemTestCase();
        problemTestCase.setProblemId(2L);
        problemTestCase.setTestCaseString(jsonString);
        problemTestCaseRepository.save(problemTestCase);


    }

    @Test
    public void testSaveTestCase3() {
        List<TestCaseObject> objects = new ArrayList<>();

        TestCaseObject object1 = new TestCaseObject();
        object1.setIndex(0);
        object1.setInputArguments("3,2,1");
        object1.setExpectedResult("[1, 2, 3]");

        TestCaseObject object2 = new TestCaseObject();
        object2.setIndex(1);
        object2.setInputArguments("10,9,2,5,3,7,101,18");
        object2.setExpectedResult("[2, 3, 5, 7, 9, 10, 18, 101]");

        TestCaseObject object3 = new TestCaseObject();
        object3.setIndex(2);
        object3.setInputArguments("0,1,0,3,2,3");
        object3.setExpectedResult("[0, 0, 1, 2, 3, 3]");

        objects.add(object1);
        objects.add(object2);
        objects.add(object3);

        String jsonString = JSON.toJSONString(objects);

        ProblemTestCase problemTestCase = new ProblemTestCase();
        problemTestCase.setProblemId(3L);
        problemTestCase.setTestCaseString(jsonString);
        problemTestCaseRepository.save(problemTestCase);


    }
}

package com.evweet.executor;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileOutputStream;
import java.io.IOException;

@SpringBootTest
class ExecutorApplicationTests {

    @Test
    public void runJavaCode() throws IOException {
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command("java", "C:\\Users\\Johnny\\Desktop\\Projects\\Personal\\HelloWorld.java");
        Process p = processBuilder.start();
        String output = new String(p.getInputStream().readAllBytes());
        System.out.println(output);
    }

}

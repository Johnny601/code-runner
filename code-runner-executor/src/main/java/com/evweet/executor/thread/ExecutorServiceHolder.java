package com.evweet.executor.thread;

import lombok.Getter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class ExecutorServiceHolder {
    @Getter
    private static final ExecutorService executor = Executors.newFixedThreadPool(3);
}

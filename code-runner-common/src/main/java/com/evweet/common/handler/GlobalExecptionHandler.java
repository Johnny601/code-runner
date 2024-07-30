package com.evweet.common.handler;

import com.evweet.common.exception.BaseException;
import com.evweet.common.response.Response;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExecptionHandler {
    @ExceptionHandler
    public Response handleException(BaseException e) {
        return Response.error(e.getMessage());
    }
}

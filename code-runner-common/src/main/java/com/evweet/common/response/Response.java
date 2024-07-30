package com.evweet.common.response;

import lombok.Data;

import java.io.Serializable;

@Data
public class Response<T> implements Serializable {

    private Integer code; //编码：1成功，0和其它数字为失败
    private String message; //错误信息
    private T data; //数据
    public static <T> T getData(T dataset) {
        return dataset;
    }

    public static <E> Response<E> success() {
        Response<E> response = new Response<>();
        response.code = 1;
        return response;
    }

    public static <E> Response<E> success(E object) {
        Response<E> response = new Response<>();
        response.data = object;
        response.code = 1;
        return response;
    }

    public static <E> Response<E> error(String message) {
        Response<E> response = new Response<>();
        response.message = message;
        response.code = 0;
        return response;
    }

}

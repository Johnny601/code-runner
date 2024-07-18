package com.evweet.common.result;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class PlaygroundExecutionResult implements Serializable {
    private Integer code;
    private String content;
}

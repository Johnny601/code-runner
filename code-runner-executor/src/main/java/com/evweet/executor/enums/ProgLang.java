package com.evweet.executor.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProgLang {
    JAVA("java","java", ".java"),
    PYTHON("python","python", ".py");

    final String name;
    final String executable;
    final String fileExtension;
}

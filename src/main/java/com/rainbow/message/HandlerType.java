package com.rainbow.message;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum HandlerType {

    REGEX(1, "正则"),
    LIKE(2, "模糊查询");

    private final Integer code;

    private final String message;


}

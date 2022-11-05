package com.rainbow.message.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Type {

    EAT(1, "吃饭"),
    SNACKS(2, "零食"),
    FRUIT(3, "水果"),
    CLOTHES(4, "衣服"),
    DAILY(5, "生活用品"),
    WAGES(6, "工资");

    private final Integer code;

    private final String message;

    public static Type get(String message) {
        for (Type type : Type.values()) {
            if (type.getMessage().equals(message)) {
                return type;
            }
        }

        throw new RuntimeException("未找到相关的类型");
    }
}

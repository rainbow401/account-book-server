package com.rainbow.message.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EventType {

    SUBSCRIBE(1, "subscribe"),
    UNSUBSCRIBE(2, "unsubscribe");

    private final Integer code;

    private final String message;
}

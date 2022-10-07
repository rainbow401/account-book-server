package com.rainbow.exceptions;

public class MessageFormatException extends RuntimeException {

    private final String message = "message format error";

    public MessageFormatException() {
    }

    public MessageFormatException(String message) {
        super(message);
    }
}

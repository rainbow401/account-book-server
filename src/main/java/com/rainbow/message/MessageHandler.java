package com.rainbow.message;

public interface MessageHandler {

    void handler(String message);

    boolean checkPattern(String message);

    String getStrategy();
}

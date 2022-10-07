package com.rainbow.message;

import com.rainbow.message.handlers.AddHandler;

public class Test {

    public static void main(String[] args) {
        AddHandler addHandler = new AddHandler();
        addHandler.checkPattern("sss");
    }
}

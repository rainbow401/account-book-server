package com.rainbow.message.handlers;

import com.rainbow.message.MessageHandler;
import com.rainbow.wx.MessageParam;

public class DefaultMessageHandler implements MessageHandler {

    private final String[] strategyArray = new String[]{"default"};


    @Override
    public String handler(MessageParam param) {
        return "你好啊";
    }

    @Override
    public boolean checkPattern(String data) {
        return true;
    }

    @Override
    public String[] getTriggerArray() {
        return strategyArray;
    }

    @Override
    public boolean isDefault() {
        return true;
    }
}

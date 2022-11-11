package com.rainbow.message;

import com.rainbow.wx.MessageParam;

public interface MessageHandler {

    String handler(MessageParam param);

    default boolean checkPattern(String data) {
        return true;
    }

    String[] getTriggerArray();

    default boolean isDefault() {
        return false;
    }
}

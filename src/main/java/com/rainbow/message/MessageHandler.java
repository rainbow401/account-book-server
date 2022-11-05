package com.rainbow.message;

import com.rainbow.wx.MessageParam;

public interface MessageHandler {

    String handler(MessageParam param);

    boolean checkPattern(String data);

    String[] getStrategyArray();
}

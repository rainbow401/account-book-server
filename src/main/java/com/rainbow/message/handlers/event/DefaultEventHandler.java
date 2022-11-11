package com.rainbow.message.handlers.event;

import com.rainbow.entity.WeChatMessageDTO;
import com.rainbow.message.EventHandler;

public class DefaultEventHandler implements EventHandler {

    @Override
    public String[] getEvent() {
        return new String[0];
    }

    @Override
    public String handler(WeChatMessageDTO messageDTO) {
        return "你好";
    }

    @Override
    public boolean isDefault() {
        return true;
    }
}

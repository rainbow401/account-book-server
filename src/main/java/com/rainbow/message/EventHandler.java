package com.rainbow.message;

import com.rainbow.entity.WeChatMessageDTO;

public interface EventHandler {

    String[] getEvent();

    String handler(WeChatMessageDTO messageDTO);

    default boolean isDefault() {
        return false;
    }
}

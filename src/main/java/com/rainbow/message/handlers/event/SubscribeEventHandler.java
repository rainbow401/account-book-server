package com.rainbow.message.handlers.event;

import com.rainbow.entity.WeChatMessageDTO;
import com.rainbow.message.EventHandler;
import com.rainbow.message.enums.EventType;
import org.springframework.stereotype.Component;

@Component
public class SubscribeEventHandler implements EventHandler {

    private final String[] eventArray = new String[]{"subscribe", "unsubscribe"};

    @Override
    public String[] getEvent() {
        return eventArray;
    }

    @Override
    public String handler(WeChatMessageDTO dto) {
        String event = dto.getEvent();
        if (EventType.SUBSCRIBE.getMessage().equals(event)) {
            return "欢迎订阅";
        } else {
            return "再见";
        }
    }
}

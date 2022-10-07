package com.rainbow.message;

import com.rainbow.exceptions.MessageFormatException;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class MessageHandlerFactory implements ApplicationContextAware {

    private final Map<String, MessageHandler> messageHandlers = new HashMap<>();

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, MessageHandler> beansMap = applicationContext.getBeansOfType(MessageHandler.class);
        for (String e : beansMap.keySet()) {
            MessageHandler messageHandler = beansMap.get(e);
            messageHandlers.put(messageHandler.getStrategy(), messageHandler);
        }
    }

    public MessageHandler createHandler(String strategy, String message) {

        if (messageHandlers.containsKey(strategy)) {
            MessageHandler messageHandler = messageHandlers.get(strategy);
            if (!messageHandler.checkPattern(message)) {
                throw new MessageFormatException();
            }

            return messageHandler;
        }

        return null;
    }
}

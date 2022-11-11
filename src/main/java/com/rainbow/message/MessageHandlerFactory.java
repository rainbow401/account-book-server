package com.rainbow.message;

import com.rainbow.message.handlers.DefaultMessageHandler;
import com.rainbow.wx.MessageParam;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

@Component
public class MessageHandlerFactory implements ApplicationContextAware {

    private final Map<String, MessageHandler> messageHandlers = new HashMap<>();

    private final DefaultMessageHandler defaultMessageHandler = new DefaultMessageHandler();

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, MessageHandler> beansMap = applicationContext.getBeansOfType(MessageHandler.class);
        for (String e : beansMap.keySet()) {
            MessageHandler messageHandler = beansMap.get(e);
            if (!messageHandler.isDefault()) {
                String[] triggerArray = messageHandler.getTriggerArray();
                for (String trigger : triggerArray) {
                    if (!StringUtils.hasText(trigger)) {
                        throw new RuntimeException("策略不能为空，请为Handler设置触发条件Trigger");
                    }
                    messageHandlers.put(trigger, messageHandler);
                }
            }
        }
    }

    public MessageHandler createHandler(MessageParam param) {
        return messageHandlers.getOrDefault(param.getTrigger(), defaultMessageHandler);
    }
}

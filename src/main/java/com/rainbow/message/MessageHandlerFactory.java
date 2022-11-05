package com.rainbow.message;

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

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, MessageHandler> beansMap = applicationContext.getBeansOfType(MessageHandler.class);
        for (String e : beansMap.keySet()) {
            MessageHandler messageHandler = beansMap.get(e);
            String[] strategyArray = messageHandler.getStrategyArray();
            for (String strategy : strategyArray) {
                if (!StringUtils.hasText(strategy)) {
                    throw new RuntimeException("策略不能为空");
                }
                messageHandlers.put(strategy, messageHandler);
            }
        }
    }

    public MessageHandler createHandler(MessageParam param) {
        return messageHandlers.get(param.getStrategy());
    }
}

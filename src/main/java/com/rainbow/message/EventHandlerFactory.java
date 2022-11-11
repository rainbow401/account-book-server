package com.rainbow.message;

import com.rainbow.message.handlers.event.DefaultEventHandler;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class EventHandlerFactory implements ApplicationContextAware {

    private final Map<String, EventHandler> eventHandlerMap = new HashMap<>();

    private final DefaultEventHandler defaultEventHandler = new DefaultEventHandler();

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, EventHandler> beansMap = applicationContext.getBeansOfType(EventHandler.class);
        for (String e : beansMap.keySet()) {
            EventHandler eventHandler = beansMap.get(e);
            if (!eventHandler.isDefault()) {
                String[] events = eventHandler.getEvent();
                for (String event : events) {
                    eventHandlerMap.put(event, eventHandler);
                }
            }
        }
    }

    public EventHandler createEventHandler(String event) {
        return eventHandlerMap.getOrDefault(event, defaultEventHandler);
    }
}

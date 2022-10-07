package com.rainbow.message.handlers;

import com.rainbow.message.CommonMessageHandler;
import com.rainbow.message.MessageHandler;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class AddHandler extends CommonMessageHandler implements MessageHandler {

    public String pattern = "^(吃饭|零食|水果)\\s\\s[0-9]{1,9}(.{1}[0-9]{1,2})?$";

    @Override
    public boolean checkPattern(String message) {
        return regex(message, pattern);
    }

    @Override
    public String getStrategy() {
        return "吃饭";
    }

    @Override
    public void handler(String message) {
        // 存入数据库
    }

    public static void main(String[] args) {
        System.out.println(Pattern.matches("^(吃饭|零食|水果)\\s\\s[0-9]{1,9}(.{1}[0-9]{1,2})?$", "吃饭  1"));
    }
}

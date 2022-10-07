package com.rainbow.message;

import javax.validation.constraints.NotNull;
import java.util.regex.Pattern;

public class CommonMessageHandler {

    public boolean regex(@NotNull String message,@NotNull String pattern) {
        return Pattern.matches(pattern, message);
    }

    public boolean like(@NotNull String message, @NotNull String pattern) {
        Pattern p = Pattern.compile(pattern);
        return p.matcher(message).find();
    }
}

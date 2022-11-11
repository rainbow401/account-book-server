package com.rainbow.wx;

import lombok.Data;

@Data
public class MessageParam {

    private String content;

    private String trigger;

    private String data;

    private String memo;

    private String userid;

    private String event;
}

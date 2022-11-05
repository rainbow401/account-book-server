package com.rainbow.wx;

import lombok.Data;

@Data
public class MessageParam {

    private String content;

    private String strategy;

    private String data;

    private String memo;

    private String userid;
}

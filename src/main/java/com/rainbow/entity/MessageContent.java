package com.rainbow.entity;
import lombok.Data;

@Data
public class MessageContent {

    private String toUserName;

    private String fromUserName;

    private String createTime;

    private String msgType;

    private String content;

    private String msgId;

    private String msgDataId;

    private String Idx;

}
package com.rainbow.entity;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "xml")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class WeChatResponseMessage {

    /**
     * 接受者
     */
    @XmlElement(name = "ToUserName")
    private String toUserName;

    /**
     * 开发者id
     */
    @XmlElement(name = "FromUserName")
    private String fromUserName;

    /**
     * 消息创建时间 （整型）
     */
    @XmlElement(name = "CreateTime")
    private Long createTime;

    /**
     * 消息类型: {@link com.small.nine.wxmp.config.WxConfig} MSG_TYPE_*
     */
    @XmlElement(name = "MsgType")
    private String msgType;

    /**
     * 消息id，64位整型
     */
    @XmlElement(name = "MsgId")
    private Long msgId;

    /**
     * 文本消息内容
     */
    @XmlElement(name = "Content")
    private String content;
}

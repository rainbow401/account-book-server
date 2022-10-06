package com.rainbow.vo;

import lombok.Data;

@Data
public class WechatNotifyRequestVO {

    /**
     * 开发者微信号
     */
    private String toUserName;

    /**
     * 发送方帐号（一个OpenID）
     */
    private String fromUserName;

    /**
     * 消息创建时间（整型）
     */
    private Integer createTime;

    /**
     * 消息类型，event
     */
    private String messageType;

    /**
     * 事件类型，subscribe(订阅)、unsubscribe(取消订阅)
     */
    private String event;
}

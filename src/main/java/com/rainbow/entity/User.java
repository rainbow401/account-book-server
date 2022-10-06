package com.rainbow.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class User extends AutoIdEntity {

    private String openId;

    private String username;

    private String password;
}

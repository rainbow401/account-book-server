package com.rainbow.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserAccountLog extends AutoIdEntity{

    private Long userid;

    private BigDecimal amount;

    private String memo;

    private LocalDateTime time;

}

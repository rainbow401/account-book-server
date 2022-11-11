package com.rainbow.message.handlers.message;

import com.rainbow.exceptions.MessageFormatException;
import com.rainbow.message.CommonMessageHandler;
import com.rainbow.message.MessageHandler;
import com.rainbow.message.enums.Type;
import com.rainbow.wx.MessageParam;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@RequiredArgsConstructor
public class ReduceHandler extends CommonMessageHandler implements MessageHandler {

    public String pattern = "^[0-9]{1,9}(.{1}[0-9]{1,2})?$";

    private final String[] triggerArray = new String[]{"吃饭", "零食", "水果", "衣服", "生活用品", "房租", "电子设备", "其他"};

    private final JdbcTemplate jdbcTemplate;

    private final String ADD_SQL = "INSERT INTO user_account_log " +
            "(userid, amount, memo, type, create_by, create_time, update_by, update_time, deleted) " +
            "VALUES (?, ?, ?, ?, ?,DEFAULT, ?, DEFAULT, DEFAULT)";

    @Override
    public boolean checkPattern(String data) {
        return regex(data, pattern);
    }

    @Override
    public String[] getTriggerArray() {
        return triggerArray;
    }

    @Override
    public String handler(MessageParam param) {
        if (StringUtils.hasText(param.getData()) && checkPattern(param.getData())) {
            Type type = Type.get(param.getTrigger());
            jdbcTemplate.update(
                    ADD_SQL,
                    param.getUserid(),
                    -Double.parseDouble(param.getData()),
                    param.getMemo(), type.getCode(),
                    param.getUserid(),
                    param.getUserid()
            );
        } else {
            throw new MessageFormatException("数据格式为数字");
        }

        return String.format("操作成功 -%s", Double.parseDouble(param.getData()));
    }
}

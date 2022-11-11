package com.rainbow.wx;

import com.rainbow.config.AppProperties;
import com.rainbow.entity.WeChatMessageDTO;
import com.rainbow.entity.WeChatResponseMessage;
import com.rainbow.exceptions.MessageFormatException;
import com.rainbow.message.EventHandler;
import com.rainbow.message.EventHandlerFactory;
import com.rainbow.message.MessageHandler;
import com.rainbow.message.MessageHandlerFactory;
import com.rainbow.util.SignUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.DocumentException;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/wx")
public class MessageController {

    private final MessageHandlerFactory messageHandlerFactory;
    private final EventHandlerFactory eventHandlerFactory;

    private final AppProperties appProperties;

    /**
     * 微信公共号get请求，服务器安全性校验。
     */
    @GetMapping("/public/message")
    public void auth(HttpServletRequest request, HttpServletResponse response) {
        log.info("验证安全性 request: {}", request.toString());

        String echoStr = request.getParameter("echostr");
        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        log.info("echoStr: {}", echoStr);
        log.info("timestamp: {}", timestamp);
        log.info("nonce: {}", nonce);
        try {
            if (SignUtil.checkSignature(signature, timestamp, nonce, appProperties.getToken())) {
                PrintWriter out = response.getWriter();
                out.print(echoStr);
                out.close();
            } else {
                log.info("这里存在非法请求！");
            }

        } catch (Exception e) {
            log.error(e.toString());
        }
    }


    @PostMapping(value = "/public/message", produces = MediaType.TEXT_XML_VALUE)
    public WeChatResponseMessage handler(@RequestBody WeChatMessageDTO dto) throws IOException {
        log.info("接收到消息:{}", dto);
        String resultContent = null;

        if (StringUtils.hasText(dto.getEvent())) {
            resultContent = processEvent(dto);
        } else {
            resultContent = processMessage(dto);
        }

        WeChatResponseMessage responseMessage = new WeChatResponseMessage();
        responseMessage.setFromUserName(appProperties.getDeveloperId());
        responseMessage.setToUserName(dto.getFromUserName());
        responseMessage.setCreateTime(LocalDateTime.now().toEpochSecond(ZoneOffset.ofHours(8)));
        responseMessage.setMsgType("text");
        responseMessage.setMsgId(getRandomLong());
        responseMessage.setContent(resultContent);

        log.info("回复消息:{}", responseMessage);

        return responseMessage;
    }

    private String processEvent(WeChatMessageDTO dto) {
        String resultContent;
        EventHandler eventHandler = eventHandlerFactory.createEventHandler(dto.getEvent());
        resultContent = eventHandler.handler(dto);
        return resultContent;
    }

    private String processMessage(WeChatMessageDTO dto) {
        String resultContent;
        try {
            MessageParam param = convertParam(dto);
            MessageHandler messageHandler = messageHandlerFactory.createHandler(param);
            resultContent = messageHandler.handler(param);
        } catch (MessageFormatException e) {
            resultContent = e.getMessage();
        } catch (Exception e) {
            e.printStackTrace();
            resultContent = "请稍后再试";
        }
        return resultContent;
    }

    private Long getRandomLong() {
        return (long) (int) (Math.random() * 900000 + 100000);
    }

    private MessageParam convertParam(WeChatMessageDTO dto) throws DocumentException, IOException {
        log.info("parWeChatMessageDTO dto {}", dto);
        MessageParam param = new MessageParam();
        param.setUserid(dto.getFromUserName());
        param.setContent(dto.getContent());

        String[] itemArray = dto.getContent().split("！！");
        for (int i = 0; i < itemArray.length; i++) {
            switch (i) {
                case 0 -> {
                    param.setTrigger(itemArray[i]);
                }
                case 1 -> {
                    param.setData(itemArray[i]);
                }
                case 2 -> {
                    param.setMemo(itemArray[i]);
                }
                default -> {
                }
            }
        }
        return param;
    }
}

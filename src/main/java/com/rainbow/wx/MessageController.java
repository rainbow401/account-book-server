package com.rainbow.wx;

import com.rainbow.exceptions.MessageFormatException;
import com.rainbow.message.MessageHandler;
import com.rainbow.message.MessageHandlerFactory;
import com.rainbow.util.SignUtil;
import com.rainbow.util.XmlMapUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.DocumentException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/wx")
public class MessageController {

    private final MessageHandlerFactory messageHandlerFactory;

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

        try {
            if (SignUtil.checkSignature(signature, timestamp, nonce)) {
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


    @PostMapping("/public/message")
    public void handler(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/xml;charset=utf-8");
        PrintWriter out = response.getWriter();
        String result = "";
        try {
            MessageParam param = convertParam(request);
            MessageHandler messageHandler = messageHandlerFactory.createHandler(param);
            if (messageHandler == null) {
                throw new MessageFormatException("你说啥，我不懂");
            }

            result = messageHandler.handler(param);
        } catch (MessageFormatException e) {
            result = e.getMessage();
        } catch (Exception e) {
            e.printStackTrace();
            result = "请稍后再试";
        }

        log.info("result : {}", result);

        // 写入返回值
        out.print(result);
        out.close();
    }

    private MessageParam convertParam(HttpServletRequest request) throws DocumentException, IOException {
        // 拿到微信客户端发来的请求并通过工具类解析为map格式
        Map<String, String> map = XmlMapUtil.xmlToMap(request);
        String toUserName = map.get("ToUserName");
        String fromUserName = map.get("FromUserName");  //openid
        String msgType = map.get("MsgType");
        String content = map.get("Content");
        String event = map.get("event");
        log.info("param content: {}", map);

        MessageParam param = new MessageParam();
        param.setUserid(fromUserName);
        param.setContent(content);

        String[] itemArray = content.split("\\s\\s");
        for (int i = 0; i < itemArray.length; i++) {
            switch (i) {
                case 1 -> {
                    param.setStrategy(itemArray[i]);
                }
                case 2 -> {
                    param.setData(itemArray[i]);
                }
                case 3 -> {
                    param.setMemo(itemArray[i]);
                }
                default -> {
                }
            }
        }
        return param;
    }
}

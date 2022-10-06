package com.rainbow.wx;

import com.rainbow.util.SignUtil;
import com.rainbow.util.XmlMapUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/wx")
public class MessageController {

    /**
     * 微信公共号get请求，服务器安全性校验。
     */
    @GetMapping("/public/message")
    public void auth(HttpServletRequest request, HttpServletResponse response) {
        String echoStr = request.getParameter("echostr");
        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
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


    @GetMapping("/public/message")
    public void handler(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/xml;charset=utf-8");

        PrintWriter out = response.getWriter();
        try {
            // 拿到微信客户端发来的请求并通过工具类解析为map格式
            Map<String, String> map = XmlMapUtil.xmlToMap(request);
            String toUserName = map.get("ToUserName");
            String fromUserName = map.get("FromUserName");  //openid
            String msgType = map.get("MsgType");
            String content = map.get("Content");
            String event = map.get("event");
            System.out.println(fromUserName);
            if (event.equals("subscribe")) {
                System.out.println("用户关注了微信公众号, 这里写自己的业务逻辑.....");
            }

            if (event.equals("unsubscribe")) {
                log.info("用户取消关注公众号, 这里写自己的业务逻辑......");
                System.out.println("用户取消关注公众号, 这里写自己的业务逻辑......");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

package com.rainbow.util;

import com.rainbow.vo.WechatNotifyRequestVO;
import com.thoughtworks.xstream.XStream;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 实现消息的格式转换(Map类型和XML的互转)
 */
public class XmlMapUtil {
    /**
     * 将XML转换成Map集合
     */
    public static Map<String, String> xmlToMap(HttpServletRequest request)
            throws IOException, DocumentException {

        Map<String, String> map = new HashMap<String, String>();
        SAXReader reader = new SAXReader(); // 使用dom4j解析xml
        InputStream is= request.getInputStream(); // 从request中获取输入流
        Document doc = reader.read(is);

        Element root = doc.getRootElement(); // 获取根元素
        List<Element> list = root.elements(); // 获取所有节点

        for (Element e : list) {
            map.put(e.getName(), e.getText());
            System.out.println(e.getName() + "--->" + e.getText());
        }
        return map;
    }



//    /**
//     * 将XML转换成Map集合
//     */
//    public static Map<String, String> xmlToMap(HttpServletRequest request)
//            throws IOException, DocumentException {
//        String body = request.getParameter("body");
//        String encrypt_type = request.getParameter("encrypt_type");
//        String appid = request.getParameter("appid");
//        String token = request.getParameter("token");
//        String encodingaeskey = request.getParameter("encodingaeskey");
//        String post_type = request.getParameter("post_type");
//
//        Map<String, String> map = new HashMap<String, String>();
//        map.put("body", body);
//        map.put("encrypt_type", encrypt_type);
//        map.put("appid", appid);
//        map.put("token", token);
//        map.put("encodingaeskey", encodingaeskey);
//        map.put("post_type", post_type);
//
////        SAXReader reader = new SAXReader(); // 使用dom4j解析xml
////        InputStream is= request.getInputStream(); // 从request中获取输入流
////        Document doc = reader.read(is);
////        Element root = doc.getRootElement(); // 获取根元素
////        List<Element> list = root.elements(); // 获取所有节点
////
////        for (Element e : list) {
////            map.put(e.getName(), e.getText());
////            System.out.println(e.getName() + "--->" + e.getText());
////        }
////        is.close();
//
//        Document document = DocumentHelper.parseText(body);
//        Element root = document.getRootElement(); // 获取根元素
//        List<Element> list = root.elements(); // 获取所有节点
//
//        for (Element e : list) {
//            map.put(e.getName(), e.getText());
//            System.out.println(e.getName() + "--->" + e.getText());
//        }
//        return map;
//    }
    /**
     * 将文本消息对象转换成XML
     */
    public static String textMessageToXML(WechatNotifyRequestVO textMessage) {
        XStream xstream = new XStream(); // 使用XStream将实体类的实例转换成xml格式
        System.out.println(",,,,,,,,,,");
        xstream.alias("xml", textMessage.getClass()); // 将xml的默认根节点替换成“xml”
        System.out.println("......");
        return xstream.toXML(textMessage);
    }
}

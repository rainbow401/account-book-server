package com.rainbow.util;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayInputStream;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class XmlUtil {

    /**
     * xml转换为map(只适用简单的xml(没有嵌套结构))
     *
     * @param xml     xml的字符串
     * @param charset 编码类型
     * @return
     * @throws UnsupportedEncodingException
     * @throws DocumentException
     */
    public static Map<String, String> xmlToMap(String xml, String charset) throws UnsupportedEncodingException, DocumentException, DocumentException {
        Map<String, String> respMap = new HashMap();
        SAXReader reader = new SAXReader();
        Document doc = reader.read(new ByteArrayInputStream(xml.getBytes(charset)));
        Element root = doc.getRootElement();
        xmlToMap(root, respMap);
        return respMap;
    }

    private static Map<String, String> xmlToMap(Element tmpElement, Map<String, String> respMap) {

        if (tmpElement.isTextOnly()) {
            respMap.put(tmpElement.getName(), tmpElement.getText());
            return respMap;
        }

        @SuppressWarnings("unchecked")
        Iterator<Element> eItor = tmpElement.elementIterator();
        while (eItor.hasNext()) {
            Element element = eItor.next();
            xmlToMap(element, respMap);
        }
        return respMap;
    }

    /**
     * map转换为xml
     *
     * @param data(只适用简单的map(没有嵌套结构))
     * @return
     * @throws Exception
     */
    public static String mapToXml(Map<String, String> data) throws Exception {
        org.w3c.dom.Document document = newDocumentBuilder().newDocument();
        org.w3c.dom.Element root = document.createElement("xml");
        document.appendChild(root);
        Iterator var3 = data.keySet().iterator();

        while (var3.hasNext()) {
            String key = (String) var3.next();
            String value = data.get(key);
            if (value == null) {
                value = "";
            }

            value = value.trim();
            org.w3c.dom.Element filed = document.createElement(key);
            filed.appendChild(document.createTextNode(value));
            root.appendChild(filed);
        }

        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        DOMSource source = new DOMSource(document);
        transformer.setOutputProperty("encoding", "UTF-8");
        transformer.setOutputProperty("indent", "yes");
        StringWriter writer = new StringWriter();
        StreamResult result = new StreamResult(writer);
        transformer.transform(source, result);
        String output = writer.getBuffer().toString();

        try {
            writer.close();
        } catch (Exception var10) {

        }
        return output;
    }

    private static DocumentBuilder newDocumentBuilder() throws ParserConfigurationException {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        documentBuilderFactory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
        documentBuilderFactory.setFeature("http://xml.org/sax/features/external-general-entities", false);
        documentBuilderFactory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
        documentBuilderFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
        documentBuilderFactory.setFeature("http://javax.xml.XMLConstants/feature/secure-processing", true);
        documentBuilderFactory.setXIncludeAware(false);
        documentBuilderFactory.setExpandEntityReferences(false);
        return documentBuilderFactory.newDocumentBuilder();
    }

    public static void main(String[] args) throws Exception {
        String s = "<xml>\n" +
                "<auth>\n" +
                "<randomStr>suiji</randomStr>\n" +
                "<token>3ed6b709d31e87af73216149dc510c6fc94975af</token>\n" +
                "</auth>\n" +
                "</xml>";
        System.out.println("------------xmlToMap---------------");
        Map<String, String> xmlToMap = xmlToMap(s, "UTF-8");
        for (Map.Entry<String, String> entry : xmlToMap.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            System.out.println("key = " + key + ",value = " + value);
        }
        System.out.println("------------mapToXml---------------");
        String mapToXml = mapToXml(xmlToMap);
        System.out.println(mapToXml);
    }
}

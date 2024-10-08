package com.hab.blog.feature.v1.payment.util;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.BufferedReader;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.UUID;
import java.util.stream.Collectors;

public class WeChatPayUtils {

    // 生成微信支付签名
    public static String generateSignature(Map<String, String> params, String apiKey) throws Exception {
        SortedMap<String, String> sortedParams = new TreeMap<>(params);
        String paramStr = sortedParams.entrySet().stream()
                .filter(entry -> entry.getValue() != null && !entry.getValue().isEmpty())
                .map(entry -> entry.getKey() + "=" + entry.getValue())
                .collect(Collectors.joining("&"));
        paramStr += "&key=" + apiKey;
        return md5Encrypt(paramStr).toUpperCase();
    }

    private static String md5Encrypt(String text) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] array = md.digest(text.getBytes("UTF-8"));
        StringBuilder sb = new StringBuilder();
        for (byte b : array) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    // 发送请求到微信支付
    public static String sendRequestToWeChat(String url, String xmlData) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_XML);
        HttpEntity<String> entity = new HttpEntity<>(xmlData, headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
        return response.getBody();
    }

    // 将Map转换为XML
    public static String mapToXml(Map<String, String> data) {
        StringBuilder xmlBuilder = new StringBuilder();
        xmlBuilder.append("<xml>");
        for (Map.Entry<String, String> entry : data.entrySet()) {
            xmlBuilder.append("<").append(entry.getKey()).append(">");
            xmlBuilder.append(entry.getValue());
            xmlBuilder.append("</").append(entry.getKey()).append(">");
        }
        xmlBuilder.append("</xml>");
        return xmlBuilder.toString();
    }

    // 将XML转换为Map
    public static Map<String, String> xmlToMap(String xmlData) throws Exception {
        Map<String, String> data = new HashMap<>();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new java.io.ByteArrayInputStream(xmlData.getBytes("UTF-8")));
        Element root = doc.getDocumentElement();
        NodeList nodes = root.getChildNodes();
        for (int i = 0; i < nodes.getLength(); i++) {
            if (nodes.item(i) instanceof Element) {
                Element element = (Element) nodes.item(i);
                data.put(element.getNodeName(), element.getTextContent());
            }
        }
        return data;
    }

    // 生成随机字符串
    public static String generateNonceStr() {
        return UUID.randomUUID().toString().replaceAll("-", "").substring(0, 32);
    }

    // 获取请求体内容
    public static String getRequestBody(HttpServletRequest request) throws Exception {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = request.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        }
        return sb.toString();
    }
}

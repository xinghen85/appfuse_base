package com.btxy.basis.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import com.alibaba.fastjson.JSONObject;

public class HTTP {
	
	public static final String RESULT = "result_code";
	public static final String RESULT_MSG = "result_message";
	
	private static Logger log = Logger.getLogger(HTTP.class);
	public static String httpClientGet(String requestUrl) {
        log.debug("请求URL：" + requestUrl);
        StringBuilder response = new StringBuilder();
        try {
            URL url = new URL(requestUrl);
            HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
            urlConn.setReadTimeout(30000);
            BufferedReader reader = new BufferedReader(new InputStreamReader(urlConn.getInputStream(), "utf-8")); // 实例化输入流，并获取网页代码
            String s; // 依次循环，至到读的值为空            
            while ((s = reader.readLine()) != null) {
                response.append(s);
            }
            log.debug("返回响应：" + response.toString());
            reader.close();
        } catch (IOException e) {
            log.debug(e);
        }
        return response.toString();
    }
	public static String httpClientPost(String requestUrl, Object content) throws IOException {
		String contentStr = "";
		if (content != null) {
			contentStr = JSONObject.toJSONString(content);
		}
		log.info("HTTP POST 请求URL" + requestUrl + "写入内容 - " + contentStr);
		return httpClientPost(requestUrl, contentStr);
	}
	
	public static String httpClientPost(String requestUrl, String content) throws IOException {
		return httpClientPost(requestUrl, content, MediaType.APPLICATION_JSON);
	}
	
	public static String httpClientPost(String requestUrl, String content, String mimeType) throws IOException {
        URL url = new URL(requestUrl);
        HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
        urlConn.setReadTimeout(30000000);
        urlConn.setDoInput(true);// 有输入
        urlConn.setDoOutput(true);// 有输出
        urlConn.setRequestMethod("POST");
        urlConn.setRequestProperty("Content-Type", mimeType);
        OutputStream os = urlConn.getOutputStream();
        os.write(content.getBytes());
        os.close();
        BufferedReader reader = new BufferedReader(new InputStreamReader(urlConn.getInputStream(), "utf-8")); // 实例化输入流，并获取网页代码
        String s; // 依次循环，至到读的值为空
        StringBuilder response = new StringBuilder();
        while ((s = reader.readLine()) != null) {
            response.append(s);
        }
        log.info("http请求响应：" + response.toString());
        reader.close();
        urlConn.disconnect();
        return response.toString();
    }
}

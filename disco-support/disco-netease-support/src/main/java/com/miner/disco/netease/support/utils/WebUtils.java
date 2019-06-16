package com.miner.disco.netease.support.utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by lubycoder@163.com on 2017/8/7.
 */
public class WebUtils {

    private static final String DEFAULT_CHARSET = "UTF-8";

    public static String doPost(String url, Map<String, String> headers, Map<String, String> queryParams) throws IOException {
        CloseableHttpClient httpclient = HttpClientBuilder.create().build();
        HttpPost request = new HttpPost(url);
        request.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
        for (String key : headers.keySet()) {
            request.addHeader(key, headers.get(key));
        }
        List<NameValuePair> params = new ArrayList<>();
        for (String key : queryParams.keySet()) {
            params.add(new BasicNameValuePair(key, queryParams.get(key)));
        }
        request.setEntity(new UrlEncodedFormEntity(params, DEFAULT_CHARSET));
        HttpEntity entity = httpclient.execute(request).getEntity();
        String result = EntityUtils.toString(entity, DEFAULT_CHARSET);
        request.releaseConnection();
        return result;
    }

    public static String doGet(String url, Map<String, String> headers, Map<String, String> params) throws IOException {
        CloseableHttpClient httpclient = HttpClientBuilder.create().build();
        StringBuilder sb = new StringBuilder();
        Set es = params.entrySet();
        for (Object e : es) {
            Map.Entry entry = (Map.Entry) e;
            String k = (String) entry.getKey();
            String v = (String) entry.getValue();
            if (null != v && !"".equals(v) && !"key".equals(k)) {
                sb.append(k).append("=").append(v).append("&");
            }
        }
        String param = sb.toString();
        HttpGet request = new HttpGet(url.concat("?").concat(param));
        request.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
        for (String key : headers.keySet()) {
            request.addHeader(key, headers.get(key));
        }
        HttpResponse response = httpclient.execute(request);
        HttpEntity entity = response.getEntity();
        String result = EntityUtils.toString(entity, DEFAULT_CHARSET);
        request.releaseConnection();
        System.out.println(result);
        return result;
    }

}

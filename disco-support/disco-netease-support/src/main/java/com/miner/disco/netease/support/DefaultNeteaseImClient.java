package com.miner.disco.netease.support;

import com.miner.disco.netease.support.exception.ImException;
import com.miner.disco.netease.support.utils.WebUtils;
import org.apache.commons.beanutils.PropertyUtilsBean;

import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by lubycoder@163.com on 2017/8/7.
 */
public class DefaultNeteaseImClient implements NeteaseImClient {

    private String appKey;
    private String secret;
    private int connectTimeout;
    private int readTimeout;

    public DefaultNeteaseImClient() {

    }

    public DefaultNeteaseImClient(String appKey, String secret) {
        this.connectTimeout = 3000;
        this.readTimeout = 15000;
        this.appKey = appKey;
        this.secret = secret;
    }

    public DefaultNeteaseImClient(String appKey, String secret, int connectTimeout, int readTimeout) {
        this.connectTimeout = connectTimeout;
        this.readTimeout = readTimeout;
        this.appKey = appKey;
        this.secret = secret;
    }

    @Override
    public <T extends NeteaseImResponse> T execute(NeteaseImRequest<T> request) throws ImException {
        Map<String, String> params = beanToMap(request.getBizModel());
        Map<String, String> headers = new HashMap<>();
        long CurTime = System.currentTimeMillis() / 1000;
        String Nonce = UUID.randomUUID().toString();
        headers.put("AppKey", appKey);
        headers.put("Nonce", Nonce);
        headers.put("CurTime", String.valueOf(CurTime));
        headers.put("CheckSum", CheckSumBuilder.getCheckSum(secret, Nonce, String.valueOf(CurTime)));

        String resultStr;

        try {
            resultStr = WebUtils.doPost(request.getUrl(), headers, params);
        } catch (IOException e) {
            throw new ImException("http client error", e);
        }

        return JsonParser.deserializeByJson(resultStr, request.getResponseClass());
    }

    private static Map<String, String> beanToMap(Object obj) {
        Map<String, String> params = new HashMap<>(0);
        try {
            PropertyUtilsBean propertyUtilsBean = new PropertyUtilsBean();
            PropertyDescriptor[] descriptors = propertyUtilsBean.getPropertyDescriptors(obj);
            for (PropertyDescriptor descriptor : descriptors) {
                String name = descriptor.getName();
                if (!"class".equals(name)) {
                    Object object = propertyUtilsBean.getNestedProperty(obj, name);
                    if (null != object) {
                        params.put(name, object.toString());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return params;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public void setReadTimeout(int readTimeout) {
        this.readTimeout = readTimeout;
    }

}

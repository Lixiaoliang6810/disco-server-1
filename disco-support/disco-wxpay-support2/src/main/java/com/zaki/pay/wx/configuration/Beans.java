package com.zaki.pay.wx.configuration;


import com.zaki.pay.wx.service.impl.WXPayServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.security.KeyStoreException;

/**
 * @author Created by lubycoder@163.com 2019/1/7
 */
@Configuration
public class Beans {

    @Value("${wechat.pay.app-id}")
    String wxAppId;
    @Value("${wechat.pay.mch-id}")
    String mchid;
    @Value("${wechat.pay.api-secret}")
    String secret;

    @Bean(name = "wxPayService")
    public WXPayServiceImpl wxPayService() throws KeyStoreException {
        return new WXPayServiceImpl(wxAppId,mchid,secret);
    }
}

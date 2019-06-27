package com.miner.disco.mch.configuration;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.miner.disco.alipay.support.AlipayService;
import com.miner.disco.wxpay.support.WxpayService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Created by lubycoder@163.com 2019/1/7
 */
@Configuration
public class Beans {

    @Value("${ali.pay.gateway}")
    String gateway;
    @Value("${ali.pay.appid}")
    String appid;
    @Value("${ali.pay.private-key}")
    String privateKey;
    @Value("${ali.pay.public-key}")
    String publicKey;
    @Value("${ali.pay.sign-type}")
    String signType;
    @Value("${ali.pay.format}")
    String format;
    @Value("${ali.pay.charset}")
    String charset;

    @Value("${wechat.pay.app-id}")
    String wxAppId;
    @Value("${wechat.pay.mch-id}")
    String mchid;
    @Value("${wechat.pay.api-secret}")
    String secret;

    @Bean(name = "alipayClient")
    public AlipayClient alipayClient() {
        return new DefaultAlipayClient(gateway, appid, privateKey, format, charset, publicKey, signType);
    }

    @Bean(name = "alipayService")
    public AlipayService alipayService() {
        return new AlipayService().setAlipayClient(alipayClient());
    }

    @Bean(name = "wxpayService")
    public WxpayService wxpayService() {
        return new WxpayService(wxAppId,mchid,secret);
    }
}

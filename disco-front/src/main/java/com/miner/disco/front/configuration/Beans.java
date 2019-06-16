package com.miner.disco.front.configuration;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.miner.disco.alipay.support.AlipayService;
import com.miner.disco.front.helper.IdentifierService;
import com.miner.disco.netease.support.DefaultNeteaseImClient;
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


    @Bean(name = "alipayClient")
    public AlipayClient alipayClient() {
        return new DefaultAlipayClient(gateway, appid, privateKey, format, charset, publicKey, signType);
    }

    @Bean(name = "alipayService")
    public AlipayService alipayService() {
        return new AlipayService().setAlipayClient(alipayClient());
    }

    @Bean(name = "wxpayService")
    public WxpayService wxpayService(@Value("${wechat.pay.app-id}") String appId,
                                     @Value("${wechat.pay.mch-id}") String mchId,
                                     @Value("${wechat.pay.api-secret}") String apiSecret) {
        return new WxpayService(appId, mchId, apiSecret);
    }

    @Bean(name = "defaultNeteaseImClient")
    public DefaultNeteaseImClient defaultNeteaseImClient(@Value("${netease.im.app-key}") String appKey,
                                                         @Value("${netease.im.app-secret}") String appSecret) {
        return new DefaultNeteaseImClient(appKey, appSecret);
    }

    @Bean
    public IdentifierService identifierService() {
        return new IdentifierService(1000);
    }

}

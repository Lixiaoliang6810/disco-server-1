package com.miner.disco.front.service.impl;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.miner.disco.front.service.SmsService;
import org.springframework.stereotype.Service;

/**
 * @Author: wz1016_vip@163.com
 * @Date: 2019/6/21 13:38
 */
@Service
public class SmsServiceImpl implements SmsService {
    private final static String SIGN = "麦罗佛伦";
    private final static String TEMPLATE_CODE = "SMS_168590610";

    private final static String ACCESS_KEY_ID = "LTAIW9LJUYbjTqI8";
    private final static String ACCESS_KEY_SECRET = "AnCkbmX32ZYHdfgfqmtbH4AINAW7W9";

    /**
     * 新订单短信提醒服务
     * @param mchPhone 店家手机
     * @param mobile 预定的客户手机
     * @param arrivalTime 预计到达时间
     * @param amount 预计人数
     */
    @Override
    public SendSmsResponse newOrderSmsNotify(String mchPhone,String mobile, String arrivalTime, String amount) throws ClientException {
        //设置超时时间-可自行调整
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");
        //短信API产品名称（短信产品名固定，无需修改）
        final String product = "Dysmsapi";
        //短信API产品域名（接口地址固定，无需修改）
        final String domain = "dysmsapi.aliyuncs.com";
        //初始化ascClient,暂时不支持多region（请勿修改）
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", ACCESS_KEY_ID, ACCESS_KEY_SECRET);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        IAcsClient acsClient = new DefaultAcsClient(profile);
        //组装请求对象
        SendSmsRequest request = new SendSmsRequest();
        //使用post提交
        request.setMethod(MethodType.POST);
        //必填:待发送手机号。支持以逗号分隔的形式进行批量调用，批量上限为1000个手机号码,批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式；发送国际/港澳台消息时，接收号码格式为00+国际区号+号码，如“0085200000000”
        request.putQueryParameter("PhoneNumbers", mchPhone);
        //必填:短信签名-可在短信控制台中找到
        request.putQueryParameter("SignName", SIGN);
        //必填:短信模板-可在短信控制台中找到，发送国际/港澳台消息时，请使用国际/港澳台短信模版
        request.putQueryParameter("TemplateCode", TEMPLATE_CODE);
        //可选:模板中的变量替换JSON串->您有新的订单，客户手机${phone}，预计到店时间${time}，人数${amount}，请尽快联系客户确定。
        //友情提示:如果JSON中需要带换行符,请参照标准的JSON协议对换行符的要求,比如短信内容中包含\r\n的情况在JSON中需要表示成\\r\\n,否则会导致JSON在服务端解析失败
        // {phone:17316290556,time:18,amount:2}
        request.setTemplateParam("{phone:"+mobile+",time:'"+arrivalTime+"',amount:"+amount+"}");
        //请求失败这里会抛ClientException异常
        return acsClient.getAcsResponse(request);
    }
}

package com.miner.disco.front.controller;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.miner.disco.basic.model.response.ViewData;
import com.miner.disco.basic.util.SMSHelper;
import com.miner.disco.front.consts.Const;
import com.miner.disco.front.consts.SMSType;
import com.miner.disco.front.exception.BusinessExceptionCode;
import com.miner.disco.front.service.MemberService;
import com.miner.disco.front.service.impl.SmsServiceImpl;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author Created by lubycoder@163.com 2018/12/27
 */
@RestController
public class SmsController {



    @Resource(name = "redisTemplate")
    private ValueOperations<String, String> vOps;

    private final MemberService memberService;
    private final SmsServiceImpl smsService;
    @Autowired
    public SmsController(MemberService memberService,SmsServiceImpl smsService){
        this.memberService = memberService;
        this.smsService = smsService;
    }

    @GetMapping(value = "/sms/code", headers = Const.API_VERSION_1_0_0)
    public ViewData sendSmsCode(@RequestParam("mobile") String mobile,
                                @RequestParam("smsType") SMSType smsType) throws IOException {
        boolean flag;
        switch (smsType) {
            case REG:
                flag = memberService.exist(mobile);
                if (flag){
                    return ViewData.builder().status(BusinessExceptionCode.MEMBER_REGISTERED.getCode()).message("手机号已注册").build();
                }
                break;
            case REST:
                flag = memberService.exist(mobile);
                if (!flag) {
                    return ViewData.builder().status(BusinessExceptionCode.MEMBER_UNREGISTERED.getCode()).message("手机号未注册").build();
                }
                break;
            default:break;
        }
        int code = RandomUtils.nextInt(100000, 999999);
        SMSHelper.sendChinaMessage(String.format("验证码：%s，10分钟内有效，请勿告诉他人。", code), mobile);
        vOps.set(String.format(Const.REDIS_CACHE_SMS_PREFIX, mobile), String.valueOf(code), 5 * 60, TimeUnit.SECONDS);
        return ViewData.builder().message("获取验证码成功").build();
    }


//    /**
//     * 短信通知店家有新订单
//     *
//     * @param phoneNumbers 预定电话
//     * @param arrivalTime 预计到达时间
//     * @param amount 人数
//     */
//    @GetMapping(value = "/sms/orderNotify",headers = Const.API_VERSION_1_0_0)
//    public ViewData newOrderSmsNotify(@RequestParam("mobile") String phoneNumbers,
//                                      @RequestParam("arrivalTime") String arrivalTime,
//                                      @RequestParam("amount") String amount) throws ClientException {
//
//        SendSmsResponse sendSmsResponse = smsService.newOrderSmsNotify(phoneNumbers, arrivalTime, amount);
//
//        if (null != sendSmsResponse.getCode() && "OK".equals(sendSmsResponse.getCode())) {
//            //请求成功
//            System.out.println("=====success====");
//            return ViewData.builder().message("新订单短信提醒成功").build();
//        } else {
//            System.out.println("=====fail=======");
//            return ViewData.builder().message("短信发送超时").build();
//        }
//    }

    public static void main(String[] args) throws ClientException {
//         newOrderSmsNotify("17316290556","18:00",2);
    }
}

package com.miner.disco.mch.controller;

import com.miner.disco.basic.model.response.ViewData;
import com.miner.disco.basic.util.SMSHelper;
import com.miner.disco.mch.consts.Const;
import com.miner.disco.mch.consts.SMSType;
import com.miner.disco.mch.exception.MchBusinessExceptionCode;
import com.miner.disco.mch.service.MerchantService;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author Created by lubycoder@163.com 2019-2-15
 */
@RestController
public class SmsController {

    @Autowired
    private MerchantService merchantService;

    @Resource(name = "redisTemplate")
    private ValueOperations<String, String> vOps;

    @GetMapping(value = "/sms/code", headers = Const.API_VERSION_1_0_0)
    public ViewData sendSmsCode(@RequestParam("mobile") String mobile,
                                @RequestParam("smsType") SMSType smsType) throws IOException {
        boolean flag;
        switch (smsType) {
            case REG:
                flag = merchantService.exist(mobile);
                if (flag)
                    return ViewData.builder().status(MchBusinessExceptionCode.MOBILE_REGISTERED.getCode()).message("手机号已注册").build();
                break;
            case REST:
                flag = merchantService.exist(mobile);
                if (!flag)
                    return ViewData.builder().status(MchBusinessExceptionCode.MOBILE_UNREGISTERED.getCode()).message("手机号未注册").build();
                break;
        }
        int code = RandomUtils.nextInt(100000, 999999);
        SMSHelper.sendChinaMessage(String.format("验证码：%s，10分钟内有效，请勿告诉他人。", code), mobile);
        vOps.set(String.format(Const.REDIS_CACHE_SMS_PREFIX, mobile), String.valueOf(code), 5 * 60, TimeUnit.SECONDS);
        return ViewData.builder().message("获取验证码成功").build();
    }

}

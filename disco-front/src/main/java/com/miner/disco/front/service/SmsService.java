package com.miner.disco.front.service;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;

/**
 * @Author: wz1016_vip@163.com
 * @Description: TODO
 */
public interface SmsService {
    SendSmsResponse newOrderSmsNotify(String phoneNumbers,String mobile, String arriveTime, String amount) throws ClientException;
}

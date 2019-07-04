package com.miner.disco.front.service;

import org.springframework.stereotype.Service;

/**
 * @author: wz1016_vip@163.com  2019/7/3
 */
@Service
public interface RefundService {

    void refund(String outTradeNo);
}

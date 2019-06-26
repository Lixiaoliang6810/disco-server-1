package com.miner.disco.mch.dao;

import com.miner.disco.mybatis.support.BasicMapper;
import com.miner.disco.pojo.MerchantAggregateQrcode;
import org.apache.ibatis.annotations.Param;

/**
 * @author Created by lubycoder@163.com 2019-06-21
 */
public interface MerchantAggregateQrcodeMapper extends BasicMapper<MerchantAggregateQrcode> {

    MerchantAggregateQrcode queryByOutTradeNo(@Param("outTradeNo") String outTradeNo);

}

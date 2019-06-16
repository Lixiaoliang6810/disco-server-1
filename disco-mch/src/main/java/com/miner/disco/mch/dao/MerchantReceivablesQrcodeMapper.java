package com.miner.disco.mch.dao;

import com.miner.disco.mybatis.support.BasicMapper;
import com.miner.disco.pojo.MerchantReceivablesQrcode;
import org.apache.ibatis.annotations.Param;

/**
 * @author Created by lubycoder@163.com 2019-2-20
 */
public interface MerchantReceivablesQrcodeMapper extends BasicMapper<MerchantReceivablesQrcode> {

    MerchantReceivablesQrcode queryByKey(@Param("key") String key);

}

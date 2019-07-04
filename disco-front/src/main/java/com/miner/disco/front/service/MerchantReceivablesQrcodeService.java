package com.miner.disco.front.service;

import com.miner.disco.front.exception.BusinessException;
import com.miner.disco.pojo.MerchantReceivablesQrcode;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

/**
 * @author Created by lubycoder@163.com 2019-2-20
 */
@Service
public interface MerchantReceivablesQrcodeService {

    MerchantReceivablesQrcode queryByPrimaryKey(Long id) throws BusinessException;

    MerchantReceivablesQrcode queryByKey(@Param("key") String key) throws BusinessException;

}

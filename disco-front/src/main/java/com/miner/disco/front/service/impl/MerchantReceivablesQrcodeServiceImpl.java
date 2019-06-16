package com.miner.disco.front.service.impl;

import com.miner.disco.front.dao.MerchantReceivablesQrcodeMapper;
import com.miner.disco.front.exception.BusinessException;
import com.miner.disco.front.service.MerchantReceivablesQrcodeService;
import com.miner.disco.pojo.MerchantReceivablesQrcode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Created by lubycoder@163.com 2019-2-20
 */
@Service
public class MerchantReceivablesQrcodeServiceImpl implements MerchantReceivablesQrcodeService {

    @Autowired
    private MerchantReceivablesQrcodeMapper merchantReceivablesQrcodeMapper;

    @Override
    public MerchantReceivablesQrcode queryByPrimaryKey(Long id) throws BusinessException {
        return merchantReceivablesQrcodeMapper.queryByPrimaryKey(id);
    }

    @Override
    public MerchantReceivablesQrcode queryByKey(String key) throws BusinessException {
        return merchantReceivablesQrcodeMapper.queryByKey(key);
    }

}

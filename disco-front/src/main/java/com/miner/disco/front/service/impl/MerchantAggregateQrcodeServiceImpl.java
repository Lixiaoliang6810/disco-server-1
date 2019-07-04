//package com.miner.disco.front.service.impl;
//
//import com.miner.disco.front.dao.MerchantAggregateQrcodeMapper;
//import com.miner.disco.front.exception.BusinessException;
//import com.miner.disco.front.service.MerchantAggregateQrcodeService;
//import com.miner.disco.pojo.MerchantAggregateQrcode;
//import org.springframework.beans.factory.annotation.Autowired;
//
///**
// * @author: wz1016_vip@163.com  2019/7/4
// */
//public class MerchantAggregateQrcodeServiceImpl implements MerchantAggregateQrcodeService {
//
//    @Autowired
//    private MerchantAggregateQrcodeMapper merchantAggregateQrcodeMapper;
//
//
//    @Override
//    public MerchantAggregateQrcode queryByPrimaryKey(Long id) throws BusinessException {
//        return merchantAggregateQrcodeMapper.queryByPrimaryKey(id);
//    }
//
//    @Override
//    public MerchantAggregateQrcode queryByOutTradeNo(String outTradeNo) {
//        return merchantAggregateQrcodeMapper.queryByOutTradeNo(outTradeNo);
//    }
//}

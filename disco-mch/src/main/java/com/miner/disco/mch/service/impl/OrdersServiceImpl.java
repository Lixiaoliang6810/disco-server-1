package com.miner.disco.mch.service.impl;

import com.miner.disco.basic.assertion.Assert;
import com.miner.disco.mch.dao.MerchantBillMapper;
import com.miner.disco.mch.dao.MerchantMapper;
import com.miner.disco.mch.dao.OrdersMapper;
import com.miner.disco.mch.exception.MchBusinessException;
import com.miner.disco.mch.exception.MchBusinessExceptionCode;
import com.miner.disco.mch.model.request.OrdersListRequest;
import com.miner.disco.mch.model.response.OrdersDetailsResponse;
import com.miner.disco.mch.model.response.OrdersListResponse;
import com.miner.disco.mch.service.OrdersService;
import com.miner.disco.pojo.Merchant;
import com.miner.disco.pojo.MerchantBill;
import com.miner.disco.pojo.Orders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author Created by lubycoder@163.com 2019/1/9
 */
@Service
public class OrdersServiceImpl implements OrdersService {

    @Autowired
    private OrdersMapper ordersMapper;

    @Autowired
    private MerchantMapper merchantMapper;

    @Autowired
    private MerchantBillMapper merchantBillMapper;

    @Override
    public List<OrdersListResponse> list(OrdersListRequest request) throws MchBusinessException {
        return ordersMapper.queryByMerchantId(request);
    }

    @Override
    public OrdersDetailsResponse details(Long ordersId) throws MchBusinessException {
        return ordersMapper.queryById(ordersId);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class, isolation = Isolation.READ_COMMITTED)
    public void confirm(Long merchantId, Long ordersId) throws MchBusinessException {
        Orders orders = ordersMapper.queryByPrimaryKey(ordersId);
        Assert.notNull(orders, MchBusinessExceptionCode.ILLEGAL_OPERATION.getCode(), "非法操作");
        if (orders.getSeller().longValue() != merchantId) {
            throw new MchBusinessException(MchBusinessExceptionCode.NON_LOCAL_ORDERS.getCode(), "非本商家订单");
        }
        if (orders.getStatus().intValue() != Orders.STATUS.WAIT_CONSUMPTION.getKey()) {
            throw new MchBusinessException(MchBusinessExceptionCode.ORDERS_STATUS_ERROR.getCode(), "订单状态错误");
        }
        Orders saveOrders = new Orders();
        saveOrders.setId(ordersId);
        saveOrders.setStatus(Orders.STATUS.WAIT_EVALUATE.getKey());
        ordersMapper.updateByPrimaryKey(saveOrders);

        Merchant merchant = merchantMapper.queryByPrimaryKeyForUpdate(orders.getSeller());
        Merchant saveMerchant = new Merchant();
        saveMerchant.setId(merchant.getId());
        saveMerchant.setUsableBalance(merchant.getUsableBalance().add(orders.getTotalMoney()));
        saveMerchant.setFrozenBalance(merchant.getFrozenBalance().subtract(orders.getTotalMoney()));
        merchantMapper.updateByPrimaryKey(saveMerchant);

        //记录商户流水
        MerchantBill merchantBill = new MerchantBill();
        merchantBill.setAmount(orders.getTotalMoney());
        merchantBill.setMerchantId(orders.getSeller());
        merchantBill.setSourceId(orders.getId());
        merchantBill.setCreateDate(new Date());
        merchantBill.setUpdateDate(new Date());
        merchantBill.setType(MerchantBill.STATUS.IN.getKey());
        merchantBill.setTradeType(MerchantBill.TRADE_STATUS.ON_LINE.getKey());
        merchantBill.setRemark(MerchantBill.TRADE_STATUS.ON_LINE.getValue());
        merchantBillMapper.insert(merchantBill);
    }

}

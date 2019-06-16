package com.miner.disco.mch.service.impl;

import com.miner.disco.basic.constants.BasicConst;
import com.miner.disco.mch.dao.MerchantBillMapper;
import com.miner.disco.mch.dao.MerchantMapper;
import com.miner.disco.mch.dao.OrdersMapper;
import com.miner.disco.mch.exception.MchBusinessException;
import com.miner.disco.mch.model.response.IndexResponse;
import com.miner.disco.mch.service.IndexService;
import com.miner.disco.pojo.Merchant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Objects;

/**
 * @author Created by lubycoder@163.com 2019/1/9
 */
@Service
public class IndexServiceImpl implements IndexService {

    @Autowired
    private OrdersMapper ordersMapper;

    @Autowired
    private MerchantBillMapper merchantBillMapper;

    @Resource(name = "redisTemplate")
    private ValueOperations<String, String> vOps;

    @Override
    public IndexResponse index(Long merchantId) throws MchBusinessException {
        IndexResponse indexResponse = new IndexResponse();
        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DATE);

        int currentOrdersAmount = vOps.get(BasicConst.REDIS_CACHE_MERCHANT_RESERVE_ORDERS) != null ?
                Integer.parseInt(Objects.requireNonNull(vOps.get(BasicConst.REDIS_CACHE_MERCHANT_RESERVE_ORDERS))) : 0;
        int todayOrdersAmount = ordersMapper.statisticsToDayOrders(merchantId);
        int thisMonthOrdersAmount = ordersMapper.statisticsThisMonthOrders(merchantId);
        int totalOrdersAmount = ordersMapper.statisticsTotalOrders(merchantId);
        BigDecimal todayIncomeMoney = merchantBillMapper.statisticsTodayIncomeMoney(merchantId);
        BigDecimal thisMonthIncomeMoney = merchantBillMapper.statisticsThisMonthIncomeMoney(merchantId);
        BigDecimal totalIncomeMoney = merchantBillMapper.statisticsTotalIncomeMoney(merchantId);
        int todayBrowseAmount = vOps.get(String.format(BasicConst.REDIS_CACHE_MERCHANT_DAY_BROWSE_VOLUME, day, merchantId)) != null ?
                Integer.parseInt(Objects.requireNonNull(vOps.get(String.format(BasicConst.REDIS_CACHE_MERCHANT_DAY_BROWSE_VOLUME, day, merchantId)))) : 0;
        int thisMonthBrowseAmount = vOps.get(String.format(BasicConst.REDIS_CACHE_MERCHANT_MONTH_BROWSE_VOLUME, month, merchantId)) != null ?
                Integer.parseInt(Objects.requireNonNull(vOps.get(String.format(BasicConst.REDIS_CACHE_MERCHANT_MONTH_BROWSE_VOLUME, month, merchantId)))) : 0;
        int totalBrowseAmount = vOps.get(String.format(BasicConst.REDIS_CACHE_MERCHANT_BROWSE_VOLUME, merchantId)) != null ?
                Integer.parseInt(Objects.requireNonNull(vOps.get(String.format(BasicConst.REDIS_CACHE_MERCHANT_BROWSE_VOLUME, merchantId)))) : 0;

        indexResponse.setCurrentOrdersAmount(currentOrdersAmount);
        indexResponse.setTodayOrdersAmount(todayOrdersAmount);
        indexResponse.setThisMonthOrdersAmount(thisMonthOrdersAmount);
        indexResponse.setTotalOrdersAmount(totalOrdersAmount);
        indexResponse.setTodayIncomeMoney(todayIncomeMoney == null ? BigDecimal.ZERO : todayIncomeMoney);
        indexResponse.setThisMonthIncomeMoney(thisMonthIncomeMoney == null ? BigDecimal.ZERO : thisMonthIncomeMoney);
        indexResponse.setTotalIncomeMoney(totalIncomeMoney == null ? BigDecimal.ZERO : totalIncomeMoney);
        indexResponse.setTodayBrowseAmount(todayBrowseAmount);
        indexResponse.setThisMonthBrowseAmount(thisMonthBrowseAmount);
        indexResponse.setTotalBrowseAmount(totalBrowseAmount);

        vOps.set(String.format(BasicConst.REDIS_CACHE_MERCHANT_RESERVE_ORDERS, merchantId), String.valueOf(0));
        return indexResponse;
    }

}

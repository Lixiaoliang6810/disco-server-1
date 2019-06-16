package com.miner.disco.mch.dao;

import com.miner.disco.mch.model.request.MerchantOfflineBillRequest;
import com.miner.disco.mch.model.response.MerchantOfflineBillResponse;
import com.miner.disco.mch.model.response.QueryMerchantBill;
import com.miner.disco.mybatis.support.BasicMapper;
import com.miner.disco.pojo.MerchantBill;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Created by lubycoder@163.com 2019-2-18
 */
public interface MerchantBillMapper extends BasicMapper<MerchantBill> {

    List<QueryMerchantBill> queryByMerchantId(@Param("merchantId") Long merchantId,
                                              @Param("year") Integer year,
                                              @Param("month") Integer month);

    List<MerchantOfflineBillResponse> offLineBills(MerchantOfflineBillRequest request);

    BigDecimal statisticsTodayIncomeMoney(@Param("merchantId") Long merchantId);

    BigDecimal statisticsThisMonthIncomeMoney(@Param("merchantId") Long merchantId);

    BigDecimal statisticsTotalIncomeMoney(@Param("merchantId") Long merchantId);

}

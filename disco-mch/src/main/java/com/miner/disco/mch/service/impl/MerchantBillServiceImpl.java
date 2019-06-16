package com.miner.disco.mch.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.miner.disco.basic.util.DtoTransition;
import com.miner.disco.mch.dao.MerchantBillMapper;
import com.miner.disco.mch.dao.MerchantMapper;
import com.miner.disco.mch.exception.MchBusinessException;
import com.miner.disco.mch.model.request.MerchantOfflineBillRequest;
import com.miner.disco.mch.model.response.MerchantBillListResponse;
import com.miner.disco.mch.model.response.MerchantBillResponse;
import com.miner.disco.mch.model.response.MerchantOfflineBillResponse;
import com.miner.disco.mch.model.response.QueryMerchantBill;
import com.miner.disco.mch.service.MerchantBillService;
import com.miner.disco.pojo.Merchant;
import com.miner.disco.pojo.MerchantBill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Created by lubycoder@163.com 2019-2-18
 */
@Service
public class MerchantBillServiceImpl implements MerchantBillService {

    @Autowired
    private MerchantBillMapper merchantBillMapper;

    @Autowired
    private MerchantMapper merchantMapper;

    @Override
    public MerchantBillResponse list(Long merchantId, Integer year, Integer month) throws MchBusinessException {
        List<QueryMerchantBill> billList = merchantBillMapper.queryByMerchantId(merchantId, year, month);
        MerchantBillResponse response = new MerchantBillResponse();
        response.setOnlineIncome(BigDecimal.ZERO);
        response.setUnLineIncome(BigDecimal.ZERO);
        response.setBills(Maps.newHashMap());
        Merchant merchant = merchantMapper.queryByPrimaryKey(merchantId);
        response.setUsableBalance(merchant.getUsableBalance());
        billList.forEach(bill -> {
            MerchantBillResponse.MerchantBillListWrap wrap;
            List<MerchantBillListResponse> billListResponses;
            if (response.getBills().containsKey(bill.getDay())) {
                wrap = response.getBills().get(bill.getDay());
                billListResponses = wrap.getBills();
            } else {
                wrap = new MerchantBillResponse.MerchantBillListWrap();
                billListResponses = Lists.newArrayList();
            }
            if (bill.getTradeType().intValue() == MerchantBill.TRADE_STATUS.ON_LINE.getKey()) {
                wrap.setOnlineIncome(response.getOnlineIncome().add(bill.getAmount()));
                response.setOnlineIncome(response.getOnlineIncome().add(bill.getAmount()));
            }
            if (bill.getTradeType().intValue() == MerchantBill.TRADE_STATUS.OFF_LINE.getKey()) {
                wrap.setUnLineIncome(response.getUnLineIncome().add(bill.getAmount()));
                response.setUnLineIncome(response.getUnLineIncome().add(bill.getAmount()));
            }
            if (bill.getTradeType().intValue() == MerchantBill.TRADE_STATUS.REFUND.getKey()) {
                wrap.setOnlineIncome(response.getOnlineIncome().subtract(bill.getAmount()));
                response.setOnlineIncome(response.getOnlineIncome().subtract(bill.getAmount()));
            }
            billListResponses.add((MerchantBillListResponse) DtoTransition.trans(MerchantBillListResponse.class, bill));
            wrap.setBills(billListResponses);
            response.getBills().put(bill.getDay(), wrap);
        });
        return response;
    }

    @Override
    public List<MerchantOfflineBillResponse> offLineBills(MerchantOfflineBillRequest request) throws MchBusinessException {
        return merchantBillMapper.offLineBills(request);
    }
}

package com.miner.disco.admin.service.merchant.impl;

import com.miner.disco.admin.dao.merchant.MerchantBillDao;
import com.miner.disco.admin.dao.merchant.MerchantDao;
import com.miner.disco.admin.dao.merchant.SettlementApplyDao;
import com.miner.disco.admin.exception.AdminBuzException;
import com.miner.disco.admin.exception.AdminBuzExceptionCode;
import com.miner.disco.admin.model.request.merchant.SettlementApplyReviewRequest;
import com.miner.disco.admin.model.request.merchant.SettlementApplySearchRequest;
import com.miner.disco.admin.model.response.merchant.SettlementApplyListResponse;
import com.miner.disco.admin.service.merchant.SettlementApplyService;
import com.miner.disco.basic.assertion.Assert;
import com.miner.disco.basic.model.response.PageResponse;
import com.miner.disco.pojo.Merchant;
import com.miner.disco.pojo.MerchantBill;
import com.miner.disco.pojo.MerchantBill.TRADE_STATUS;
import com.miner.disco.pojo.MerchantSettlementApply;
import com.miner.disco.pojo.MerchantSettlementApply.STATUS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * author:linjw Date:2019/2/21 Time:11:58
 */
@Service
public class SettlementApplyServiceImpl implements SettlementApplyService {

    @Autowired
    private SettlementApplyDao settlementApplyDao;

    @Autowired
    private MerchantDao merchantDao;

    @Autowired
    private MerchantBillDao merchantBillDao;

    @Override
    public PageResponse list(SettlementApplySearchRequest searchRequest) throws AdminBuzException {
        List<SettlementApplyListResponse> listResponses = settlementApplyDao.list(searchRequest);
        Integer total = settlementApplyDao.countList(searchRequest);
        return new PageResponse(total, listResponses);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = RuntimeException.class)
    public void auditing(Long reviewUserId, SettlementApplyReviewRequest reviewRequest) throws AdminBuzException {
        MerchantSettlementApply settlementApply = settlementApplyDao.queryByPrimaryKey(reviewRequest.getId());
        Assert.notNull(settlementApply, AdminBuzExceptionCode.OBJECT_NOT_EXIST.getCode());
        Assert.isTrue(settlementApply.getStatus().intValue() == STATUS.WAIT_REVIEW.getKey().intValue(),
            AdminBuzExceptionCode.SETTLEMENT_APPLY_ALREADY_REVIEW.getCode(), AdminBuzExceptionCode.SETTLEMENT_APPLY_ALREADY_REVIEW.getMessage());
        settlementApply.setStatus(reviewRequest.getStatus());
        settlementApply.setReviewUserId(reviewUserId);
        settlementApply.setUpdateDate(new Date());
        settlementApplyDao.updateByPrimaryKey(settlementApply);

        Merchant merchant = merchantDao.queryByPrimaryKey(settlementApply.getMerchantId());
        Assert.notNull(merchant, AdminBuzExceptionCode.OBJECT_NOT_EXIST.getCode());
        merchant = merchantDao.queryForUpdate(merchant.getId());
       Assert.isTrue(merchant.getFrozenBalance().compareTo(settlementApply.getAmount()) >= 0,
            AdminBuzExceptionCode.SETTLEMENT_APPLY_HANDLE_ERROR.getCode(), "冻结余额不足");

        Merchant saveMerchant = new Merchant();
        saveMerchant.setId(merchant.getId());
/*        saveMerchant.setUsableBalance(reviewRequest.getStatus().intValue() == STATUS.APPLY_REJECT.getKey().intValue() ? merchant.getUsableBalance()
            .add(settlementApply.getAmount()) : merchant.getUsableBalance());*/

        if (reviewRequest.getStatus().intValue() == STATUS.APPLY_ADOPT.getKey().intValue()) {
            saveMerchant.setFrozenBalance(merchant.getFrozenBalance().subtract(settlementApply.getAmount()));
            MerchantBill merchantBill = new MerchantBill();
            merchantBill.setAmount(settlementApply.getAmount());
            merchantBill.setMerchantId(settlementApply.getMerchantId());
            merchantBill.setType(MerchantBill.STATUS.OUT.getKey());
            merchantBill.setTradeType(TRADE_STATUS.SETTLEMENT.getKey());
            merchantBill.setRemark(TRADE_STATUS.SETTLEMENT.getValue());
            merchantBill.setSourceId(Long.valueOf(-1));
            merchantBill.setCreateDate(new Date());
            merchantBill.setUpdateDate(new Date());
            merchantBillDao.insert(merchantBill);
        }else if (reviewRequest.getStatus().intValue() == STATUS.APPLY_REJECT.getKey().intValue()){
            saveMerchant.setFrozenBalance(merchant.getFrozenBalance().subtract(settlementApply.getAmount()));
            saveMerchant.setUsableBalance(merchant.getUsableBalance().add(settlementApply.getAmount()));
        }
        saveMerchant.setUpdateDate(new Date());
        merchantDao.updateByPrimaryKey(saveMerchant);
    }
}

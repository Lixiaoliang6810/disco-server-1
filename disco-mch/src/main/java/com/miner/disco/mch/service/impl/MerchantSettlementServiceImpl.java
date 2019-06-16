package com.miner.disco.mch.service.impl;

import com.miner.disco.mch.dao.MerchantMapper;
import com.miner.disco.mch.dao.MerchantSettlementApplyMapper;
import com.miner.disco.mch.exception.MchBusinessException;
import com.miner.disco.mch.exception.MchBusinessExceptionCode;
import com.miner.disco.mch.model.request.MerchantSettlementApplyRequest;
import com.miner.disco.mch.model.request.MerchantSettlementRecordRequest;
import com.miner.disco.mch.model.response.MerchantSettlementRecordResponse;
import com.miner.disco.mch.service.MerchantSettlementService;
import com.miner.disco.pojo.Merchant;
import com.miner.disco.pojo.MerchantSettlementApply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author Created by lubycoder@163.com 2019-2-21
 */
@Service
public class MerchantSettlementServiceImpl implements MerchantSettlementService {

    @Autowired
    private MerchantMapper merchantMapper;

    @Autowired
    private MerchantSettlementApplyMapper merchantSettlementApplyMapper;

    @Override
    @Transactional(rollbackFor = RuntimeException.class, isolation = Isolation.READ_COMMITTED)
    public void apply(MerchantSettlementApplyRequest request) throws MchBusinessException {
        Merchant merchant = merchantMapper.queryByPrimaryKey(request.getMerchantId());
        if (merchant.getUsableBalance().compareTo(request.getAmount()) < 0) {
            throw new MchBusinessException(MchBusinessExceptionCode.NOT_SUFFICIENT_FUNDS.getCode(), "余额不足");
        }
        merchant = merchantMapper.queryByPrimaryKeyForUpdate(merchant.getId());
        MerchantSettlementApply merchantSettlementApply = new MerchantSettlementApply();
        merchantSettlementApply.setAmount(request.getAmount());
        merchantSettlementApply.setMerchantId(merchant.getId());
        merchantSettlementApply.setStatus(MerchantSettlementApply.STATUS.WAIT_REVIEW.getKey());
        merchantSettlementApply.setCreateDate(new Date());
        merchantSettlementApply.setUpdateDate(new Date());
        merchantSettlementApplyMapper.insert(merchantSettlementApply);

        Merchant saveMerchant = new Merchant();
        saveMerchant.setId(merchant.getId());
        saveMerchant.setUsableBalance(merchant.getUsableBalance().subtract(request.getAmount()));
        saveMerchant.setFrozenBalance(merchant.getFrozenBalance().add(request.getAmount()));
        merchantMapper.updateByPrimaryKey(saveMerchant);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MerchantSettlementRecordResponse> record(MerchantSettlementRecordRequest request) throws MchBusinessException {
        return merchantSettlementApplyMapper.record(request);
    }

}

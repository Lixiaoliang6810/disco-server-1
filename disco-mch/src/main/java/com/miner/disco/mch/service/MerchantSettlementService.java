package com.miner.disco.mch.service;

import com.miner.disco.mch.exception.MchBusinessException;
import com.miner.disco.mch.model.request.MerchantSettlementApplyRequest;
import com.miner.disco.mch.model.request.MerchantSettlementRecordRequest;
import com.miner.disco.mch.model.response.MerchantSettlementRecordResponse;

import java.util.List;

/**
 * @author Created by lubycoder@163.com 2019-2-21
 */
public interface MerchantSettlementService {

    void apply(MerchantSettlementApplyRequest request) throws MchBusinessException;

    List<MerchantSettlementRecordResponse> record(MerchantSettlementRecordRequest request) throws MchBusinessException;

}

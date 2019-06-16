package com.miner.disco.mch.dao;

import com.miner.disco.mch.model.request.MerchantSettlementRecordRequest;
import com.miner.disco.mch.model.response.MerchantSettlementRecordResponse;
import com.miner.disco.mybatis.support.BasicMapper;
import com.miner.disco.pojo.MerchantSettlementApply;

import java.util.List;

/**
 * @author Created by lubycoder@163.com 2019-2-21
 */
public interface MerchantSettlementApplyMapper extends BasicMapper<MerchantSettlementApply> {

    List<MerchantSettlementRecordResponse> record(MerchantSettlementRecordRequest request);

}

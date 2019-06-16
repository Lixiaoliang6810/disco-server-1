package com.miner.disco.mch.service;

import com.miner.disco.mch.exception.MchBusinessException;
import com.miner.disco.mch.model.request.MerchantOfflineBillRequest;
import com.miner.disco.mch.model.response.MerchantBillResponse;
import com.miner.disco.mch.model.response.MerchantOfflineBillResponse;

import java.util.List;

/**
 * @author Created by lubycoder@163.com 2019-2-18
 */
public interface MerchantBillService {

    MerchantBillResponse list(Long merchantId, Integer year, Integer month) throws MchBusinessException;

    List<MerchantOfflineBillResponse> offLineBills(MerchantOfflineBillRequest request) throws MchBusinessException;

}

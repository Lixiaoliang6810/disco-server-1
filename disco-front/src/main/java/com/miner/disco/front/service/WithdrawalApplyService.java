package com.miner.disco.front.service;

import com.miner.disco.front.exception.BusinessException;
import com.miner.disco.front.model.request.WithdrawalApplyRequest;

/**
 * @author Created by lubycoder@163.com 2019/1/9
 */
public interface WithdrawalApplyService {

    void apply(WithdrawalApplyRequest request) throws BusinessException;

}

package com.miner.disco.mch.service;

import com.miner.disco.mch.exception.MchBusinessException;
import com.miner.disco.mch.model.response.IndexResponse;

/**
 * @author Created by lubycoder@163.com 2019/1/9
 */
public interface IndexService {

    IndexResponse index(Long merchantId) throws MchBusinessException;

}

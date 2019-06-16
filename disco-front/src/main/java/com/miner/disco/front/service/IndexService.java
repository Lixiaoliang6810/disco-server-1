package com.miner.disco.front.service;

import com.miner.disco.front.exception.BusinessException;
import com.miner.disco.front.model.response.IndexResponse;

/**
 * @author Created by lubycoder@163.com 2019/1/7
 */
public interface IndexService {

    IndexResponse index() throws BusinessException;

}

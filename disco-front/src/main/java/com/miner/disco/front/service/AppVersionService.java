package com.miner.disco.front.service;

import com.miner.disco.front.exception.BusinessException;
import com.miner.disco.front.model.request.AppVersionCheckRequest;
import com.miner.disco.front.model.response.AppVersionCheckResponse;

/**
 * @author Created by lubycoder@163.com 2019-2-14
 */
public interface AppVersionService {

    AppVersionCheckResponse check(AppVersionCheckRequest request) throws BusinessException;

}

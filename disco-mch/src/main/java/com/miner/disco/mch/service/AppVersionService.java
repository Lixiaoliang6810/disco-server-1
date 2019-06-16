package com.miner.disco.mch.service;

import com.miner.disco.mch.exception.MchBusinessException;
import com.miner.disco.mch.model.request.AppVersionCheckRequest;
import com.miner.disco.mch.model.response.AppVersionCheckResponse;

/**
 * @author Created by lubycoder@163.com 2019-2-14
 */
public interface AppVersionService {

    AppVersionCheckResponse check(AppVersionCheckRequest request) throws MchBusinessException;

}

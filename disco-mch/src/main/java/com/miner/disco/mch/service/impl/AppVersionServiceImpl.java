package com.miner.disco.mch.service.impl;

import com.miner.disco.basic.constants.AppEnum;
import com.miner.disco.basic.constants.BooleanStatus;
import com.miner.disco.mch.dao.AppVersionMapper;
import com.miner.disco.mch.exception.MchBusinessException;
import com.miner.disco.mch.model.request.AppVersionCheckRequest;
import com.miner.disco.mch.model.response.AppVersionCheckResponse;
import com.miner.disco.mch.service.AppVersionService;
import com.miner.disco.pojo.AppVersion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Created by lubycoder@163.com 2019-2-14
 */
@Service
public class AppVersionServiceImpl implements AppVersionService {

    @Autowired
    private AppVersionMapper appVersionMapper;

    @Override
    @Transactional(readOnly = true)
    public AppVersionCheckResponse check(AppVersionCheckRequest request) throws MchBusinessException {
        AppVersionCheckResponse response = new AppVersionCheckResponse();
        request.setApp(AppEnum.MERCHANT);
        AppVersion appVersion = appVersionMapper.queryLatestByAppAndType(request);
        if (appVersion == null) return response;
        if (appVersion.getVersion().compareTo(request.getVersion()) <= 0) return response;
        response.setCompulsion(appVersion.getCompulsion());
        response.setUpgrade(BooleanStatus.YES.getKey());
        switch (request.getType()) {
            case AOS:
                response.setFile(appVersion.getFile());
                return response;
            case IOS:
                return response;
            default:
                return response;
        }
    }
}

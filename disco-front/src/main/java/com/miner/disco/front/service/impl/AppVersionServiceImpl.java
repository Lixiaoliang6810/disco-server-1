package com.miner.disco.front.service.impl;

import com.miner.disco.basic.constants.AppEnum;
import com.miner.disco.basic.constants.BooleanStatus;
import com.miner.disco.front.dao.AppVersionMapper;
import com.miner.disco.front.exception.BusinessException;
import com.miner.disco.front.model.request.AppVersionCheckRequest;
import com.miner.disco.front.model.response.AppVersionCheckResponse;
import com.miner.disco.front.service.AppVersionService;
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
    public AppVersionCheckResponse check(AppVersionCheckRequest request) throws BusinessException {
        AppVersionCheckResponse response = new AppVersionCheckResponse();
        request.setApp(AppEnum.MEMBER);
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

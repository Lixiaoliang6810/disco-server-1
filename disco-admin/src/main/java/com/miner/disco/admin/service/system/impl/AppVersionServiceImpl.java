package com.miner.disco.admin.service.system.impl;

import com.miner.disco.admin.dao.system.AppVersionDao;
import com.miner.disco.admin.exception.AdminBuzException;
import com.miner.disco.admin.exception.AdminBuzExceptionCode;
import com.miner.disco.admin.model.request.system.AppVersionCreateRequest;
import com.miner.disco.admin.model.request.system.AppVersionSearchRequest;
import com.miner.disco.admin.model.response.system.AppVersionListResponse;
import com.miner.disco.admin.service.system.AppVersionService;
import com.miner.disco.basic.assertion.Assert;
import com.miner.disco.basic.model.response.PageResponse;
import com.miner.disco.basic.util.DtoTransition;
import com.miner.disco.pojo.AppVersion;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * author:linjw Date:2019/2/19 Time:11:30
 */
@Service
public class AppVersionServiceImpl implements AppVersionService {

    @Autowired
    private AppVersionDao appVersionDao;

    @Override
    public PageResponse versionList(AppVersionSearchRequest searchRequest) throws AdminBuzException {
        List<AppVersionListResponse> listResponses = appVersionDao.versionList(searchRequest);
        int total = appVersionDao.countVersion(searchRequest);
        return new PageResponse(total, listResponses);
    }

    @Override
    public void createVersion(AppVersionCreateRequest createRequest) throws AdminBuzException {
        AppVersion appVersion = (AppVersion) DtoTransition.trans(AppVersion.class, createRequest);
        Assert.notNull(appVersion, AdminBuzExceptionCode.DATA_CONVERSION_ERROR.getCode());
        appVersion.setCreateDate(new Date());
        appVersion.setUpdateDate(new Date());
        appVersionDao.insert(appVersion);
    }
}

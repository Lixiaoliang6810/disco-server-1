package com.miner.disco.admin.service.system;

import com.miner.disco.admin.exception.AdminBuzException;
import com.miner.disco.admin.model.request.system.AppVersionCreateRequest;
import com.miner.disco.admin.model.request.system.AppVersionSearchRequest;
import com.miner.disco.basic.model.response.PageResponse;

/**
 * author:linjw Date:2019/2/19 Time:11:29
 */
public interface AppVersionService {

    PageResponse versionList(AppVersionSearchRequest searchRequest) throws AdminBuzException;

    void createVersion(AppVersionCreateRequest createRequest) throws AdminBuzException;
}

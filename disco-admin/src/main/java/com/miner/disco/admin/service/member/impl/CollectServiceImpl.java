package com.miner.disco.admin.service.member.impl;

import com.miner.disco.admin.dao.member.CollectDao;
import com.miner.disco.admin.exception.AdminBuzException;
import com.miner.disco.admin.model.request.member.CollectSearchRequest;
import com.miner.disco.admin.model.response.member.CollectListResponse;
import com.miner.disco.admin.service.member.CollectService;
import com.miner.disco.basic.model.response.PageResponse;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * author:linjw Date:2019/1/8 Time:14:41
 */
@Service
public class CollectServiceImpl implements CollectService {

    @Autowired
    private CollectDao collectDao;

    @Override
    public PageResponse collectList(CollectSearchRequest searchRequest) throws AdminBuzException {
        List<CollectListResponse> listResponses = collectDao.collectList(searchRequest);
        int total = collectDao.countCollectList(searchRequest);
        return new PageResponse(total, listResponses);
    }
}

package com.miner.disco.admin.service.member.impl;

import com.miner.disco.admin.dao.member.FollowRecordDao;
import com.miner.disco.admin.exception.AdminBuzException;
import com.miner.disco.admin.model.response.member.FollowRecordListResponse;
import com.miner.disco.admin.service.member.FollowRecordService;
import com.miner.disco.basic.model.request.Pagination;
import com.miner.disco.basic.model.response.PageResponse;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * author:linjw Date:2019/1/7 Time:17:56
 */
@Service
public class FollowRecordServiceImpl implements FollowRecordService {

    @Autowired
    private FollowRecordDao followRecordDao;

    @Override
    public PageResponse idolList(Pagination page, Long id) throws AdminBuzException {
        List<FollowRecordListResponse> listResponses = followRecordDao.idolList(page, id);
        int total = followRecordDao.countIdol(id);
        return new PageResponse(total, listResponses);
    }

    @Override
    public PageResponse fansList(Pagination page, Long id) throws AdminBuzException {
        List<FollowRecordListResponse> listResponses = followRecordDao.fansList(page, id);
        int total = followRecordDao.countFans(id);
        return new PageResponse(total, listResponses);
    }
}

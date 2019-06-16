package com.miner.disco.admin.service.member.impl;

import com.miner.disco.admin.dao.member.DynamicDao;
import com.miner.disco.admin.exception.AdminBuzException;
import com.miner.disco.admin.exception.AdminBuzExceptionCode;
import com.miner.disco.admin.model.request.member.MemberDynamicSearchRequest;
import com.miner.disco.admin.model.response.member.MemberDynamicListResponse;
import com.miner.disco.admin.service.member.DynamicService;
import com.miner.disco.basic.assertion.Assert;
import com.miner.disco.basic.constants.DeleteStatus;
import com.miner.disco.basic.model.response.PageResponse;
import com.miner.disco.pojo.Dynamic;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * author:linjw Date:2019/1/8 Time:10:27
 */
@Service
public class DynamicServiceImpl implements DynamicService {

    @Autowired
    private DynamicDao dynamicDao;

    @Override
    public PageResponse dynamicList(MemberDynamicSearchRequest searchRequest) throws AdminBuzException {
        List<MemberDynamicListResponse> listResponses = dynamicDao.queryMemberDynamicPage(searchRequest);
        int total = 0;
        if (CollectionUtils.isNotEmpty(listResponses)){
            total = dynamicDao.countDynamicPage(searchRequest);
        }
        return new PageResponse(total, listResponses);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = RuntimeException.class)
    public void del(Long id) throws AdminBuzException {
        Dynamic dynamic = dynamicDao.queryByPrimaryKey(id);
        Assert.notNull(dynamic, AdminBuzExceptionCode.OBJECT_NOT_EXIST.getCode(), "错误操作");
        Dynamic del = new Dynamic();
        del.setId(id);
        del.setDeleted(DeleteStatus.DELETE.getKey());
        del.setUpdateDate(new Date());
        dynamicDao.updateByPrimaryKey(del);
    }
}

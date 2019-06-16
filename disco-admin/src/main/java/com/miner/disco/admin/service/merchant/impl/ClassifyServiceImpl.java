package com.miner.disco.admin.service.merchant.impl;

import com.miner.disco.admin.dao.merchant.ClassifyDao;
import com.miner.disco.admin.exception.AdminBuzException;
import com.miner.disco.admin.exception.AdminBuzExceptionCode;
import com.miner.disco.admin.model.request.merchant.ClassifyCreateRequest;
import com.miner.disco.admin.model.request.merchant.ClassifyModifyRequest;
import com.miner.disco.admin.model.response.merchant.ClassifyListResponse;
import com.miner.disco.admin.service.merchant.ClassifyService;
import com.miner.disco.basic.assertion.Assert;
import com.miner.disco.basic.model.request.Pagination;
import com.miner.disco.basic.model.response.PageResponse;
import com.miner.disco.basic.util.DtoTransition;
import com.miner.disco.pojo.Classify;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * author:linjw Date:2019/1/3 Time:15:44
 */
@Service
public class ClassifyServiceImpl implements ClassifyService {

    @Autowired
    private ClassifyDao classifyDao;

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void createClassify(ClassifyCreateRequest createRequest) throws AdminBuzException {
        Classify classify = (Classify) DtoTransition.trans(Classify.class, createRequest);
        Assert.notNull(classify, AdminBuzExceptionCode.DATA_CONVERSION_ERROR.getCode());
        classify.setCreateDate(new Date());
        classify.setUpdateDate(new Date());
        classifyDao.insert(classify);
    }

    @Override
    @Transactional(readOnly = true)
    public Classify queryClassifyDetail(Long id) throws AdminBuzException {
        return classifyDao.queryByPrimaryKey(id);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void modifyClassify(ClassifyModifyRequest modifyRequest) throws AdminBuzException {
        Classify classify = (Classify) DtoTransition.trans(Classify.class, modifyRequest);
        classifyDao.updateByPrimaryKey(classify);
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse queryClassifyList(Pagination page) throws AdminBuzException {
        List<ClassifyListResponse> listResponses = classifyDao.classifyList(page);
        int total = classifyDao.countClassify();
        return new PageResponse(total, listResponses);
    }

    @Override
    public List<ClassifyListResponse> allClassify() throws AdminBuzException {
        return classifyDao.allClassify();
    }
}

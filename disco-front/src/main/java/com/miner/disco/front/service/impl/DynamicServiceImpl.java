package com.miner.disco.front.service.impl;

import com.google.common.collect.Lists;
import com.miner.disco.basic.assertion.Assert;
import com.miner.disco.basic.constants.DeleteStatus;
import com.miner.disco.basic.exception.BasicException;
import com.miner.disco.basic.util.JsonParser;
import com.miner.disco.front.dao.DynamicMapper;
import com.miner.disco.front.exception.BusinessException;
import com.miner.disco.front.model.request.DynamicsListRequest;
import com.miner.disco.front.model.request.MemberPhotosRequest;
import com.miner.disco.front.model.request.ReportRequest;
import com.miner.disco.front.model.response.DynamicsListResponse;
import com.miner.disco.pojo.Dynamic;
import com.miner.disco.front.service.DynamicService;
import com.miner.disco.pojo.Report;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author Created by lubycoder@163.com 2018/12/24
 */
@Slf4j
@Service
public class DynamicServiceImpl implements DynamicService {

    @Autowired
    private DynamicMapper dynamicMapper;

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void publish(Long userId, String content, String images) throws BusinessException {
        Dynamic dynamic = new Dynamic();
        dynamic.setUserId(userId);
        dynamic.setContent(content);
        dynamic.setImages(images);
        dynamic.setDeleted(DeleteStatus.NORMAL.getKey());
        dynamic.setCreateDate(new Date());
        dynamic.setUpdateDate(new Date());
        dynamicMapper.insert(dynamic);
    }


    @Override
    @Transactional(readOnly = true)
    public List<DynamicsListResponse> list(DynamicsListRequest request) throws BusinessException {
        List<DynamicsListResponse> list = dynamicMapper.queryList(request);
        return list;
    }

    @Override
    public List<String> photos(MemberPhotosRequest request) throws BusinessException {
        log.error("uid {} offset {} limit {}", request.getUserId(), request.getOffset(), request.getLimit());
        List<String> images = dynamicMapper.queryImagesByUserId(request.getUserId());
        if (images.isEmpty()) return Lists.newArrayList();
        List<String> temps = Lists.newArrayList();
        images.forEach(img -> {
            if (StringUtils.isNotBlank(img)) temps.addAll(JsonParser.deserializeByJson(img, List.class));
        });
        if (temps.isEmpty()) return temps;
        if (request.getOffset() <= 0 && temps.size() <= request.getLimit()) return temps;
        if (temps.size() <= request.getOffset()) return Lists.newArrayList();
        int index = request.getOffset() + request.getLimit();
        return temps.subList(request.getOffset(), temps.size() < index ? temps.size() - 1 : index);
    }



    @Override
    public void del(Long id) {
        Dynamic dynamic = dynamicMapper.queryByPrimaryKey(id);
        Assert.notNull(dynamic,new BasicException("该动态已删除"));
        Dynamic del = new Dynamic();
        del.setId(id);
        del.setDeleted(DeleteStatus.DELETE.getKey());
        del.setUpdateDate(new Date());
        dynamicMapper.updateByPrimaryKey(del);
    }
}

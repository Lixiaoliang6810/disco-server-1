package com.miner.disco.mch.service.impl;

import com.miner.disco.mch.dao.ClassifyMapper;
import com.miner.disco.mch.exception.MchBusinessException;
import com.miner.disco.mch.model.response.ClassifySelectorResponse;
import com.miner.disco.mch.service.ClassifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Created by lubycoder@163.com 2019-2-19
 */
@Service
public class ClassifyServiceImpl implements ClassifyService {

    @Autowired
    private ClassifyMapper classifyMapper;

    @Override
    @Transactional(readOnly = true)
    public List<ClassifySelectorResponse> selector() throws MchBusinessException {
        return classifyMapper.queryAllEffective();
    }

}

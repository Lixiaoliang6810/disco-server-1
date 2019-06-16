package com.miner.disco.admin.service.system.impl;

import com.miner.disco.admin.dao.system.ConfigDao;
import com.miner.disco.admin.exception.AdminBuzException;
import com.miner.disco.admin.exception.AdminBuzExceptionCode;
import com.miner.disco.admin.model.request.system.ConfigCreateRequest;
import com.miner.disco.admin.model.request.system.ConfigModifyRequest;
import com.miner.disco.admin.model.response.system.ConfigListResponse;
import com.miner.disco.admin.service.system.ConfigService;
import com.miner.disco.basic.assertion.Assert;
import com.miner.disco.basic.model.request.Pagination;
import com.miner.disco.basic.model.response.PageResponse;
import com.miner.disco.basic.util.DtoTransition;
import com.miner.disco.pojo.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * author:linjw Date:2019/1/7 Time:20:01
 */
@Service
public class ConfigServiceImpl implements ConfigService {

    @Autowired
    private ConfigDao configDao;

    @Override
    public PageResponse configList(Pagination pagination) throws AdminBuzException {
        List<ConfigListResponse> listResponses = configDao.configList(pagination);
        int total = configDao.countConfigList();
        return new PageResponse(total, listResponses);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = RuntimeException.class)
    public void addConfig(ConfigCreateRequest configCreateRequest) throws AdminBuzException {
        Config config = (Config) DtoTransition.trans(Config.class, configCreateRequest);
        Assert.notNull(config, AdminBuzExceptionCode.DATA_CONVERSION_ERROR.getCode());
        config.setCreateDate(new Date());
        config.setUpdateDate(new Date());
        configDao.insert(config);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = RuntimeException.class)
    public void update(ConfigModifyRequest configModifyRequest) throws AdminBuzException {
        Config config = configDao.queryByPrimaryKey(configModifyRequest.getId());
        Assert.notNull(config, AdminBuzExceptionCode.OBJECT_NOT_EXIST.getCode());
        Config saveConfig = (Config) DtoTransition.trans(config.getClass(), configModifyRequest);
        Assert.notNull(saveConfig, AdminBuzExceptionCode.DATA_CONVERSION_ERROR.getCode());
        saveConfig.setUpdateDate(new Date());
        configDao.updateByPrimaryKey(saveConfig);
    }

    @Override
    public Config configDetail(Long id) throws AdminBuzException {
        return configDao.queryByPrimaryKey(id);
    }
}

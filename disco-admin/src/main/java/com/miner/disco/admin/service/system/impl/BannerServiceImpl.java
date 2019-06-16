package com.miner.disco.admin.service.system.impl;

import com.miner.disco.admin.dao.system.BannerDao;
import com.miner.disco.admin.exception.AdminBuzException;
import com.miner.disco.admin.exception.AdminBuzExceptionCode;
import com.miner.disco.admin.model.request.system.BannerCreateRequest;
import com.miner.disco.admin.model.request.system.BannerModifyRequest;
import com.miner.disco.admin.model.request.system.BannerSearchRequest;
import com.miner.disco.admin.model.response.system.BannerListResponse;
import com.miner.disco.admin.service.system.BannerService;
import com.miner.disco.basic.assertion.Assert;
import com.miner.disco.basic.constants.DeleteStatus;
import com.miner.disco.basic.model.response.PageResponse;
import com.miner.disco.basic.util.DtoTransition;
import com.miner.disco.pojo.Banner;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * author:linjw Date:2019/1/7 Time:19:20
 */
@Service
public class BannerServiceImpl implements BannerService {

    @Autowired
    private BannerDao bannerDao;

    @Override
    public PageResponse list(BannerSearchRequest search) throws AdminBuzException {
        List<BannerListResponse> listResponses = bannerDao.bannerList(search);
        int total = 0;
        if (CollectionUtils.isNotEmpty(listResponses)) {
            total = bannerDao.countBannerList(search);
        }
        return PageResponse.builder().list(listResponses).total(total).build();
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = RuntimeException.class)
    public void add(BannerCreateRequest bannerCreateRequest) throws AdminBuzException {
        Banner banner = (Banner) DtoTransition.trans(Banner.class, bannerCreateRequest);
        Assert.notNull(banner, AdminBuzExceptionCode.DATA_CONVERSION_ERROR.getCode());
        banner.setCreateDate(new Date());
        banner.setUpdateDate(new Date());
        banner.setDeleted(DeleteStatus.NORMAL.getKey());
        bannerDao.insert(banner);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = RuntimeException.class)
    public void update(BannerModifyRequest bannerModifyRequest) throws AdminBuzException {
        Banner banner = bannerDao.queryByPrimaryKey(bannerModifyRequest.getId());
        Assert.notNull(banner, AdminBuzExceptionCode.OBJECT_NOT_EXIST.getCode());
        Banner saveBanner = (Banner) DtoTransition.trans(Banner.class, bannerModifyRequest);
        Assert.notNull(saveBanner, AdminBuzExceptionCode.DATA_CONVERSION_ERROR.getCode());
        saveBanner.setUpdateDate(new Date());
        bannerDao.updateByPrimaryKey(saveBanner);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = RuntimeException.class)
    public void delete(Long id) throws AdminBuzException {
        Banner banner = bannerDao.queryByPrimaryKey(id);
        Assert.notNull(banner, AdminBuzExceptionCode.OBJECT_NOT_EXIST.getCode());
        Banner delBanner = new Banner();
        delBanner.setId(id);
        delBanner.setDeleted(DeleteStatus.DELETE.getKey());
        delBanner.setUpdateDate(new Date());
        bannerDao.updateByPrimaryKey(delBanner);
    }
}

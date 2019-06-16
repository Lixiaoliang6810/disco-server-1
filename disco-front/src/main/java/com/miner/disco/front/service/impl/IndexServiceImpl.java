package com.miner.disco.front.service.impl;

import com.miner.disco.front.dao.BannerMapper;
import com.miner.disco.front.dao.ClassifyMapper;
import com.miner.disco.front.dao.MerchantMapper;
import com.miner.disco.front.exception.BusinessException;
import com.miner.disco.front.model.response.BannerListResponse;
import com.miner.disco.front.model.response.ClassifyListResponse;
import com.miner.disco.front.model.response.IndexResponse;
import com.miner.disco.front.model.response.MerchantListResponse;
import com.miner.disco.front.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Created by lubycoder@163.com 2019/1/7
 */
@Service
public class IndexServiceImpl implements IndexService {

    @Autowired
    private BannerMapper bannerMapper;

    @Autowired
    private ClassifyMapper classifyMapper;

    @Autowired
    private MerchantMapper merchantMapper;

    @Override
    public IndexResponse index() throws BusinessException {
        List<BannerListResponse> banner = bannerMapper.queryAllEffective();
        List<ClassifyListResponse> classify = classifyMapper.queryAllEffective();
        List<MerchantListResponse> merchant = merchantMapper.queryRecommend();
        IndexResponse indexResponse = new IndexResponse();
        indexResponse.setBanner(banner);
        indexResponse.setClassify(classify);
        indexResponse.setMerchant(merchant);
        return indexResponse;
    }
}

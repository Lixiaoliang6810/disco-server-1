package com.miner.disco.front.service.impl;

import com.google.common.collect.Lists;
import com.miner.disco.basic.assertion.Assert;
import com.miner.disco.basic.constants.BasicConst;
import com.miner.disco.basic.constants.BooleanStatus;
import com.miner.disco.basic.util.DtoTransition;
import com.miner.disco.front.dao.CollectMapper;
import com.miner.disco.front.dao.MerchantEvaluateMapper;
import com.miner.disco.front.dao.MerchantMapper;
import com.miner.disco.front.exception.BusinessException;
import com.miner.disco.front.exception.BusinessExceptionCode;
import com.miner.disco.front.model.request.MerchantListRequest;
import com.miner.disco.front.model.response.MerchantDetailsResponse;
import com.miner.disco.front.model.response.MerchantListResponse;
import com.miner.disco.pojo.Collect;
import com.miner.disco.pojo.Merchant;
import com.miner.disco.front.service.MerchantService;
import com.spatial4j.core.context.SpatialContext;
import com.spatial4j.core.distance.DistanceUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Created by lubycoder@163.com 2018/12/24
 */
@Slf4j
@Service
public class MerchantServiceImpl implements MerchantService {

    @Autowired
    private MerchantMapper merchantMapper;

    @Autowired
    private CollectMapper collectMapper;

    @Autowired
    private MerchantEvaluateMapper merchantEvaluateMapper;

    @Resource(name = "redisTemplate")
    private ValueOperations<String, String> vOps;

    @Override
    public MerchantDetailsResponse details(Long userId, Long merchantId) throws BusinessException {
        Merchant merchant = merchantMapper.queryByPrimaryKey(merchantId);
        Assert.notNull(merchant, BusinessExceptionCode.OBJECT_NOT_FOUND.getCode(), "商家不存在");
        MerchantDetailsResponse response = (MerchantDetailsResponse) DtoTransition.trans(MerchantDetailsResponse.class, merchant);
        Assert.notNull(response, BusinessExceptionCode.OBJECT_CONVERSION_ERROR.getCode(), "数据转换错误");
        Collect collect = collectMapper.queryByMerchantIdAndUserId(merchant.getId(), userId);
        response.setCollected(collect == null ? BooleanStatus.NO.getKey() : BooleanStatus.YES.getKey());
        Integer totalEvaluate = merchantEvaluateMapper.countByMerchantId(merchantId);
        response.setTotalEvaluateCount(totalEvaluate != null ? totalEvaluate : 0);
        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DATE);
        vOps.increment(String.format(BasicConst.REDIS_CACHE_MERCHANT_BROWSE_VOLUME, merchantId), 1L);
        vOps.increment(String.format(BasicConst.REDIS_CACHE_MERCHANT_DAY_BROWSE_VOLUME, day, merchantId), 1L);
        vOps.increment(String.format(BasicConst.REDIS_CACHE_MERCHANT_MONTH_BROWSE_VOLUME, month, merchantId), 1L);
        return response;
    }

    @Override
    public List<MerchantListResponse> list(MerchantListRequest request) throws BusinessException {
        log.error("offset {} limit {}", request.getOffset(), request.getLimit());
        List<MerchantListResponse> responses = merchantMapper.search(request);
        DecimalFormat df = new DecimalFormat("######0.00");
        SpatialContext spatialContext = SpatialContext.GEO;
        responses.forEach(m -> {
            Double distance = spatialContext.calcDistance(spatialContext.makePoint(request.getLongitude(), request.getLatitude()),
                    spatialContext.makePoint(m.getLongitude(), m.getLatitude())) * DistanceUtils.DEG_TO_KM;
            m.setDistance(Double.valueOf(df.format(distance)));
        });
        responses = responses.stream()
                .filter(m -> {
                    if (request.getDistance() == -1) {
                        return m.getDistance() > request.getDistance();
                    }
                    return m.getDistance() <= request.getDistance();
                }).collect(Collectors.toList());
        responses.sort(request.getSort().getComparable());
        if (responses.isEmpty()) return responses;
        if (request.getOffset() <= 0 && responses.size() <= request.getLimit()) return responses;
        if (responses.size() <= request.getOffset()) return Lists.newArrayList();
        int index = request.getOffset() + request.getLimit();
        return responses.subList(request.getOffset(), responses.size() < index ? responses.size() - 1 : index);
    }
}

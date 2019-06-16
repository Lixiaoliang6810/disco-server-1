package com.miner.disco.front.service.impl;

import com.miner.disco.basic.assertion.Assert;
import com.miner.disco.basic.util.DtoTransition;
import com.miner.disco.front.dao.FeedbackMapper;
import com.miner.disco.front.exception.BusinessException;
import com.miner.disco.front.exception.BusinessExceptionCode;
import com.miner.disco.front.model.request.FeedbackRequest;
import com.miner.disco.front.service.FeedbackService;
import com.miner.disco.pojo.Feedback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author Created by lubycoder@163.com 2019-2-14
 */
@Service
public class FeedbackServiceImpl implements FeedbackService {

    @Autowired
    private FeedbackMapper feedbackMapper;

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void feedback(FeedbackRequest request) throws BusinessException {
        Feedback feedback = (Feedback) DtoTransition.trans(Feedback.class, request);
        Assert.notNull(feedback, BusinessExceptionCode.OBJECT_CONVERSION_ERROR.getCode(), "数据转换错误");
        feedback.setCreateDate(new Date());
        feedback.setUpdateDate(new Date());
        feedbackMapper.insert(feedback);
    }

}

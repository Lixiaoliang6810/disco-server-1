package com.miner.disco.admin.service.system.impl;

import com.miner.disco.admin.dao.system.FeedbackDao;
import com.miner.disco.admin.exception.AdminBuzException;
import com.miner.disco.admin.model.request.system.FeedbackSearchRequest;
import com.miner.disco.admin.model.response.system.FeedbackListResponse;
import com.miner.disco.admin.service.system.FeedbackService;
import com.miner.disco.basic.model.response.PageResponse;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * author:linjw Date:2019/2/19 Time:17:46
 */
@Service
public class FeedbackServiceImpl implements FeedbackService {

    @Autowired
    private FeedbackDao feedbackDao;

    @Override
    public PageResponse FeedbackList(FeedbackSearchRequest searchRequest) throws AdminBuzException {
        List<FeedbackListResponse> listResponses = feedbackDao.feedbackList(searchRequest);
        int total = feedbackDao.countFeedback(searchRequest);
        return new PageResponse(total, listResponses);
    }
}

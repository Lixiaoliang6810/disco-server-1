package com.miner.disco.front.service;

import com.miner.disco.front.exception.BusinessException;
import com.miner.disco.front.model.request.FeedbackRequest;

/**
 * @author Created by lubycoder@163.com 2019-2-14
 */
public interface FeedbackService {

    void feedback(FeedbackRequest request) throws BusinessException;

}

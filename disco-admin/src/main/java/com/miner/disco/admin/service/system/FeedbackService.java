package com.miner.disco.admin.service.system;

import com.miner.disco.admin.exception.AdminBuzException;
import com.miner.disco.admin.model.request.system.FeedbackSearchRequest;
import com.miner.disco.basic.model.response.PageResponse;

/**
 * author:linjw Date:2019/2/19 Time:17:45
 */
public interface FeedbackService {

    PageResponse FeedbackList(FeedbackSearchRequest searchRequest) throws AdminBuzException;

}

package com.miner.disco.admin.dao.system;

import com.miner.disco.admin.model.request.system.FeedbackSearchRequest;
import com.miner.disco.admin.model.response.system.FeedbackListResponse;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * author:linjw Date:2019/2/19 Time:17:04
 */
public interface FeedbackDao {

    List<FeedbackListResponse> feedbackList(@Param(value = "search") FeedbackSearchRequest search);

    int countFeedback(@Param(value = "search") FeedbackSearchRequest search);
}

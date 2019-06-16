package com.miner.disco.admin.controller;

import com.miner.disco.admin.auth.annotation.Module;
import com.miner.disco.admin.constant.module.admin.FeedbackManager;
import com.miner.disco.admin.model.request.system.FeedbackSearchRequest;
import com.miner.disco.admin.service.system.FeedbackService;
import com.miner.disco.basic.model.response.PageResponse;
import com.miner.disco.basic.model.response.ViewData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * author:linjw Date:2019/2/19 Time:17:48
 */
@RestController
@RequestMapping(value = "/system/feedback")
@Module(FeedbackManager.class)
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    @RequestMapping(value = "/list")
    public ViewData feedbackList(FeedbackSearchRequest feedbackSearchRequest) {
        PageResponse pageResponse = feedbackService.FeedbackList(feedbackSearchRequest);
        return ViewData.builder().data(pageResponse).total(pageResponse.getTotal()).message("获取意见反馈成功").build();
    }
}

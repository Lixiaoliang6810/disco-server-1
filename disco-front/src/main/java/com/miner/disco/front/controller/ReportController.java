package com.miner.disco.front.controller;

import com.miner.disco.basic.model.response.ViewData;
import com.miner.disco.front.consts.Const;
import com.miner.disco.front.model.request.ReportRequest;
import com.miner.disco.front.oauth.model.CustomUserDetails;
import com.miner.disco.front.service.ReportService;
import com.miner.disco.front.service.impl.ReportServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName ReportController
 * @Author: wz1016_vip@163.com
 * @Date: 2019/6/16 17:05
 * @Description: TODO
 */
@RestController
public class ReportController {

    private final ReportService reportService;

    @Autowired
    public ReportController(ReportServiceImpl reportService){
        this.reportService = reportService;
    }

    /**
     * 用户动态举报
     * @param oAuth2Authentication 举报人信息
     * @param request              被举报人信息
     * @return
     * 举报信息表： 举报人id，被举报人id，举报内容，举报截图，时间
     */
    @PostMapping(value = "/dynamic/report", headers = Const.API_VERSION_1_0_0)
    public ViewData report(@AuthenticationPrincipal OAuth2Authentication oAuth2Authentication,
                           ReportRequest request) {
        Long reporterId = ((CustomUserDetails) oAuth2Authentication.getPrincipal()).getId();
        reportService.report(reporterId,request);
        return ViewData.builder().message("举报成功").build();
    }

}

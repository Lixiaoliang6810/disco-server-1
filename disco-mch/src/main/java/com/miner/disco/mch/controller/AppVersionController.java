package com.miner.disco.mch.controller;

import com.miner.disco.basic.model.response.ViewData;
import com.miner.disco.mch.consts.Const;
import com.miner.disco.mch.model.request.AppVersionCheckRequest;
import com.miner.disco.mch.model.response.AppVersionCheckResponse;
import com.miner.disco.mch.service.AppVersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Created by lubycoder@163.com 2019-2-14
 */
@RestController
public class AppVersionController {

    @Autowired
    private AppVersionService appVersionService;

    @GetMapping(value = "/version/check", headers = Const.API_VERSION_1_0_0)
    public ViewData check(AppVersionCheckRequest request) {
        AppVersionCheckResponse response = appVersionService.check(request);
        return ViewData.builder().data(response).message("版本检测").build();
    }

}

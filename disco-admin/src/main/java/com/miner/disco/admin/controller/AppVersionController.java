package com.miner.disco.admin.controller;

import com.miner.disco.admin.auth.annotation.Func;
import com.miner.disco.admin.auth.annotation.Module;
import com.miner.disco.admin.constant.function.Add;
import com.miner.disco.admin.constant.module.admin.AppVersionManager;
import com.miner.disco.admin.constant.module.admin.BannerManager;
import com.miner.disco.admin.model.request.system.AppVersionCreateRequest;
import com.miner.disco.admin.model.request.system.AppVersionSearchRequest;
import com.miner.disco.admin.service.system.AppVersionService;
import com.miner.disco.basic.model.response.PageResponse;
import com.miner.disco.basic.model.response.ViewData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * author:linjw Date:2019/2/19 Time:11:42
 */
@RestController
@RequestMapping("/system/version")
@Module(AppVersionManager.class)
public class AppVersionController {

    @Autowired
    private AppVersionService appVersionService;

    @RequestMapping(value = "/list")
    public ViewData versionList(AppVersionSearchRequest searchRequest) {
        PageResponse pageResponse = appVersionService.versionList(searchRequest);
        return ViewData.builder().data(pageResponse).total(pageResponse.getTotal()).message("查询版本列表成功").build();
    }

    @PostMapping(value = "/create")
    @Func(Add.class)
    public ViewData createVersion(AppVersionCreateRequest createRequest) {
        appVersionService.createVersion(createRequest);
        return ViewData.builder().message("新增版本成功").build();
    }

}

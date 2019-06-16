package com.miner.disco.admin.controller;

import com.miner.disco.admin.auth.annotation.Func;
import com.miner.disco.admin.auth.annotation.Module;
import com.miner.disco.admin.constant.function.Add;
import com.miner.disco.admin.constant.function.Edit;
import com.miner.disco.admin.constant.module.admin.ConfigManager;
import com.miner.disco.admin.model.request.system.ConfigCreateRequest;
import com.miner.disco.admin.model.request.system.ConfigModifyRequest;
import com.miner.disco.admin.service.system.ConfigService;
import com.miner.disco.basic.model.request.Pagination;
import com.miner.disco.basic.model.response.PageResponse;
import com.miner.disco.basic.model.response.ViewData;
import com.miner.disco.pojo.Config;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 系统配置管理 author:linjw Date:2019/1/7 Time:20:09
 */
@RestController
@Module(ConfigManager.class)
public class ConfigController {

    @Autowired
    private ConfigService configService;

    @GetMapping(value = "/system/config/list")
    public ViewData configList(Pagination pagination) {
        PageResponse pageResponse = configService.configList(pagination);
        return ViewData.builder().data(pageResponse.getList()).total(pageResponse.getTotal()).message("配置列表").build();
    }

    @PostMapping(value = "/system/config/add")
    @Func(Add.class)
    public ViewData addConfig(ConfigCreateRequest configCreateRequest) {
        configService.addConfig(configCreateRequest);
        return ViewData.builder().message("新增配置").build();
    }

    @PostMapping(value = "/system/config/update")
    @Func(Edit.class)
    public ViewData updateConfig(ConfigModifyRequest configModifyRequest) {
        configService.update(configModifyRequest);
        return ViewData.builder().message("修改配置").build();
    }

    @GetMapping(value = "/system/config/detail")
    public ViewData configDetail(@Param(value = "id") Long id) {
        Config config = configService.configDetail(id);
        return ViewData.builder().data(config).message("配置详情").build();
    }
}

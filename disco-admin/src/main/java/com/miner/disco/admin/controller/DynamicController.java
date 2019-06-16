package com.miner.disco.admin.controller;

import com.miner.disco.admin.auth.annotation.Func;
import com.miner.disco.admin.auth.annotation.Module;
import com.miner.disco.admin.constant.function.Delete;
import com.miner.disco.admin.constant.module.member.DynamicManager;
import com.miner.disco.admin.model.request.member.MemberDynamicSearchRequest;
import com.miner.disco.admin.service.member.DynamicService;
import com.miner.disco.basic.model.response.PageResponse;
import com.miner.disco.basic.model.response.ViewData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户动态管理
 * author:linjw Date:2019/1/8 Time:10:30
 */
@RestController
@Module(DynamicManager.class)
public class DynamicController {

    @Autowired
    private DynamicService dynamicService;

    @GetMapping(value = "/member/dynamic/list")
    public ViewData dynamicList(@Validated MemberDynamicSearchRequest memberDynamicSearchRequest) {
        PageResponse pageResponse = dynamicService.dynamicList(memberDynamicSearchRequest);
        return ViewData.builder().data(pageResponse).message("获取动态列表").build();
    }

    @DeleteMapping(value = "/member/dynamic/del")
    @Func(Delete.class)
    public ViewData del(@RequestParam("id") Long id) {
        dynamicService.del(id);
        return ViewData.builder().message("删除动态成功").build();
    }
}

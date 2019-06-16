package com.miner.disco.admin.controller;

import com.miner.disco.admin.model.request.member.CollectSearchRequest;
import com.miner.disco.admin.service.member.CollectService;
import com.miner.disco.basic.model.response.PageResponse;
import com.miner.disco.basic.model.response.ViewData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户收藏
 * author:linjw Date:2019/1/8 Time:14:42
 */
@RestController
public class CollectController {

    @Autowired
    private CollectService collectService;

    @GetMapping(value = "/member/collect/list")
    public ViewData collectList(CollectSearchRequest searchRequest) {
        PageResponse pageResponse = collectService.collectList(searchRequest);
        return ViewData.builder().data(pageResponse.getList()).total(pageResponse.getTotal()).message("收藏列表").build();
    }
}

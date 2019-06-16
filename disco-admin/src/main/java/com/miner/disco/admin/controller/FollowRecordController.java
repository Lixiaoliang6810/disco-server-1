package com.miner.disco.admin.controller;

import com.miner.disco.admin.service.member.FollowRecordService;
import com.miner.disco.basic.model.request.Pagination;
import com.miner.disco.basic.model.response.PageResponse;
import com.miner.disco.basic.model.response.ViewData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户关注
 * author:linjw Date:2019/1/7 Time:18:24
 */
@RestController
public class FollowRecordController {

    @Autowired
    private FollowRecordService followRecordService;

    @GetMapping(value = "/member/idol/list")
    public ViewData idolList(Pagination pagination,Long id){
        PageResponse pageResponse = followRecordService.idolList(pagination,id);
        return ViewData.builder().data(pageResponse.getList()).total(pageResponse.getTotal()).message("偶像列表").build();
    }

    @GetMapping(value = "/member/fans/list")
    public ViewData fansList(Pagination pagination,Long id){
        PageResponse pageResponse = followRecordService.fansList(pagination,id);
        return ViewData.builder().data(pageResponse.getList()).total(pageResponse.getTotal()).message("粉丝列表").build();
    }

}

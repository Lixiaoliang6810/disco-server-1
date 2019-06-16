package com.miner.disco.admin.controller;

import com.miner.disco.admin.auth.annotation.Func;
import com.miner.disco.admin.auth.annotation.Module;
import com.miner.disco.admin.constant.function.Add;
import com.miner.disco.admin.constant.function.Delete;
import com.miner.disco.admin.constant.function.Edit;
import com.miner.disco.admin.constant.module.admin.BannerManager;
import com.miner.disco.admin.model.request.system.BannerCreateRequest;
import com.miner.disco.admin.model.request.system.BannerModifyRequest;
import com.miner.disco.admin.model.request.system.BannerSearchRequest;
import com.miner.disco.admin.service.system.BannerService;
import com.miner.disco.basic.model.response.PageResponse;
import com.miner.disco.basic.model.response.ViewData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * banner管理
 * author:linjw Date:2019/1/7 Time:19:42
 */
@RestController
@RequestMapping("/system/banner")
@Module(BannerManager.class)
public class BannerController {

    @Autowired
    private BannerService bannerService;

    @GetMapping
    public ViewData list(BannerSearchRequest search) {
        PageResponse pageResponse = bannerService.list(search);
        return ViewData.builder().data(pageResponse).total(pageResponse.getTotal()).message("广告列表").build();
    }

    @PostMapping
    @Func(Add.class)
    public ViewData add(@RequestBody BannerCreateRequest bannerCreateRequest) {
        bannerService.add(bannerCreateRequest);
        return ViewData.builder().message("新增广告成功").build();
    }

    @PutMapping
    @Func(Edit.class)
    public ViewData update(@RequestBody BannerModifyRequest bannerModifyRequest) {
        bannerService.update(bannerModifyRequest);
        return ViewData.builder().message("修改广告成功").build();
    }

    @DeleteMapping
    @Func(Delete.class)
    public ViewData delete(@RequestParam("id") Long id){
        bannerService.delete(id);
        return ViewData.builder().message("删除广告成功").build();
    }
}

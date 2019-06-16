package com.miner.disco.admin.controller;

import com.miner.disco.admin.auth.annotation.Func;
import com.miner.disco.admin.auth.annotation.Module;
import com.miner.disco.admin.constant.function.Add;
import com.miner.disco.admin.constant.function.Edit;
import com.miner.disco.admin.constant.module.admin.UserManager;
import com.miner.disco.admin.constant.module.merchant.MerchantClassify;
import com.miner.disco.admin.model.request.merchant.ClassifyCreateRequest;
import com.miner.disco.admin.model.request.merchant.ClassifyModifyRequest;
import com.miner.disco.admin.model.response.merchant.ClassifyListResponse;
import com.miner.disco.admin.service.merchant.ClassifyService;
import com.miner.disco.basic.model.request.Pagination;
import com.miner.disco.basic.model.response.PageResponse;
import com.miner.disco.basic.model.response.ViewData;
import com.miner.disco.pojo.Classify;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 分类管理 author:linjw Date:2019/1/4 Time:10:57
 */
@RestController
@Module(MerchantClassify.class)
public class ClassifyController {

    @Autowired
    private ClassifyService classifyService;

    @PostMapping(value = "/classify/create")
    @Func(Add.class)
    public ViewData createClassify(ClassifyCreateRequest createRequest) {
        classifyService.createClassify(createRequest);
        return ViewData.builder().message("创建分类").build();
    }

    @GetMapping(value = "/classify/list")
    public ViewData ClassifyList(Pagination pagination) {
        PageResponse pageResponse = classifyService.queryClassifyList(pagination);
        return ViewData.builder().data(pageResponse.getList()).total(pageResponse.getTotal()).message("分类列表").build();
    }

    @GetMapping(value = "/classify/detail")
    public ViewData classifyDetail(Long id) {
        Classify classify = classifyService.queryClassifyDetail(id);
        return ViewData.builder().data(classify).message("分类详情").build();
    }

    @PostMapping(value = "/classify/modify")
    @Func(Edit.class)
    public ViewData modifyClassify(ClassifyModifyRequest modifyRequest) {
        classifyService.modifyClassify(modifyRequest);
        return ViewData.builder().message("修改分类").build();
    }

    @GetMapping(value = "/classify/all")
    public ViewData allClassify() {
        List<ClassifyListResponse> listResponses = classifyService.allClassify();
        return ViewData.builder().data(listResponses).message("所有分类").build();
    }
}

package com.miner.disco.mch.controller;

import com.miner.disco.basic.model.response.ViewData;
import com.miner.disco.mch.consts.Const;
import com.miner.disco.mch.model.response.ClassifySelectorResponse;
import com.miner.disco.mch.service.ClassifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Created by lubycoder@163.com 2019-2-19
 */
@RestController
public class ClassifyController {

    @Autowired
    private ClassifyService classifyService;

    @GetMapping(value = "/classify/selector", headers = Const.API_VERSION_1_0_0)
    public ViewData selector() {
        List<ClassifySelectorResponse> response = classifyService.selector();
        return ViewData.builder().data(response).message("商家分类").build();
    }

}

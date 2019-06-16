package com.miner.disco.front.controller;

import com.miner.disco.basic.model.response.ViewData;
import com.miner.disco.front.model.response.IndexResponse;
import com.miner.disco.front.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Created by lubycoder@163.com 2019/1/7
 */
@RestController
public class IndexController {

    @Autowired
    private IndexService indexService;

    @GetMapping(value = "/index")
    public ViewData index() {
        IndexResponse response = indexService.index();
        return ViewData.builder().data(response).message("首页").build();
    }

}

package com.miner.disco.admin.controller;

import com.aliyuncs.exceptions.ClientException;
import com.miner.disco.basic.model.response.ViewData;
import com.miner.disco.oss.support.AliSts;
import com.miner.disco.oss.support.OSSSTSResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Created by lubycoder@163.com 2018/12/24
 */
@Slf4j
@RestController
public class OssController {

    @Autowired
    private AliSts aliSts;

    @GetMapping(value = "/oss/authorize")
    public ViewData ossAuthorize() {
        try {
            OSSSTSResponse response = aliSts.authorize();
            return ViewData.builder().data(response).build();
        } catch (ClientException e) {
            log.error("ali oss sts authorize error", e);
            return ViewData.builder().build();
        }

    }

}

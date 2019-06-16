package com.miner.disco.admin.controller;

import com.miner.disco.admin.model.request.member.EmergencyContactSearchRequest;
import com.miner.disco.admin.service.member.EmergencyContactService;
import com.miner.disco.basic.model.response.PageResponse;
import com.miner.disco.basic.model.response.ViewData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 紧急联系人管理
 * author:linjw Date:2019/1/8 Time:11:04
 */
@RestController
public class EmergencyContact {

    @Autowired
    private EmergencyContactService emergencyContactService;

    @GetMapping(value = "/member/emergency/list")
    public ViewData emergencyContactList(EmergencyContactSearchRequest searchRequest) {
        PageResponse pageResponse = emergencyContactService.emergencyContactList(searchRequest);
        return ViewData.builder().data(pageResponse.getList()).total(pageResponse.getTotal()).message("紧急联系人列表").build();
    }
}

package com.miner.disco.front.controller;

import com.miner.disco.basic.model.response.ViewData;
import com.miner.disco.basic.util.JsonParser;
import com.miner.disco.front.consts.Const;
import com.miner.disco.front.exception.BusinessExceptionCode;
import com.miner.disco.front.model.request.DynamicsListRequest;
import com.miner.disco.front.model.request.MemberPhotosRequest;
import com.miner.disco.front.model.response.DynamicsListResponse;
import com.miner.disco.front.oauth.model.CustomUserDetails;
import com.miner.disco.front.service.DynamicService;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Created by lubycoder@163.com 2018/12/24
 */
@RestController
public class DynamicController {

    @Autowired
    private DynamicService dynamicService;

    @PostMapping(value = "/dynamic/publish", headers = Const.API_VERSION_1_0_0)
    public ViewData publishDynamics(@AuthenticationPrincipal OAuth2Authentication oAuth2Authentication,
                                    @RequestParam(value = "content", required = false) String content,
                                    @RequestParam(value = "images", required = false) String images) {
        Long uid = ((CustomUserDetails) oAuth2Authentication.getPrincipal()).getId();
        if (StringUtils.isBlank(content) && StringUtils.isBlank(images)) {
            return ViewData.builder().data(BusinessExceptionCode.PARAMETER_ERROR).message("参数错误").build();
        }
        if (StringUtils.isNotBlank(images) && JsonParser.deserializeByJson(images, List.class).size() > 9) {
            return ViewData.builder().data(BusinessExceptionCode.PARAMETER_ERROR).message("最多只能上传9张图片").build();
        }
        dynamicService.publish(uid, content, images);
        return ViewData.builder().message("发布成功").build();
    }

    @GetMapping(value = "/dynamic/list", headers = Const.API_VERSION_1_0_0)
    public ViewData dynamicsList(DynamicsListRequest request) {
        List<DynamicsListResponse> response = dynamicService.list(request);
        return ViewData.builder().data(response).message("动态列表").build();
    }
    @PostMapping("/dynamic/delete")
    public ViewData dynamicDel(@RequestParam("id") Long id) {
       dynamicService.del(id);
        return ViewData.builder().message("删除动态成功").build();
    }

    @GetMapping(value = "/dynamic/photos", headers = Const.API_VERSION_1_0_0)
    public ViewData photos(MemberPhotosRequest request) {
        List<String> list = dynamicService.photos(request);
        return ViewData.builder().data(list).message("相册列表").build();
    }




}

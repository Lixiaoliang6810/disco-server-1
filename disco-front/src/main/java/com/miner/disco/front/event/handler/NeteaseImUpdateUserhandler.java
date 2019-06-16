package com.miner.disco.front.event.handler;

import com.miner.disco.front.event.NeteaseImUpdateUserEvent;
import com.miner.disco.netease.support.DefaultNeteaseImClient;
import com.miner.disco.netease.support.JsonParser;
import com.miner.disco.netease.support.domain.NeteaseImUpdateUinfoModel;
import com.miner.disco.netease.support.request.NeteaseImUpdateUinfoRequest;
import com.miner.disco.netease.support.response.NeteaseImUpdateResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @author Created by lubycoder@163.com 2019/1/8
 */
@Slf4j
@Component
public class NeteaseImUpdateUserhandler {

    @Autowired
    private DefaultNeteaseImClient neteaseImClient;

    @Value("${ali.oss.domain}")
    private String ossDomain;

    @Async
    @EventListener
    public void handle(NeteaseImUpdateUserEvent event) {
        NeteaseImUpdateUinfoRequest request = new NeteaseImUpdateUinfoRequest();
        NeteaseImUpdateUinfoModel model = new NeteaseImUpdateUinfoModel();
        model.setAccid(event.getAccid());
        model.setName(event.getName());
        model.setIcon(String.format("%s%s", ossDomain,event.getIcon()));
        request.setBizModel(model);
        NeteaseImUpdateResponse response = neteaseImClient.execute(request);
        log.info("request {} response {}", JsonParser.serializeToJson(request), JsonParser.serializeToJson(response));
        if (!response.isSuccess()) {
            log.error("update im user info error. ");
        }
    }

}

package com.miner.disco.netease.support.request;

import com.miner.disco.netease.support.NeteaseImObject;
import com.miner.disco.netease.support.NeteaseImRequest;
import com.miner.disco.netease.support.response.NeteaseImCreateResponse;

/**
 * Created by linjw on 2017/8/11.
 */
public class NeteaseImCreateRequest implements NeteaseImRequest<NeteaseImCreateResponse> {

    private final static String URL = "https://api.netease.im/nimserver/user/create.action";
    private final static String METHOD = "POST";
    private NeteaseImObject bizModel = null;

    @Override
    public String getMethod() {
        return METHOD;
    }

    @Override
    public String getUrl() {
        return URL;
    }

    @Override
    public void setBizModel(NeteaseImObject bizModel) {
        this.bizModel = bizModel;
    }

    @Override
    public NeteaseImObject getBizModel() {
        return this.bizModel;
    }

    @Override
    public Class<NeteaseImCreateResponse> getResponseClass() {
        return NeteaseImCreateResponse.class;
    }

}

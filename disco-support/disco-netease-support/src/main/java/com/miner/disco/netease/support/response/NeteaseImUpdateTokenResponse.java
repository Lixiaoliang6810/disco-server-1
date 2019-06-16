package com.miner.disco.netease.support.response;

import com.miner.disco.netease.support.NeteaseImResponse;
import com.miner.disco.netease.support.domain.Info;

/**
 * Created by linjw on 2017/10/23.
 */
public class NeteaseImUpdateTokenResponse extends NeteaseImResponse {

    private Info info;

    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }
}

package com.miner.disco.netease.support.response;

import com.miner.disco.netease.support.NeteaseImResponse;
import com.miner.disco.netease.support.domain.Info;

import java.util.List;

/**
 * Created by linjw on 2017/8/27.
 */
public class NeteaseImGetResponse extends NeteaseImResponse {

    private List<Info> uinfos;

    public List<Info> getUinfos() {
        return uinfos;
    }

    public void setUinfos(List<Info> uinfos) {
        this.uinfos = uinfos;
    }
}

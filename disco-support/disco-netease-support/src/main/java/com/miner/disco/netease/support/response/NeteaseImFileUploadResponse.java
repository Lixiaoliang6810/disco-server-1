package com.miner.disco.netease.support.response;

import com.miner.disco.netease.support.NeteaseImResponse;

/**
 * Created by lubycoder@163.com on 2017/8/7.
 */
public class NeteaseImFileUploadResponse extends NeteaseImResponse {

    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

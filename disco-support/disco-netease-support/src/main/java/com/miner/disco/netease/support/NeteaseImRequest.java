package com.miner.disco.netease.support;

/**
 * Created by lubycoder@163.com on 2017/8/7.
 */
public interface NeteaseImRequest<T extends NeteaseImResponse> {

    String getMethod();

    String getUrl();

    void setBizModel(NeteaseImObject bizModel);

    NeteaseImObject getBizModel();

    Class<T> getResponseClass();
}

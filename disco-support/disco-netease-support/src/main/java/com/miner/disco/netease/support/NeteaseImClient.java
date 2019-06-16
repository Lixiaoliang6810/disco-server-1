package com.miner.disco.netease.support;

import com.miner.disco.netease.support.exception.ImException;

/**
 * Created by lubycoder@163.com on 2017/8/7.
 */
public interface NeteaseImClient {

    <T extends NeteaseImResponse> T execute(NeteaseImRequest<T> request) throws ImException;

}

package com.miner.disco.mch.service;

import com.miner.disco.mch.exception.MchBusinessException;
import com.miner.disco.mch.model.response.ClassifySelectorResponse;

import java.util.List;

/**
 * @author Created by lubycoder@163.com 2019-2-19
 */
public interface ClassifyService {

    List<ClassifySelectorResponse> selector() throws MchBusinessException;

}

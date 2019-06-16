package com.miner.disco.front.service;

import com.miner.disco.front.exception.BusinessException;
import com.miner.disco.front.model.request.ReportRequest;

public interface ReportService {
    void report(Long reporterId,ReportRequest request) throws BusinessException;
}

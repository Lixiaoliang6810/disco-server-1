package com.miner.disco.front.service;

import com.miner.disco.basic.model.response.ViewData;
import com.miner.disco.front.exception.BusinessException;
import com.miner.disco.front.model.request.ReportRequest;

public interface ReportService {
    ViewData report(Long reporterId, ReportRequest request) throws BusinessException;
}

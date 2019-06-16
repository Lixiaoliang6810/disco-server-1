package com.miner.disco.front.service.impl;

import com.miner.disco.front.dao.ReportMapper;
import com.miner.disco.front.exception.BusinessException;
import com.miner.disco.front.model.request.ReportRequest;
import com.miner.disco.front.service.ReportService;
import com.miner.disco.pojo.Report;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @ClassName ReportServiceImpl
 * @Auther: wz1016_vip@163.com
 * @Date: 2019/6/16 17:06
 * @Description: TODO
 */
@Service
public class ReportServiceImpl implements ReportService {

    private final ReportMapper reportMapper;

    @Autowired
    public ReportServiceImpl(ReportMapper reportMapper){
        this.reportMapper = reportMapper;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void report(Long reporterId,ReportRequest request) throws BusinessException {
        Report report = new Report();
        BeanUtils.copyProperties(request,report);
        report.setReporterId(reporterId);
        reportMapper.insert(report);
    }
}

package com.miner.disco.front.service.impl;

import com.miner.disco.basic.model.response.ViewData;
import com.miner.disco.front.dao.DynamicMapper;
import com.miner.disco.front.dao.ReportMapper;
import com.miner.disco.front.exception.BusinessException;
import com.miner.disco.front.model.request.ReportRequest;
import com.miner.disco.front.service.ReportService;
import com.miner.disco.pojo.Dynamic;
import com.miner.disco.pojo.Report;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @ClassName ReportServiceImpl
 * @Author: wz1016_vip@163.com
 * @Date: 2019/6/16 17:06
 * @Description: TODO
 */
@Service
public class ReportServiceImpl implements ReportService {
    private ReportMapper reportMapper;
    @Autowired
    private DynamicMapper dynamicMapper;
    @Autowired
    public ReportServiceImpl(ReportMapper reportMapper){
        this.reportMapper = reportMapper;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public ViewData report(Long reporterId, ReportRequest request) throws BusinessException {
        // 动态id 查用户id
        Dynamic dynamic = dynamicMapper.queryByPrimaryKey(request.getDynamicId());
        if(reporterId.intValue() == dynamic.getUserId().intValue()){
            return ViewData.builder().message("不能举报自己").build();
        }
        Report report = new Report();
        BeanUtils.copyProperties(request,report);
        report.setReporterId(reporterId);
        reportMapper.insert(report);
        return ViewData.builder().message("举报成功").build();
    }
}

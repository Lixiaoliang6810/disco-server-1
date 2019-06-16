package com.miner.disco.admin.service.admin.impl;

import com.google.common.collect.Lists;
import com.miner.disco.admin.auth.model.FunctionBean;
import com.miner.disco.admin.auth.model.GroupBean;
import com.miner.disco.admin.auth.model.ModuleBean;
import com.miner.disco.admin.constant.GroupEnum;
import com.miner.disco.admin.dao.system.PermissionDao;
import com.miner.disco.admin.exception.AdminBuzException;
import com.miner.disco.admin.service.admin.PermissionService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: chencx
 * @create: 2019-01-24
 **/
@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionDao permissionDao;

    @Override
    public List<GroupBean> findAuths() throws AdminBuzException {
        List<GroupBean> resultAuths = Lists.newArrayList();
        GroupEnum[] groups = GroupEnum.getGroups();
        if (groups != null){
            for (GroupEnum group : groups){
                GroupBean groupBean = new GroupBean();
                groupBean.setName(group.getDesc());
                List<ModuleBean> moduleBeans = getAuths(group.name());
                if (CollectionUtils.isNotEmpty(moduleBeans)){ //组下没有模块 不显示
                    groupBean.addModuleBean(moduleBeans);
                    resultAuths.add(groupBean);
                }
            }
        }
        return resultAuths;
    }

    List<ModuleBean> getAuths(String groupName){
        List<ModuleBean> moduleBeans = permissionDao.queryModuleByGroup(groupName); //  全部模块
        if (CollectionUtils.isNotEmpty(moduleBeans)){
            moduleBeans.forEach(moduleBean -> {
                List<FunctionBean> functionBeans = permissionDao.queryFunctionByModuleId(moduleBean.getId());
                if (CollectionUtils.isNotEmpty(functionBeans)) moduleBean.addFunctions(functionBeans);
            });
        }
        return moduleBeans;
    }
}

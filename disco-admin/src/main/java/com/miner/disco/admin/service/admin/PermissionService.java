package com.miner.disco.admin.service.admin;

import com.miner.disco.admin.auth.model.GroupBean;
import com.miner.disco.admin.exception.AdminBuzException;

import java.util.List;

/**
 * @author: chencx
 * @create: 2019-01-24
 **/
public interface PermissionService {

    /**
     * 查找权限树
     * @return
     * @throws AdminBuzException
     */
    List<GroupBean> findAuths() throws AdminBuzException;
}

package com.miner.disco.admin.dao.system;

import com.miner.disco.admin.auth.model.FunctionBean;
import com.miner.disco.admin.auth.model.ModuleBean;
import com.miner.disco.admin.model.request.system.PermissionRequest;
import com.miner.disco.pojo.SysModuleFunc;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * @author: chencx
 * @create: 2019-01-08
 **/
public interface PermissionDao {

    /**
     * 查询用户权限
     * @param roleId
     * @return
     */
    List<PermissionRequest> queryPermisByRole(@Param("roleId") Long roleId);

    /**
     * 根据角色ID查询权限IDs
     * @param roleId
     * @return
     */
    Set<Long> queryPermisIdsByRole(@Param("roleId") Long roleId);

    /**
     * 查询所有module
     * @return
     */
    List<ModuleBean> queryModuleByGroup(@Param("group") String group);

    /**
     * 根据模块ID 查询方法
     * @param moduleId
     * @return
     */
    List<FunctionBean> queryFunctionByModuleId(@Param("moduleId") Long moduleId);

    /**
     * 查询所有默认权限
     * @return
     */
    Set<SysModuleFunc> querymoduleDefaultId();
}

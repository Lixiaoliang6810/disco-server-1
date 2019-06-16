package com.miner.disco.admin.dao.system;

import com.miner.disco.admin.model.request.system.SysRoleRequest;
import com.miner.disco.admin.model.response.system.SysRoleResponse;
import com.miner.disco.mybatis.support.BasicMapper;
import com.miner.disco.pojo.SysRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * @author: chencx
 * @create: 2019-01-08
 **/
public interface RoleDao extends BasicMapper<SysRole> {

    /**
     * 分页查询
     * @param search
     * @return
     */
    List<SysRoleResponse> queryPage(@Param("search") SysRoleRequest search, @Param("roleIds") Set<Long> roleIds);

    /**
     * 统计
     * @param search
     * @return
     */
    Integer queryPageCount(@Param("search") SysRoleRequest search, @Param("roleIds") Set<Long> roleIds);

    /**
     * 查询用户角色
     * @param userId
     * @return
     */
//    List<SysRole> queryByUserId(@Param("userId") Long userId);

    /**
     * 查询所有角色
     * @return
     */
    List<SysRoleResponse> queryAll();

    /**
     * 查询用户角色Id
     * @param userId
     * @return
     */
    Set<Long> queryRoleIdsByUserId(@Param("userId") Long userId);

    /**
     * 按角色名查找
     * @param name
     * @return
     */
    SysRole queryByName(@Param("name") String name);

    /**
     * 根据ID删除角色
     * @param id
     */
    void deleteRole(@Param("id") Long id);

    /**
     * 删除菜单关联
     * @param id
     * @param menuIds
     */
    void deleteMenusRelation(@Param("id") Long id, @Param("menuIds") Set<Long> menuIds);

    /**
     * 新增菜单关联
     * @param id
     * @param menuIds
     */
    void relationMenus(@Param("id") Long id, @Param("menuIds") Set<Long> menuIds);

    /**
     * 删除权限关联
     * @param id
     * @param authIds
     */
    void deleteAuthsRelation(@Param("id") Long id, @Param("authIds") Set<Long> authIds);

    /**
     * 新增权限关联
     * @param id
     * @param authIds
     */
    void relationAuths(@Param("id") Long id, @Param("authIds") Set<Long> authIds);
}

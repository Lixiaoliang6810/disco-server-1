package com.miner.disco.admin.service.admin;

import com.miner.disco.admin.exception.AdminBuzException;
import com.miner.disco.admin.model.request.system.RelationAuthRequest;
import com.miner.disco.admin.model.request.system.SysRoleFormRequest;
import com.miner.disco.admin.model.request.system.SysRoleRequest;
import com.miner.disco.admin.model.response.system.MenusTreeResponse;
import com.miner.disco.basic.model.response.PageResponse;

import java.util.List;
import java.util.Set;

/**
 * @author: chencx
 * @create: 2019-01-24
 **/
public interface RoleService {

    /**
     * 分页查询
     * @param search
     * @return
     * @throws AdminBuzException
     */
    PageResponse findPage(SysRoleRequest search, Long userId) throws AdminBuzException;

    /**
     * 新增角色
     * @param role
     * @throws AdminBuzException
     */
    void add(SysRoleFormRequest role) throws AdminBuzException;

    /**
     * 编辑角色
     * @param role
     * @throws AdminBuzException
     */
    void update(SysRoleFormRequest role) throws AdminBuzException;

    /**
     * 删除角色
     * @param id
     * @throws AdminBuzException
     */
    void delete(Long id) throws AdminBuzException;

    /**
     * 关联菜单
     * @param id
     * @param menuIds
     * @throws AdminBuzException
     */
    void relationMenus(Long id, Set<Long> menuIds) throws AdminBuzException;

    /**
     * 授权
     * @param relation
     * @throws AdminBuzException
     */
    void authorization(RelationAuthRequest relation) throws AdminBuzException;

    /**
     * 所有菜单
     * @return
     * @throws AdminBuzException
     */
    List<MenusTreeResponse> allMenu(Long id) throws AdminBuzException;

    /**
     * 角色权限
     * @return
     * @throws AdminBuzException
     */
    Set<Long> findRolePermiss(Long id) throws AdminBuzException;
}

package com.miner.disco.admin.service.admin;

import com.miner.disco.admin.exception.AdminBuzException;
import com.miner.disco.admin.model.request.system.PermissionRequest;
import com.miner.disco.admin.model.request.system.RegistryRequest;
import com.miner.disco.admin.model.request.system.SysUserRequest;
import com.miner.disco.admin.model.request.system.SysUserUpdateRequest;
import com.miner.disco.admin.model.response.system.SysRoleResponse;
import com.miner.disco.admin.model.response.system.UserDetailsResponse;
import com.miner.disco.basic.model.response.PageResponse;
import com.miner.disco.pojo.SysUser;

import java.util.List;
import java.util.Set;

/**
 * @author: chencx
 * @create: 2019-01-08
 **/
public interface UserService {

    /**
     * 系统用户注册
     * @param registry
     * @throws AdminBuzException
     */
    void registry(RegistryRequest registry) throws AdminBuzException;

    /**
     * 根据登陆名查找用户
     * @param loginName
     * @return
     * @throws AdminBuzException
     */
    SysUser findByLoginName(String loginName) throws AdminBuzException;

    /**
     * 根据用户ID 查询用户权限
     * @return
     * @throws AdminBuzException
     */
    List<PermissionRequest> findPermissions(Long id) throws AdminBuzException;

    /**
     * 获取用户所有权限code
     * @param id
     * @return
     * @throws AdminBuzException
     */
    Set<String> findPermissionCodes(Long id) throws AdminBuzException;

    /**
     * 用户信息
     * @param id
     * @return
     * @throws AdminBuzException
     */
    UserDetailsResponse details(Long id) throws AdminBuzException;

    /**
     * 分页查询用户
     * @param search
     * @return
     * @throws AdminBuzException
     */
    PageResponse findPage(SysUserRequest search, Long loginUserId) throws AdminBuzException;

    /**
     * 修改用户状态
     * @param id
     * @param status
     * @throws AdminBuzException
     */
    void modifyStatus(Long id, Integer status) throws AdminBuzException;

    /**
     * 更新用户
     * @param sysUser
     * @throws AdminBuzException
     */
    void update(SysUserUpdateRequest sysUser) throws AdminBuzException;

    /**
     * 删除系统用户
     * @param id
     * @throws AdminBuzException
     */
    void del(Long id) throws AdminBuzException;

    /**
     * 授权
     * @param id 用户ID
     * @param roleIds 角色ID
     * @throws AdminBuzException
     */
    void relationRoles(Long id, Set<Long> roleIds) throws AdminBuzException;

    /**
     * 充值密码
     * @param id
     * @param password
     * @throws AdminBuzException
     */
    void resetPassword(Long id, String password) throws AdminBuzException;

    /**
     * 查询用户角色IDs
     * @param userId
     * @return
     * @throws AdminBuzException
     */
    Set<Long> findUserRoleIds(Long userId) throws AdminBuzException;

    /**
     *
     * @return
     * @throws AdminBuzException
     */
    List<SysRoleResponse> findAllRoles() throws AdminBuzException;
}

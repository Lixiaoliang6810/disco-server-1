package com.miner.disco.admin.dao.system;

import com.miner.disco.admin.model.request.system.SysUserRequest;
import com.miner.disco.admin.model.response.system.SysUserResponse;
import com.miner.disco.mybatis.support.BasicMapper;
import com.miner.disco.pojo.SysUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * @author: chencx
 * @create: 2019-01-08
 **/
public interface UserDao extends BasicMapper<SysUser> {

    /**
     * 根据用户名查找用户
     * @param userName
     * @return
     */
    SysUser queryByUserName(@Param("userName") String userName);

    /**
     * 根据手机号查找用户
     * @param mobile
     * @return
     */
    SysUser queryByMobile(@Param("mobile") String mobile);

    /**
     * 分页查询
     * @param search
     * @return
     */
    List<SysUserResponse> queryPage(@Param("search") SysUserRequest search, @Param("loginUserId") Long loginUserId);

    /**
     * 分页查询统计
     * @param search
     * @return
     */
    Integer queryPageCount(@Param("search") SysUserRequest search, @Param("loginUserId") Long loginUserId);

    /**
     * 根据ID删除用户
     * @param id
     */
    void deleteSysUser(@Param("id") Long id);

    /**
     * 删除角色关联
     * @param userId 用户ID
     * @param roleIds 要删除关联的角色ID
     */
    void deleteRolesRelation(@Param("userId") Long userId, @Param("roleIds") Set<Long> roleIds);

    /**
     * 关联角色
     * @param userId 用户ID
     * @param roleIds 要关联的角色ID
     */
    void relationRole(@Param("userId") Long userId, @Param("roleIds") Set<Long> roleIds);
}

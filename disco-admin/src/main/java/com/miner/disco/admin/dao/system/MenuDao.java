package com.miner.disco.admin.dao.system;

import com.miner.disco.mybatis.support.BasicMapper;
import com.miner.disco.pojo.SysMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * @author: chencx
 * @create: 2019-01-09
 **/
public interface MenuDao extends BasicMapper<SysMenu> {

    /**
     * 查询用户菜单
     *
     * @param userId
     * @return
     */
    List<SysMenu> queryUserMenus(@Param("userId") Long userId);

    /**
     * 查询所有菜单
     * @return
     */
    List<SysMenu> queryAll();

    /**
     * 根据角色ID查询菜单Id
     * @param roleId
     * @return
     */
    Set<Long> queryMenuIdsByRoleId(@Param("roleId") Long roleId);
}

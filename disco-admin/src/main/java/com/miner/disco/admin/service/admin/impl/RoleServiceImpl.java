package com.miner.disco.admin.service.admin.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.miner.disco.admin.dao.system.MenuDao;
import com.miner.disco.admin.dao.system.PermissionDao;
import com.miner.disco.admin.dao.system.RoleDao;
import com.miner.disco.admin.exception.AdminBuzException;
import com.miner.disco.admin.exception.AdminBuzExceptionCode;
import com.miner.disco.admin.model.request.system.RelationAuthRequest;
import com.miner.disco.admin.model.request.system.SysRoleFormRequest;
import com.miner.disco.admin.model.request.system.SysRoleRequest;
import com.miner.disco.admin.model.response.system.MenusTreeResponse;
import com.miner.disco.admin.model.response.system.SysRoleResponse;
import com.miner.disco.admin.service.admin.MenuUtil;
import com.miner.disco.admin.service.admin.RoleService;
import com.miner.disco.basic.assertion.Assert;
import com.miner.disco.basic.model.response.PageResponse;
import com.miner.disco.basic.util.DtoTransition;
import com.miner.disco.pojo.SysMenu;
import com.miner.disco.pojo.SysModuleFunc;
import com.miner.disco.pojo.SysRole;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author: chencx
 * @create: 2019-01-24
 **/
@Service
public class RoleServiceImpl extends MenuUtil implements RoleService {

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private MenuDao menuDao;

    @Autowired
    private PermissionDao permissionDao;

    @Override
    public PageResponse findPage(SysRoleRequest search, Long userId) throws AdminBuzException {
        Set<Long> roleIds = roleDao.queryRoleIdsByUserId(userId);
        List<SysRoleResponse> roles = roleDao.queryPage(search, roleIds);
        Integer count = 0;
        if (CollectionUtils.isNotEmpty(roles)) {
            count = roleDao.queryPageCount(search, roleIds);
        }
        return PageResponse.builder().list(roles).total(count).build();
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = RuntimeException.class)
    public void add(SysRoleFormRequest role) throws AdminBuzException {
        SysRole sysRole = roleDao.queryByName(role.getName());
        Assert.isNull(sysRole, AdminBuzExceptionCode.ROLE_NAME_IS_ALREADY.getCode(), AdminBuzExceptionCode.ROLE_NAME_IS_ALREADY.getMessage());
        SysRole add = (SysRole) DtoTransition.trans(SysRole.class, role);
        add.setCreateDate(new Date());
        add.setUpdateDate(new Date());
        roleDao.insert(add);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = RuntimeException.class)
    public void update(SysRoleFormRequest role) throws AdminBuzException {
        SysRole sysRole = roleDao.queryByPrimaryKey(role.getId());
        Assert.notNull(sysRole, AdminBuzExceptionCode.OBJECT_NOT_EXIST.getCode(), "角色不存在");
        if (!role.getName().equals(sysRole.getName())) {
            SysRole sysRoleByName = roleDao.queryByName(role.getName());
            Assert.isNull(sysRoleByName, AdminBuzExceptionCode.ROLE_NAME_IS_ALREADY.getCode(), AdminBuzExceptionCode.ROLE_NAME_IS_ALREADY.getMessage());
        }
        SysRole update = (SysRole) DtoTransition.trans(SysRole.class, role);
        update.setUpdateDate(new Date());
        roleDao.updateByPrimaryKey(update);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = RuntimeException.class)
    public void delete(Long id) throws AdminBuzException {
        SysRole sysRole = roleDao.queryByPrimaryKey(id);
        Assert.notNull(sysRole, AdminBuzExceptionCode.OBJECT_NOT_EXIST.getCode(), "角色不存在");
        roleDao.deleteRole(id);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = RuntimeException.class)
    public void relationMenus(Long id, Set<Long> menuIds) throws AdminBuzException {
        Set<Long> currentRoleMenusIds = menuDao.queryMenuIdsByRoleId(id);//当前角色已关联菜单
        if (CollectionUtils.isNotEmpty(currentRoleMenusIds)){
            Set<Long> removeIds = Sets.newHashSet(currentRoleMenusIds);
            removeIds.removeAll(menuIds);
            if (CollectionUtils.isNotEmpty(removeIds)){
                roleDao.deleteMenusRelation(id, removeIds); //删除菜单关联
            }
            menuIds.removeAll(currentRoleMenusIds);
        }
        if(CollectionUtils.isNotEmpty(menuIds)){
            roleDao.relationMenus(id, menuIds); //新增菜单关联
        }
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = RuntimeException.class)
    public void authorization(RelationAuthRequest relation) throws AdminBuzException {
        Long id = relation.getId();
        Set<Long> authIds = relation.getRelationIds();
        Set<Long> defaultModuleIds = relation.getDefaultModuleIds();
        if (CollectionUtils.isNotEmpty(defaultModuleIds)){ //有默认权限的模块
            Set<SysModuleFunc> allDefaultIds = permissionDao.querymoduleDefaultId();
            Set<Long> defModuleFuncIds = allDefaultIds.stream().filter(def ->  defaultModuleIds.contains(def.getModuleId())).map(SysModuleFunc :: getId).collect(Collectors.toSet());
            if (CollectionUtils.isNotEmpty(defModuleFuncIds)){
                relation.addRelationIds(defModuleFuncIds);
            }
        }
        Set<Long> currentRoleAuthIds = permissionDao.queryPermisIdsByRole(id); //当前角色已关联权限
        if (CollectionUtils.isNotEmpty(currentRoleAuthIds)){
            Set<Long> removeIds = Sets.newHashSet(currentRoleAuthIds);
            removeIds.removeAll(authIds);
            if (CollectionUtils.isNotEmpty(removeIds)){
                roleDao.deleteAuthsRelation(id, removeIds); //删除权限关联
            }
            authIds.removeAll(currentRoleAuthIds);
        }
        if(CollectionUtils.isNotEmpty(authIds)){
            roleDao.relationAuths(id, authIds); //新增权限关联
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<MenusTreeResponse> allMenu(Long id) throws AdminBuzException {
        List<SysMenu> menus = menuDao.queryAll();
        Set<Long> checkIds = menuDao.queryMenuIdsByRoleId(id);
        List<MenusTreeResponse> treeResponses = this.findRelationMenus(Lists.newCopyOnWriteArrayList(menus), 0L, checkIds);//从第一级菜单开始处理
        return treeResponses;
    }

    @Override
    public Set<Long> findRolePermiss(Long id) throws AdminBuzException {
        Set<Long> permissIds = permissionDao.queryPermisIdsByRole(id);
        return permissIds;
    }

}

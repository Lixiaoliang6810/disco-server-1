package com.miner.disco.admin.service.admin.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.miner.disco.admin.constant.RestConst;
import com.miner.disco.admin.dao.system.MenuDao;
import com.miner.disco.admin.dao.system.PermissionDao;
import com.miner.disco.admin.dao.system.RoleDao;
import com.miner.disco.admin.dao.system.UserDao;
import com.miner.disco.admin.exception.AdminBuzException;
import com.miner.disco.admin.exception.AdminBuzExceptionCode;
import com.miner.disco.admin.model.LoginModule;
import com.miner.disco.admin.model.request.system.PermissionRequest;
import com.miner.disco.admin.model.request.system.RegistryRequest;
import com.miner.disco.admin.model.request.system.SysUserRequest;
import com.miner.disco.admin.model.request.system.SysUserUpdateRequest;
import com.miner.disco.admin.model.response.system.SysRoleResponse;
import com.miner.disco.admin.model.response.system.SysUserResponse;
import com.miner.disco.admin.model.response.system.UserDetailsResponse;
import com.miner.disco.admin.model.response.system.UserMenuResponse;
import com.miner.disco.admin.service.admin.MenuUtil;
import com.miner.disco.admin.service.admin.UserService;
import com.miner.disco.basic.assertion.Assert;
import com.miner.disco.basic.constants.StateEnum;
import com.miner.disco.basic.model.response.PageResponse;
import com.miner.disco.basic.util.DtoTransition;
import com.miner.disco.pojo.SysMenu;
import com.miner.disco.pojo.SysUser;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author: chencx
 * @create: 2019-01-08
 **/
@Service
public class UserServiceImpl extends MenuUtil implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private PermissionDao permissionDao;

    @Autowired
    private MenuDao menuDao;

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void registry(RegistryRequest registry) throws AdminBuzException {
        SysUser selectByUserName = userDao.queryByUserName(registry.getUsername());
        Assert.isNull(selectByUserName, AdminBuzExceptionCode.USER_NAME_ALREADY_EXISTS.getCode(), AdminBuzExceptionCode.USER_NAME_ALREADY_EXISTS.getMessage());

        SysUser selectByUserMobile = userDao.queryByMobile(registry.getMobile());
        Assert.isNull(selectByUserMobile, AdminBuzExceptionCode.USER_MOBILE_ALREADY_EXISTS.getCode(), AdminBuzExceptionCode.USER_MOBILE_ALREADY_EXISTS.getMessage());
        registry.setPassword(registry.getPassword().trim());
        registry.setMobile(registry.getMobile().trim());
        registry.setUsername(registry.getUsername().trim());
        SysUser sysUser = (SysUser) DtoTransition.trans(SysUser.class, registry);

        String salt = new SecureRandomNumberGenerator().nextBytes().toHex();
        sysUser.setSalt(salt);

        salt = salt.concat(registry.getMobile());
        String password = new Md5Hash(sysUser.getPassword(),salt.concat(registry.getMobile().trim()), RestConst.MD5_HASH_ITERATIONS).toString();

        sysUser.setPassword(password);

        sysUser.setSalt(salt);
        LoginModule currentUser = (LoginModule) SecurityUtils.getSubject().getPrincipal();
        sysUser.setCreateUserId(currentUser.getId());
        sysUser.setStatus(StateEnum.ENABLE.getKey());
        sysUser.setCreateDate(new Date());
        sysUser.setUpdateDate(new Date());
        userDao.insert(sysUser);
    }

    @Override
    @Transactional(readOnly = true)
    public SysUser findByLoginName(String loginName) throws AdminBuzException {
        SysUser sysUser = null;
        sysUser = userDao.queryByMobile(loginName);
        if (sysUser == null){
            sysUser = userDao.queryByUserName(loginName);
        }
        return sysUser;
    }

    @Override
    @Transactional(readOnly = true)
    public List<PermissionRequest> findPermissions(Long id) throws AdminBuzException {
        //find user role
        Set<Long> roles = roleDao.queryRoleIdsByUserId(id);

        if (roles == null){
            return null;
        }
        List<PermissionRequest> permissionRequests = Lists.newArrayList();
        //find module by role
        roles.forEach(role -> {
            List<PermissionRequest> permissions = permissionDao.queryPermisByRole(role);
            permissionRequests.addAll(permissions);
        });
        return permissionRequests;
    }

    @Override
    @Transactional(readOnly = true)
    public Set<String> findPermissionCodes(Long id) throws AdminBuzException {
        List<PermissionRequest> permissions =  this.findPermissions(id);
        Set<String> permissCodes = Sets.newHashSet();
        permissions.forEach(permission -> {
            if (StringUtils.isNotBlank(permission.getModuleCode()) && StringUtils.isNotBlank(permission.getFuncCode())){
                permissCodes.add(permission.getModuleCode().concat("-").concat(permission.getFuncCode()));
            }
        });
        return permissCodes;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetailsResponse details(Long id) throws AdminBuzException {
        UserDetailsResponse detailsResponse = new UserDetailsResponse();
        List<SysMenu> menus = menuDao.queryUserMenus(id);
        //user menu
        List<UserMenuResponse> userMenuResponse = this.findChildMenus(Lists.newCopyOnWriteArrayList(menus), 0L);//从第一级菜单开始处理
        detailsResponse.setMenus(userMenuResponse);
        //user permission
        Set<String> permissions = this.findPermissionCodes(id);
        detailsResponse.setPermissions(permissions);
        return detailsResponse;
    }

    @Override
    public PageResponse findPage(SysUserRequest search, Long loginUserId) throws AdminBuzException {
        List<SysUserResponse> list = userDao.queryPage(search, loginUserId);
        Integer count = 0;
        if (CollectionUtils.isNotEmpty(list)){
            count = userDao.queryPageCount(search, loginUserId);
        }
        return PageResponse.builder().list(list).total(count).build();
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = RuntimeException.class)
    public void modifyStatus(Long id, Integer status) throws AdminBuzException {
        SysUser selectUser = userDao.queryByPrimaryKey(id);
        Assert.notNull(selectUser, AdminBuzExceptionCode.OBJECT_NOT_EXIST.getCode(), "用户不存在");
        SysUser update = new SysUser();
        update.setId(id);
        update.setStatus(status);
        userDao.updateByPrimaryKey(update);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = RuntimeException.class)
    public void update(SysUserUpdateRequest sysUser) throws AdminBuzException {
        SysUser selectUser = userDao.queryByPrimaryKey(sysUser.getId());
        Assert.notNull(selectUser, AdminBuzExceptionCode.OBJECT_NOT_EXIST.getCode(), "用户不存在");
        sysUser.setUsername(sysUser.getUsername().trim());
        if (!sysUser.getUsername().equals(selectUser.getUsername())){
            SysUser selectByUserName = userDao.queryByUserName(sysUser.getUsername());
            Assert.isNull(selectByUserName, AdminBuzExceptionCode.USER_NAME_ALREADY_EXISTS.getCode(), AdminBuzExceptionCode.USER_NAME_ALREADY_EXISTS.getMessage());
        }
        SysUser update = (SysUser) DtoTransition.trans(SysUser.class, sysUser);
        update.setUpdateDate(new Date());
        userDao.updateByPrimaryKey(update);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = RuntimeException.class)
    public void del(Long id) throws AdminBuzException {
        SysUser selectUser = userDao.queryByPrimaryKey(id);
        Assert.notNull(selectUser, AdminBuzExceptionCode.OBJECT_NOT_EXIST.getCode(), "用户不存在");
        userDao.deleteSysUser(id);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = RuntimeException.class)
    public void relationRoles(Long id, Set<Long> roleIds) throws AdminBuzException {
        Set<Long> currentUserRoleIds = roleDao.queryRoleIdsByUserId(id); //用户当前拥有角色
        if (CollectionUtils.isNotEmpty(currentUserRoleIds)){
            Set<Long> removeIds = Sets.newHashSet(currentUserRoleIds);
            removeIds.removeAll(roleIds);
            if (CollectionUtils.isNotEmpty(removeIds)){
                userDao.deleteRolesRelation(id, removeIds); //删除角色关联
            }
            roleIds.removeAll(currentUserRoleIds);
        }
        if(CollectionUtils.isNotEmpty(roleIds)){
            userDao.relationRole(id, roleIds); //新增角色关联
        }
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = RuntimeException.class)
    public void resetPassword(Long id, String password) throws AdminBuzException {
        SysUser selectUser = userDao.queryByPrimaryKey(id);
        Assert.notNull(selectUser, AdminBuzExceptionCode.OBJECT_NOT_EXIST.getCode(), "用户不存在");
        password = password.trim();
        String pwd = new Md5Hash(password, selectUser.getSalt().concat(selectUser.getMobile()), RestConst.MD5_HASH_ITERATIONS).toString();
        SysUser resetPwd = new SysUser();
        resetPwd.setId(id);
        resetPwd.setPassword(pwd);
        userDao.updateByPrimaryKey(resetPwd);
    }

    @Override
    public Set<Long> findUserRoleIds(Long userId) throws AdminBuzException {
        Set<Long> roleIds = roleDao.queryRoleIdsByUserId(userId);
        return roleIds;
    }

    @Override
    public List<SysRoleResponse> findAllRoles() throws AdminBuzException {
        List<SysRoleResponse> roleIds = roleDao.queryAll();
        return roleIds;
    }

}

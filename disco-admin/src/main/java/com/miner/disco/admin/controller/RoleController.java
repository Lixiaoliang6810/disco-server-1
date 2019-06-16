package com.miner.disco.admin.controller;

import com.miner.disco.admin.auth.annotation.Func;
import com.miner.disco.admin.auth.annotation.Module;
import com.miner.disco.admin.auth.model.GroupBean;
import com.miner.disco.admin.constant.function.Add;
import com.miner.disco.admin.constant.function.Delete;
import com.miner.disco.admin.constant.function.Edit;
import com.miner.disco.admin.constant.module.admin.RoleManager;
import com.miner.disco.admin.model.LoginModule;
import com.miner.disco.admin.model.request.system.RelationAuthRequest;
import com.miner.disco.admin.model.request.system.RelationRequest;
import com.miner.disco.admin.model.request.system.SysRoleFormRequest;
import com.miner.disco.admin.model.request.system.SysRoleRequest;
import com.miner.disco.admin.model.response.system.MenusTreeResponse;
import com.miner.disco.admin.service.admin.PermissionService;
import com.miner.disco.admin.service.admin.RoleService;
import com.miner.disco.basic.model.response.PageResponse;
import com.miner.disco.basic.model.response.ViewData;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

/**
 * @author: chencx
 * @create: 2019-01-24
 **/
@RestController
@RequestMapping("/admin/role")
@Module(RoleManager.class)
public class RoleController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionService permissionService;

    @GetMapping
    public ViewData page(SysRoleRequest search){
        Subject subject = SecurityUtils.getSubject();
        LoginModule loginModule = (LoginModule) subject.getPrincipal();
        PageResponse result = roleService.findPage(search, loginModule.getId());
        return ViewData.builder().data(result).build();
    }

    @PostMapping
    @Func(Add.class)
    public ViewData add(@RequestBody SysRoleFormRequest sysRole){
        roleService.add(sysRole);
        return ViewData.builder().message("新增角色成功").build();
    }

    @DeleteMapping
    @Func(Delete.class)
    public ViewData del(@RequestParam("id") Long id){
        roleService.delete(id);
        return ViewData.builder().message("删除角色成功").build();
    }

    @PutMapping
    @Func(Edit.class)
    public ViewData update(@RequestBody SysRoleFormRequest sysRole){
        roleService.update(sysRole);
        return ViewData.builder().message("更新角色成功").build();
    }

    @PutMapping("/relation/menu")
    @Func(Edit.class)
    public ViewData relationRoles(@RequestBody @Validated RelationRequest relation){
        roleService.relationMenus(relation.getId(), relation.getRelationIds());
        return ViewData.builder().message("关联菜单成功").build();
    }

    @PutMapping("/authorization")
    @Func(Edit.class)
    public ViewData authorization(@RequestBody @Validated RelationAuthRequest relation){
        roleService.authorization(relation);
        return ViewData.builder().message("授权成功").build();
    }

    @GetMapping("/all/menus")
    public ViewData allMenu(@RequestParam("id") Long id){
        List<MenusTreeResponse> menus = roleService.allMenu(id);
        return ViewData.builder().data(menus).build();
    }

    @GetMapping("/all/permiss")
    public ViewData allPermission(){
        List<GroupBean> result = permissionService.findAuths();
        return ViewData.builder().data(result).build();
    }

    @GetMapping("/permiss")
    public ViewData rolePermiss(@RequestParam("id") Long id){
        Set<Long> result = roleService.findRolePermiss(id);
        return ViewData.builder().data(result).build();
    }

}

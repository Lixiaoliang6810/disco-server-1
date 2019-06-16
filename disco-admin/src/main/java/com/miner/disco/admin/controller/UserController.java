package com.miner.disco.admin.controller;

import com.miner.disco.admin.auth.annotation.Func;
import com.miner.disco.admin.auth.annotation.Module;
import com.miner.disco.admin.constant.function.Add;
import com.miner.disco.admin.constant.function.Delete;
import com.miner.disco.admin.constant.function.Edit;
import com.miner.disco.admin.constant.module.admin.UserManager;
import com.miner.disco.admin.model.LoginModule;
import com.miner.disco.admin.model.request.system.RegistryRequest;
import com.miner.disco.admin.model.request.system.RelationRequest;
import com.miner.disco.admin.model.request.system.SysUserRequest;
import com.miner.disco.admin.model.request.system.SysUserUpdateRequest;
import com.miner.disco.admin.model.response.system.SysRoleResponse;
import com.miner.disco.admin.service.admin.UserService;
import com.miner.disco.basic.constants.BooleanStatus;
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
 * @create: 2019-01-08
 **/
@RestController
@RequestMapping("/admin/user")
@Module(UserManager.class)
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ViewData page(SysUserRequest search){
        Subject subject = SecurityUtils.getSubject();
        LoginModule loginModule = (LoginModule) subject.getPrincipal();
        PageResponse result = userService.findPage(search, loginModule.getId());
        return ViewData.builder().data(result).build();
    }

    @PostMapping
    @Func(Add.class)
    public ViewData registry(@RequestBody @Validated RegistryRequest registry){
        userService.registry(registry);
        return ViewData.builder().message("新增用户成功").build();
    }

    @DeleteMapping
    @Func(Delete.class)
    public ViewData del(@RequestParam("id") Long id){
        userService.del(id);
        return ViewData.builder().message("删除用户成功").build();
    }

    @PutMapping
    @Func(Edit.class)
    public ViewData update(@RequestBody @Validated SysUserUpdateRequest sysUser){
        userService.update(sysUser);
        return ViewData.builder().message("修改用户成功").build();
    }

    @PutMapping("/enable")
    @Func(Edit.class)
    public ViewData enable(@RequestParam("id") Long id){
        userService.modifyStatus(id, BooleanStatus.YES.getKey());
        return ViewData.builder().message("用户启用成功").build();
    }

    @PutMapping("/disable")
    @Func(Edit.class)
    public ViewData disable(@RequestParam("id") Long id){
        userService.modifyStatus(id, BooleanStatus.NO.getKey());
        return ViewData.builder().message("用户禁用成功").build();
    }

    @PutMapping("/relation/role")
    @Func(Edit.class)
    public ViewData relationRoles(@RequestBody @Validated RelationRequest relation){
        userService.relationRoles(relation.getId(), relation.getRelationIds());
        return ViewData.builder().message("关联角色成功").build();
    }

    @PutMapping("/reset/password")
    @Func(Edit.class)
    public ViewData resetPassword(@RequestParam("id") Long id, @RequestParam("password") String password){
        userService.resetPassword(id, password);
        return ViewData.builder().message("密码修改成功").build();
    }

    @GetMapping("/role")
    public ViewData userRoles(@RequestParam("userId") Long userId){
        Set<Long> roles = userService.findUserRoleIds(userId);
        return ViewData.builder().data(roles).build();
    }

    @GetMapping("/all/role")
    public ViewData userRoles(){
        List<SysRoleResponse> roles = userService.findAllRoles();
        return ViewData.builder().data(roles).build();
    }
}

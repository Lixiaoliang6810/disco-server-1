package com.miner.disco.admin.constant.module.admin;


import com.miner.disco.admin.auth.BasicModule;
import com.miner.disco.admin.constant.GroupEnum;

/**
 * 后台管理(ADMIN)
 *
 * 角色管理
 */
public class RoleManager implements BasicModule {

  @Override
  public String getCode() {
    return "system.role";
  }

  @Override
  public String getName() {
    return "角色管理";
  }

  @Override
  public String group() {
    return GroupEnum.ADMIN.group();
  }

}

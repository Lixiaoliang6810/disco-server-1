package com.miner.disco.admin.constant.module.admin;

import com.miner.disco.admin.auth.BasicModule;
import com.miner.disco.admin.constant.GroupEnum;

/**
 * 后台管理(ADMIN)
 *
 * 菜单管理
 */
public class MenuManager implements BasicModule {

  @Override
  public String getCode() {
    return "system.menu";
  }

  @Override
  public String getName() {
    return "菜单管理";
  }

  @Override
  public String group() {
    return GroupEnum.ADMIN.group();
  }


}

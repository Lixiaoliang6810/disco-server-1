package com.miner.disco.admin.service.admin;

import com.google.common.collect.Lists;
import com.miner.disco.admin.model.response.system.MenusTreeResponse;
import com.miner.disco.admin.model.response.system.UserMenuResponse;
import com.miner.disco.basic.util.DtoTransition;
import com.miner.disco.pojo.SysMenu;
import org.apache.commons.collections.CollectionUtils;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author: chencx
 * @create: 2019-01-24
 **/
public abstract class MenuUtil {
    /**
     * 处理菜单父子关系
     *
     * @param sysMenus
     * @param parentId
     * @return
     */
    protected List<UserMenuResponse> findChildMenus(List<SysMenu> sysMenus, Long parentId) {
        List<UserMenuResponse> childMenus = Lists.newArrayList();
        List<SysMenu> remove = Lists.newArrayList();
        Iterator<SysMenu> iterable = sysMenus.iterator();
        while (iterable.hasNext()) {
            SysMenu menu = iterable.next();
            if (menu.getParentId() == parentId) {
                UserMenuResponse userMenuResponse = (UserMenuResponse) DtoTransition.trans(UserMenuResponse.class, menu);
                if (userMenuResponse.getSort() == null) userMenuResponse.setSort(0); //排序必须存在，不然没法往下执行
                List<UserMenuResponse> childs = this.findChildMenus(sysMenus, menu.getId());
                userMenuResponse.setChildren(childs);
                childMenus.add(userMenuResponse);
                remove.add(menu);
            }
        }
        if (CollectionUtils.isNotEmpty(childMenus)) {
            sysMenus.removeAll(remove);
        }
        return  childMenus.stream().sorted(Comparator.comparing(UserMenuResponse :: getSort)).collect(Collectors.toList());
    }

    protected List<MenusTreeResponse> findRelationMenus(List<SysMenu> sysMenus, Long parentId, Set<Long> checked) {
        List<MenusTreeResponse> childMenus = Lists.newArrayList();
        List<SysMenu> remove = Lists.newArrayList();
        Iterator<SysMenu> iterable = sysMenus.iterator();
        while (iterable.hasNext()) {
            SysMenu menu = iterable.next();
            if (menu.getParentId() == parentId) {
                MenusTreeResponse menusTreeResponse = (MenusTreeResponse) DtoTransition.trans(MenusTreeResponse.class, menu);
                menusTreeResponse.setTitle(menu.getMenuName());
                if (checked.contains(menusTreeResponse.getId())) menusTreeResponse.setChecked(true);
                if (menusTreeResponse.getSort() == null) menusTreeResponse.setSort(0); //排序必须存在，不然没法往下执行
                List<MenusTreeResponse> childs = this.findRelationMenus(sysMenus, menu.getId(), checked);
                if (CollectionUtils.isNotEmpty(childs)){
                    menusTreeResponse.setExpand(true);
                    menusTreeResponse.setChildren(childs);
                    List<MenusTreeResponse> checkedChilds = childs.stream().filter(checkMenus -> checkMenus.isChecked()).collect(Collectors.toList());
                    if (CollectionUtils.isNotEmpty(checkedChilds) && checkedChilds.size() < childs.size()){
                        menusTreeResponse.setChecked(false);
                        menusTreeResponse.setIndeterminate(true);
                    }
                }
                childMenus.add(menusTreeResponse);
                remove.add(menu);
            }

        }
        if (CollectionUtils.isNotEmpty(childMenus)) {
            sysMenus.removeAll(remove);
        }
        return  childMenus.stream().sorted(Comparator.comparing(MenusTreeResponse :: getSort)).collect(Collectors.toList());
    }
}

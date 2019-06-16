//package com.miner.disco.admin.util;
//
//import com.google.common.collect.Lists;
//import com.miner.disco.admin.model.response.system.UserMenuResponse;
//import com.miner.disco.basic.util.DtoTransition;
//import com.miner.disco.pojo.SysMenu;
//import org.apache.commons.collections.CollectionUtils;
//
//import java.util.Comparator;
//import java.util.Iterator;
//import java.util.List;
//import java.util.stream.Collectors;
//
///**
// * @author: chencx
// * @create: 2019-02-15
// **/
//public abstract class RelationUtil {
//
//    protected static List findChilds(List<? extends RelationModule> sysMenus, Long parentId, Class<?> result) {
//        List<UserMenuResponse> childMenus = Lists.newArrayList();
//        List<SysMenu> remove = Lists.newArrayList();
//        Iterator<SysMenu> iterable = sysMenus.iterator();
//        while (iterable.hasNext()) {
//            SysMenu menu = iterable.next();
//            if (menu.getParentId() == parentId) {
//                UserMenuResponse userMenuResponse = (UserMenuResponse) DtoTransition.trans(UserMenuResponse.class, menu);
//                if (userMenuResponse.getSort() == null) userMenuResponse.setSort(0); //排序必须存在，不然没法往下执行
//                List<UserMenuResponse> childs = findChilds(sysMenus, menu.getId());
//                userMenuResponse.setChildren(childs);
//                childMenus.add(userMenuResponse);
//                remove.add(menu);
//            }
//        }
//        if (CollectionUtils.isNotEmpty(childMenus)) {
//            sysMenus.removeAll(remove);
//        }
//        return  childMenus.stream().sorted(Comparator.comparing(UserMenuResponse :: getSort)).collect(Collectors.toList());
//    }
//}

package com.miner.disco.admin.constant;

import com.miner.disco.admin.auth.Group;
import com.miner.disco.basic.constants.BasicEnum;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: chencx
 * @create: 2019-01-07
 **/
public enum GroupEnum implements Group, BasicEnum {
    ADMIN("后台管理"),
    MEMBER("会员管理"),
    MERCHANT("商家管理"),
    ;

    private String desc;

    GroupEnum(String desc) {
        this.desc = desc;
    }

    public static boolean hasKey(String key) {
        List<Map<String, Object>> lists = BasicEnum.toList(GroupEnum.class);
        for (int i = 0; i < lists.size(); i++) {
            if (lists.get(i).get("key").equals(key)) {
                return true;
            }
        }
        return false;
    }

    public static Map<String, String> toMap() {
        Map<String, String> map = new HashMap<>();
        GroupEnum[] enums = GroupEnum.class.getEnumConstants();
        for (GroupEnum e : enums) {
            map.put(e.name(), e.desc);
        }
        return map;
    }

    public static GroupEnum[] getGroups(){
        GroupEnum[] enums = GroupEnum.class.getEnumConstants();
        return enums;
    }

    public static List<Map<String, String>> toList() {
        List<Map<String, String>> list = new ArrayList<>();
        GroupEnum[] enums = GroupEnum.class.getEnumConstants();
        for (GroupEnum e : enums) {
            Map<String, String> map = new HashMap<>();
            map.put(e.name(), e.desc);
            list.add(map);
        }
        return list;
    }

   public String getDesc(){
        return this.desc;
   }

    @Override
    public String group() {
        return this.name();
    }

    @Override
    public Integer getKey() {
        return null;
    }

    @Override
    public String getValue() {
        return this.desc;
    }
}

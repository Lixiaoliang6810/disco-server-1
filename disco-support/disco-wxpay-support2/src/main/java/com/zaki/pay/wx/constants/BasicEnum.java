package com.zaki.pay.wx.constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Created by lubycoder@163.com 2018/12/21
 */
public interface BasicEnum {

    String _key = "key";
    String _value = "name";

    Integer getKey();

    String getValue();

    static String getValue(Integer key, Class<? extends BasicEnum> clazz) {
        BasicEnum[] enums = clazz.getEnumConstants();
        for (BasicEnum e : enums) {
            if (e.getKey().equals(key)) {
                return e.getValue();
            }
        }
        return null;
    }

    static List<Map<String, Object>> toList(Class<? extends BasicEnum> clazz) {
        List<Map<String, Object>> list = new ArrayList<>();
        BasicEnum[] enums = clazz.getEnumConstants();
        for (BasicEnum e : enums) {
            Map<String, Object> map = new HashMap<>();
            map.put(BasicEnum._key, e.getKey());
            map.put(BasicEnum._value, e.getValue());
            list.add(map);
        }
        return list;
    }

}

package com.miner.disco.netease.support;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Type;

/**
 * JSON转换工具类 - 依赖google gson框架
 *
 * @author lubycoder@163.com
 */
public class JsonParser {

    public static Gson gson = new GsonBuilder().create();

    public static <T> T deserializeByJson(String data, Type type) {
        if (StringUtils.isEmpty(data)) {
            return null;
        }
        return gson.fromJson(data, type);
    }

    public static <T> T deserializeByJson(String data, Class<T> clz) {
        if (StringUtils.isEmpty(data)) {
            return null;
        }
        return gson.fromJson(data, clz);
    }

    public static <T> String serializeToJson(T t) {
        if (null == t) {
            return "";
        }
        return gson.toJson(t);
    }

    public static <T> String serializeToJsonForGsonBuilder(T t) {
        if (t == null) {
            return "";
        }
        Gson gs = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        return gs.toJson(t);
    }

}

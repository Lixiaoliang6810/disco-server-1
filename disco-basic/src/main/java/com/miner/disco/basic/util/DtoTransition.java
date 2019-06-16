package com.miner.disco.basic.util;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

/**
 * @author lubycoder@163.com
 */
@Slf4j
public class DtoTransition {

    public static Object trans(Class<?> target, Object orig) {
        if (null == target || orig == null) {
            throw new IllegalArgumentException("target or orig is null..");
        }
        try {
            Object dest = target.newInstance();
            BeanUtils.copyProperties(orig, dest);
            return dest;
        } catch (Exception e) {
            log.error("dto trans error", e);
        }
        return null;
    }

}

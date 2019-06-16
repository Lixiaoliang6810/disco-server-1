package com.miner.disco.admin.auth.annotation;
import com.miner.disco.admin.auth.BasicModule;
import org.apache.shiro.authz.annotation.Logical;

import java.lang.annotation.*;

/**
 * 业务模块注解
 * @author: chencx
 * @create: 2018-11-20
 **/
@Target({ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Module {

    /**
     * 模块列表
     * @return 这个Controller含有那些模块
     */
    Class<? extends BasicModule>[] value();

    /**
     * The logical operation for the permission checks in case multiple roles are specified. AND is the default
     * @since 1.1.0
     */
    Logical logical() default Logical.AND;
}

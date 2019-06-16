package com.miner.disco.admin.auth.annotation;


import com.miner.disco.admin.auth.BasicFunction;
import com.miner.disco.admin.auth.BasicModule;

import java.lang.annotation.*;

/**
 * 方法注解
 * @author: chencx
 * @create: 2018-11-20
 **/
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Func {

    /**
     * 这个方法上可以执行那些功能
     */
    Class<? extends BasicFunction> value();

    /**
     * 这些功能属于 target模块
     */
    Class<? extends BasicModule> target() default BasicModule.class;
}

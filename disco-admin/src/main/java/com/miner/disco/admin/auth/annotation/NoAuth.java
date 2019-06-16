package com.miner.disco.admin.auth.annotation;


import java.lang.annotation.*;

/**
 * 不验证
 * @author: chencx
 * @create: 2018-11-20
 **/
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface NoAuth {

}

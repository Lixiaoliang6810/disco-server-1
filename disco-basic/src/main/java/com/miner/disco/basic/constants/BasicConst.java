package com.miner.disco.basic.constants;

import java.nio.charset.Charset;

/**
 * @author Created by lubycoder@163.com 2018/12/21
 */
public class BasicConst {

    public static final Charset UTF_8 = Charset.forName("UTF-8");

    public static final String EMPTY = "";

    public static final String REDIS_NAMESPACE_PREFIX = "disco:";

    /**
     * 订单超时下标
     */
    public static final String ORDERS_OVERTIME_INDEX = REDIS_NAMESPACE_PREFIX.concat("orders:overtime:index");
    /**
     * 订单超时集合
     */
    public static final String ORDERS_OVERTIME_LIST = REDIS_NAMESPACE_PREFIX.concat("orders:overtime:list");

    public static final String SERIALNO_CACHE_PREFIX = "disco:serial:";

    public static final String USER_OAUTH_CACHE_PREFIX = "disco:user:oauth:";

    public static final String MCH_OAUTH_CACHE_PREFIX = "disco:mch:oauth:";

    public static final String REDIS_CACHE_MERCHANT_BROWSE_VOLUME = "disco:mch:browse_volume:%s";

    public static final String REDIS_CACHE_MERCHANT_DAY_BROWSE_VOLUME = "disco:mch:browse_volume:day:%s:%s";

    public static final String REDIS_CACHE_MERCHANT_MONTH_BROWSE_VOLUME = "disco:mch:browse_volume:month:%s:%s";

    public static final String REDIS_CACHE_MERCHANT_RESERVE_ORDERS = "disco:mch:reserve_orders:%s";

}

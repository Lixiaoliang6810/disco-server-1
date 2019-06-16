package com.miner.disco.admin.model.request.orders;

import com.miner.disco.basic.model.request.Pagination;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 * author:linjw Date:2019/1/7 Time:15:09
 */
@Getter
@Setter
public class OrdersSearchRequest extends Pagination implements Serializable {
    /**
     * 订单号
     */
    private String no;
    /**
     * 订单类型
     */
    private Integer type;
    /**
     * 订单状态
     */
    private Integer status;
    /**
     * 买方名称
     */
    private String buyerName;
    /**
     * 卖方名称
     */
    private Long sellerName;
    /**
     * 商品名称
     */
    private String goodsName;
}

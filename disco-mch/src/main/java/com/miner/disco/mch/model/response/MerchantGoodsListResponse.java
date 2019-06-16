package com.miner.disco.mch.model.response;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author Created by lubycoder@163.com 2019/1/9
 */
@Getter
@Setter
public class MerchantGoodsListResponse implements Serializable {

    private static final long serialVersionUID = 2987926830463432861L;

    /**
     * 主键
     */
    private Long goodsId;
    /**
     * 商品名称
     */
    private String name;
    /**
     * 商品价格
     */
    private BigDecimal price;
    /**
     * 商品图片
     */
    private String images;
    /**
     * 规格描述
     */
    private String standard;
    /**
     * 商品状态(1 - 上架 2 - 下架)
     */
    private Integer status;
    /**
     * 销量
     */
    private Integer salesVolume = 0;

}

package com.miner.disco.front.model.response;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author Created by lubycoder@163.com 2019/1/7
 */
@Getter
@Setter
public class MerchantGoodsListResponse implements Serializable {

    private static final long serialVersionUID = -3978148445473944160L;

    private Long goodsId;
    /**
     * 商户主键
     */
    private Long merchantId;
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
     * 最低消费
     */
    private BigDecimal minimumCharge;
    /**
     * 规格描述
     */
    private String standard;
    /**
     * 是否可退定(1 - 不可退定 2 - 可以退定)
     */
    private Integer countermand;
    /**
     * 退定提前时间
     */
    private Integer countermandAheadTime;

}

package com.miner.disco.admin.model.response.merchant;

import java.math.BigDecimal;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

/**
 * author:linjw Date:2019/1/17 Time:19:36
 */
@Getter
@Setter
public class MerchantGoodsDetailResponse {

    /**
     * 主键
     */
    private Long id;
    /**
     * 商户主键
     */
    private Long merchantId;
    /**
     * 商户名称
     */
    private String merchantName;
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
     * 商品状态(1 - 上架 2 - 下架)
     */
    private Integer status;
    /**
     * 是否删除(1 - 正常 2 - 删除)
     */
    private Integer deleted;
    /**
     * 是否可退定(1 - 不可退定 2 - 可以退定)
     */
    private Integer countermand;
    /**
     * 退定提前时间
     */
    private Integer countermandAheadTime;
    /**
     * 销量
     */
    private Integer salesVolume;
    /**
     * 创建时间
     */
    private Date createDate;
    /**
     * 更新时间
     */
    private Date updateDate;
}

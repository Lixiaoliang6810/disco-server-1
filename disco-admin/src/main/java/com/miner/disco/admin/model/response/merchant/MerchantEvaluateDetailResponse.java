package com.miner.disco.admin.model.response.merchant;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;

/**
 * author:linjw Date:2019/1/18 Time:14:43
 */
@Getter
@Setter
public class MerchantEvaluateDetailResponse {

    /**
     * 匿名评价
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
     * 用户主键
     */
    private Long userId;
    /**
     * 用户昵称
     */
    private String userName;
    /**
     * 匿名评价(1 - 匿名  2 - 公开)
     */
    private Integer anonymous;
    /**
     * 分数
     */
    private Double score;
    /**
     * 内容
     */
    private String content;
    /**
     * 图片URL
     */
    private String images;
    /**
     * 创建时间
     */
    private Date createDate;
    /**
     * 更新时间
     */
    private Date updateDate;
}

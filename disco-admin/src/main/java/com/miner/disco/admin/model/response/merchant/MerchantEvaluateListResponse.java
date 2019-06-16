package com.miner.disco.admin.model.response.merchant;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;

/**
 * author:linjw Date:2019/1/4 Time:18:02
 */
@Getter
@Setter
public class MerchantEvaluateListResponse {

    /**
     * 评价主键
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
    private String nickname;
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
     * 创建时间
     */
    private Date createDate;
}

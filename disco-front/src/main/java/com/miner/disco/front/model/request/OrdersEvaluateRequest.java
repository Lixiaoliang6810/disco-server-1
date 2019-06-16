package com.miner.disco.front.model.request;

import com.miner.disco.pojo.MerchantEvaluate;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author Created by lubycoder@163.com 2018/12/29
 */
@Getter
@Setter
public class OrdersEvaluateRequest implements Serializable {

    private static final long serialVersionUID = 8893223854468775191L;

    private static final Integer DEFAULT_ANONYMOUS_STATUS = MerchantEvaluate.ANONYMOUS.NO_ANONYMOUS.getKey();

    /**
     * 用户主键
     */
    private Long userId;
    /**
     * 订单主键
     */
    private Long ordersId;
    /**
     * 分数
     */
    private Float score;
    /**
     * 评论内容
     */
    private String content;
    /**
     * 评论图片
     */
    private String images;
    /**
     * 是否匿名评价
     */
    private Integer anonymous = DEFAULT_ANONYMOUS_STATUS;

}

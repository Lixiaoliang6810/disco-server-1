package com.miner.disco.pojo;


import com.miner.disco.basic.constants.BasicEnum;
import lombok.*;

import java.util.Date;

@Getter
@Setter
public class MerchantEvaluate {

    /**
     * 匿名评价
     */
    private Long id;
    /**
     * 商户主键
     */
    private Long merchantId;
    /**
     * 订单主键
     */
    private Long ordersId;
    /**
     * 用户主键
     */
    private Long userId;
    /**
     * 匿名评价(1 - 匿名  2 - 公开)
     */
    private Integer anonymous;
    /**
     * 分数
     */
    private Float score;
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

    public enum ANONYMOUS implements BasicEnum {
        ANONYMOUS(1, "匿名"),
        NO_ANONYMOUS(2, "公开");


        ANONYMOUS(Integer key, String value) {
            this.key = key;
            this.value = value;
        }

        private Integer key;
        private String value;

        @Override
        public Integer getKey() {
            return this.key;
        }

        @Override
        public String getValue() {
            return this.value;
        }
    }

}

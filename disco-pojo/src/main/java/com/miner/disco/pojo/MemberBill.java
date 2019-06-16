package com.miner.disco.pojo;


import com.miner.disco.basic.constants.BasicEnum;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class MemberBill {

    /**
     * 主键
     */
    private Long id;
    /**
     * 流水号
     */
    private String serialNo;
    /**
     * 用户主键
     */
    private Long userId;
    /**
     * 金额
     */
    private BigDecimal amount;
    /**
     * 收支类型（1 - 收入 2 - 支出）
     */
    private Integer type;
    /**
     * 交易类型
     */
    private Integer tradeType;
    /**
     * 来源(对应交易类型)
     */
    private String source;
    /**
     * 备注
     */
    private String remark;
    /**
     * 来源主键
     */
    private Long sourceId;
    /**
     * 创建时间
     */
    private Date createDate;
    /**
     * 更新时间
     */
    private Date updateDate;

    public enum TYPE implements BasicEnum {
        IN(1, "收入"),
        OUT(2, "支出");

        Integer key;
        String value;

        TYPE(Integer key, String value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public Integer getKey() {
            return this.key;
        }

        @Override
        public String getValue() {
            return this.value;
        }
    }

    public enum TRADE_TYPE implements BasicEnum {
        ONLINE_PURCHASE(1, "线上订购"),
        OFFLINE_PAYMENT(2, "线下付款"),
        WITHDRAW_DEPOSIT(3, "余额提现"),
        GUIDE_ROYALTY(4, "引导提成");

        Integer key;
        String value;

        TRADE_TYPE(Integer key, String value) {
            this.key = key;
            this.value = value;
        }

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

package com.miner.disco.pojo;

import com.miner.disco.basic.constants.BasicEnum;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Created by lubycoder@163.com 2019/1/10
 */
@Getter
@Setter
public class MerchantBill {

    private Long id;
    private Long merchantId;
    private BigDecimal amount;
    private Integer type;
    private Integer tradeType;
    private String source;
    private Long sourceId;
    private String remark;
    private Date createDate;
    private Date updateDate;

    public enum STATUS implements BasicEnum {
        IN(1, "收入"),
        OUT(2, "支出");

        Integer key;
        String value;

        STATUS(Integer key, String value) {
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

    public enum TRADE_STATUS implements BasicEnum {
        ON_LINE(1, "线上预定"),
        OFF_LINE(2, "线下扫码"),
        REFUND(3, "订单退款"),
        SETTLEMENT(4,"余额结算");

        Integer key;
        String value;

        TRADE_STATUS(Integer key, String value) {
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

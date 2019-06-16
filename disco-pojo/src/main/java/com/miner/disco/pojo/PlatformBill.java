package com.miner.disco.pojo;

import com.miner.disco.basic.constants.BasicEnum;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Created by lubycoder@163.com 2019/06/11
 */
@Getter
@Setter
public class PlatformBill {

    private Long id;
    private BigDecimal amount;
    private Integer type;
    private Integer tradeType;
    private String remark;
    private Date createDate;
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

        SWEEP_PAYMENT_TAKE(1, "扫码支付抽成");

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

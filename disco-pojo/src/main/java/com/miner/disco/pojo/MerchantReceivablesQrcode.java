package com.miner.disco.pojo;

import com.miner.disco.basic.constants.BasicEnum;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Created by lubycoder@163.com 2019-2-20
 */
@Getter
@Setter
public class MerchantReceivablesQrcode implements Serializable {

    private static final long serialVersionUID = -1575255630928478499L;

    private Long id;
    private Long mchId;
    private BigDecimal amount;
    private String qrcode;
    private Integer status;
    private Integer payWay;
    private String key;
    private String salt;
    private BigDecimal originalPrice;
    private BigDecimal discountPrice;
    private String metadata;
    private Date createDate;
    private Date updateDate;


    public enum STATUS implements BasicEnum {

        WAIT_PAYMENT(1, "等待支付"),

        PAY_SUCCESS(2, "支付成功");

        Integer key;
        String value;

        @Override
        public Integer getKey() {
            return this.key;
        }

        @Override
        public String getValue() {
            return this.value;
        }

        STATUS(Integer key, String value) {
            this.key = key;
            this.value = value;
        }

    }

}

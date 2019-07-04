package com.miner.disco.pojo;

import com.miner.disco.basic.constants.BasicEnum;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Created by lubycoder@163.com 2019-06-21
 */
@Getter
@Setter
public class MerchantAggregateQrcode {

    private Long id;
    private Long mchId;
    private Integer status;
    private String outTradeNo;
    private String qrcode;
    private String coupon;
    // @Deprecated -> totalPrice
    private BigDecimal totalPrice;
    private BigDecimal originalPrice;
    private BigDecimal winePrice;
    private BigDecimal foodPrice;
    private BigDecimal discountPrice;
    private String metadata;
    private Date createDate;
    private Date updateDate;
    private Date paymentDate;
    private Integer paymentWay;

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

    public enum PAYMENT_WAY implements BasicEnum {

        ALIPAY(1, "支付宝"),
        WXPAY(2, "微信");

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

        PAYMENT_WAY(Integer key, String value) {
            this.key = key;
            this.value = value;
        }
    }


}

package com.miner.disco.front.model.request;

import com.miner.disco.basic.constants.BasicEnum;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author Created by lubycoder@163.com 2019/1/7
 */
@Getter
@Setter
public class OrdersPaymentRequest implements Serializable {

    private static final long serialVersionUID = 8149550835901359174L;

    private Long ordersId;
    private PAYMENT_METHOD pm;


    public enum PAYMENT_METHOD implements BasicEnum {
        ALIPAY(1, "支付宝支付"),
        WXPAY(2, "微信支付"),
        WAP_ALIPAY(3, "支付宝网页支付"),
        WAP_WXPAY(4, "微信网页支付");

        Integer key;
        String value;

        PAYMENT_METHOD(Integer key, String value) {
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

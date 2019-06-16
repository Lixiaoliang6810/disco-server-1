package com.miner.disco.pojo;


import com.miner.disco.basic.constants.BasicEnum;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class Orders {

    /**
     * 主键
     */
    private Long id;
    /**
     * 订单号
     */
    private String no;
    /**
     * 订单类型
     */
    private Integer type;
    /**
     * 订单状态
     */
    private Integer status;
    /**
     * 买方主键
     */
    private Long buyer;
    /**
     * 卖方主键
     */
    private Long seller;
    /**
     * 到店时间
     */
    private Date arrivalTime;
    /**
     * 称呼
     */
    private String salutation;
    /**
     * 姓名
     */
    private String fullname;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 定金
     */
    private BigDecimal earnestMoney;
    /**
     * 尾款
     */
    private BigDecimal tailMoney;
    /**
     * 订单总价
     */
    private BigDecimal totalMoney;
    /**
     * 退款金额
     */
    private BigDecimal refundMoney;
    /**
     * 商品主键
     */
    private Long goodsId;
    /**
     * 创建时间
     */
    private Date createDate;
    /**
     * 支付时间
     */
    private Date paymentDate;
    /**
     * 退款时间
     */
    private Date refundDate;
    /**
     * 更新时间
     */
    private Date updateDate;
    /**
     * 是否拼座
     */
    private Integer assembleSeats;
    /**
     * 拼座位数
     */
    private Integer assembleSeatsCount;
    /**
     * 剩余座位
     */
    private Integer assembleSeatsSurplus;
    /**
     * 拼座价格
     */
    private BigDecimal assembleSeatsMoney;

    public enum STATUS implements BasicEnum {
        WAIT_PAYMENT(1, "待支付"),
        WAIT_CONSUMPTION(2, "待消费"),
        WAIT_EVALUATE(3, "待评价"),
        COMPLETE(4, "完成"),
        REFUND(5, "退款"),
        PAYMENT_TIMEOUT(6, "支付超时");

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

}

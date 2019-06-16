package com.miner.disco.pojo;


import com.miner.disco.basic.constants.BasicEnum;
import lombok.*;

import java.util.Date;

@Getter
@Setter
public class MemberBank {

    /**
     * 主键
     */
    private Long id;
    /**
     * 用户主键
     */
    private Long userId;
    /**
     * 卡类型
     */
    private Integer type;
    /**
     * 银行卡号
     */
    private String cardNo;
    /**
     * 银行名称
     */
    private String bankName;
    /**
     * 持卡人
     */
    private String cardholder;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 是否删除(1 - 正常 2 - 删除)
     */
    private Integer deleted;
    /**
     * 创建时间
     */
    private Date createDate;
    /**
     * 更新时间
     */
    private Date updateDate;

    public enum TYPE implements BasicEnum {

        ALIPAY(1, "支付宝");

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

}

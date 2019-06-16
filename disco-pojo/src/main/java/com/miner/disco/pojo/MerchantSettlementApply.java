package com.miner.disco.pojo;

import com.miner.disco.basic.constants.BasicEnum;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Created by lubycoder@163.com 2019-2-21
 */
@Getter
@Setter
public class MerchantSettlementApply implements Serializable {

    private static final long serialVersionUID = -699129142629846727L;

    /**
     * 主键
     */
    private Long id;
    /**
     * 商户主键
     */
    private Long merchantId;
    /**
     * 结算金额
     */
    private BigDecimal amount;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 审核用户主键
     */
    private Long reviewUserId;
    /**
     * 创建时间
     */
    private Date createDate;
    /**
     * 更新时间
     */
    private Date updateDate;

    public enum STATUS implements BasicEnum {

        WAIT_REVIEW(1, "等待处理"),

        APPLY_ADOPT(2, "申请通过"),

        APPLY_REJECT(3, "申请驳回");

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

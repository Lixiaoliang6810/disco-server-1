package com.miner.disco.pojo;

import com.miner.disco.basic.constants.BasicEnum;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Created by lubycoder@163.com 2019/1/9
 */
@Getter
@Setter
public class WithdrawalApply {

    private Long id;
    private Long userId;
    private Long bankId;
    private Long reviewUserId;
    private BigDecimal amount;
    private Integer status;
    private String matadata;
    private Date createDate;
    private Date updateDate;

    public enum STATUS implements BasicEnum {
        WAIT_PROCESS(1, "待处理"),
        ADOPT_APPLY(2, "通过申请"),
        REJECT_APPLY(3, "驳回申请");

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

package com.miner.disco.pojo;


import com.miner.disco.basic.constants.BasicEnum;
import lombok.*;

import java.util.Date;

@Getter
@Setter
public class OrdersInvitation {

    /**
     * 主键
     */
    private Long id;
    /**
     * 订单主键
     */
    private Long ordersId;
    /**
     * 被邀约人主键
     */
    private Long memberId;
    /**
     * 邀约状态
     */
    private Integer status;
    /**
     * 是否是发起人
     */
    private Integer owner;
    /**
     * 处理理由
     */
    private String reason;
    /**
     * 创建时间
     */
    private Date createDate;
    /**
     * 更新时间
     */
    private Date updateDate;

    public enum STATUS implements BasicEnum {
        WAIT_ACCEPT(1, "等待接受"),
        AGREE_JOIN(2, "同意加入"),
        REFUSE_JOIN(3, "拒绝加入");

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

package com.miner.disco.admin.model.response.orders;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;

/**
 * author:linjw Date:2019/1/7 Time:16:14
 */
@Getter
@Setter
public class InvitationListResponse {

    /**
     * 主键
     */
    private Long id;
    /**
     * 订单主键
     */
    private Long ordersId;
    /**
     * 订单号
     */
    private String no;
    /**
     * 被邀约人主键
     */
    private Integer inviteeId;
    /**
     * 被邀请人名称
     */
    private String inviteeName;
    /**
     * 邀约状态(1 - 等待赴约 2 - 确认赴约 3 - 拒绝赴约 4 - 完成赴约)
     */
    private Integer status;
    /**
     * 邀约状态
     */
    private String statusName;
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
}

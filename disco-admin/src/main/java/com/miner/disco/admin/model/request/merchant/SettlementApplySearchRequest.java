package com.miner.disco.admin.model.request.merchant;

import com.miner.disco.basic.model.request.Pagination;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * author:linjw Date:2019/2/21 Time:11:42
 */
@Getter
@Setter
public class SettlementApplySearchRequest extends Pagination {
    /**
     * 商户名称
     */
    private String merchantName;
    /**
     * 商户电话
     */
    private String mobile;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 审核用户名
     */
    private String reviewUsername;

    /**
     * 申请时间
     */
    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private Date createStartTime;

    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private Date createEndTime;
}

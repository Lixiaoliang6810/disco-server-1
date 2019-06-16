package com.miner.disco.admin.model.request.member;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * author:linjw Date:2019/1/7 Time:17:27
 */
@Getter
@Setter
public class VipApplyAuditingRequest implements Serializable {

    private static final long serialVersionUID = 7191721573773036519L;

    /**
     * 主键
     */
    @NotNull(message = "数据错误")
    private Long id;
    /**
     * 状态
     */
    @Max(value = 3, message = "数据错误")
    @Min(value = 2, message = "数据错误")
    private Integer status;

    /**
     * 审核意见
     */
    private String auditOpinion;
}

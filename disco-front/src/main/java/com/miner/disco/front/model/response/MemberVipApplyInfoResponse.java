package com.miner.disco.front.model.response;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 * @author Created by lubycoder@163.com 2019/1/18
 */
@Getter
@Setter
public class MemberVipApplyInfoResponse implements Serializable {

    private static final long serialVersionUID = -1512606072509917938L;
    /**
     * 真实姓名
     */
    private String fullname;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 审核意见
     */
    private String auditOpinion;
    /**
     * 身份证号码
     */
    private String cardNo;

    public String getCardNo() {
        return StringUtils.isBlank(this.cardNo) ? this.cardNo : this.cardNo.replaceAll("(?<=\\w{3})\\w(?=\\w{4})", "*");
    }
}

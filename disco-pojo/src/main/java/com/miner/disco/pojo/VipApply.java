package com.miner.disco.pojo;


import com.miner.disco.basic.constants.BasicEnum;
import lombok.*;

import java.util.Date;

@Getter
@Setter
public class VipApply {

    /**
     * 主键
     */
    private Long id;
    /**
     * 用户主键
     */
    private Long userId;
    /**
     * 真实姓名
     */
    private String fullname;
    /**
     * 出生年月
     */
    private Date birthday;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 审核意见
     */
    private String auditOpinion;

    /**
     * 审核人
     */
    private Long reviewUserId;
    /**
     * 身份证号码
     */
    private String cardNo;
    /**
     * 身份证正面图片
     */
    private String cardFrontImgUri;
    /**
     * 身份证背面图片
     */
    private String cardBackImgUri;
    /**
     * 生活照
     */
    private String liveImgUri;
    /**
     * 创建时间
     */
    private Date createDate;
    /**
     * 更新时间
     */
    private Date updateDate;


    public enum STATUS implements BasicEnum {
        WAIT_AUDIT(1, "待审核"),
        AUDIT_ADOPT(2, "审核通过"),
        AUDIT_REJECT(3, "审核驳回");

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

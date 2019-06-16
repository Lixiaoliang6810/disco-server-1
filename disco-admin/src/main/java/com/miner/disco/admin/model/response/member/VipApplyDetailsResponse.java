package com.miner.disco.admin.model.response.member;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * author:linjw Date:2019/1/7 Time:17:04
 */
@Getter
@Setter
public class VipApplyDetailsResponse implements Serializable {

    private static final long serialVersionUID = 1114887453356221545L;
    /**
     * 主键
     */
    private Long id;
    /**
     * 昵称
     */
    private String nickname;

    /**
     * 手机号
     */
    private String mobile;

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
     * 身份证号
     */
    private String cardNo;

    /**
     * 审核人姓名
     */
    private String reviewUserName;

    /**
     * 审核人手机
     */
    private String reviewUserMobile;

    /**
     * 审核意见
     */
    private String auditOpinion;

}

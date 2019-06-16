package com.miner.disco.front.model.request;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author Created by lubycoder@163.com 2019/1/8
 */
@Getter
@Setter
public class MemberVipApplyRequest implements Serializable {

    private static final long serialVersionUID = -1943110771196893233L;

    private Long userId;
    private String fullname;
//    private String cardNo;        // TODO: 2019/06/11  需求变动，VIP申请只需填写姓名，且申请后直接通过无需审核

}

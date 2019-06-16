package com.miner.disco.front.model.response;

import com.miner.disco.basic.constants.BasicConst;
import com.miner.disco.basic.constants.BasicEnum;
import com.miner.disco.basic.constants.BooleanStatus;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author Created by lubycoder@163.com 2019/1/7
 */
@Getter
@Setter
public class MemberMeCenterResponse implements Serializable {

    private static final long serialVersionUID = -3188629055680827718L;

    private Long id;
    private Integer vip;
    private Integer leader;
    private String avatar;
    private String nickname;
    private String realname;
    private Integer gender;
    private String label;
    private String slogan;
    private Integer weight;
    private Integer height;
    private Date birthday;
    private String inviteCode;
    private String coupon;
    private String city;
    private BigDecimal balance;
    private String backgroundImage;
    private Integer friendCount;
    private Integer collectCount;
    private Integer vipApplyStatus;
    private List<FriendListResponse> friends;

}

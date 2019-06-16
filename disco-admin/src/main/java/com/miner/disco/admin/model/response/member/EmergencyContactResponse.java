package com.miner.disco.admin.model.response.member;

import java.io.Serializable;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

/**
 * author:linjw Date:2019/1/8 Time:10:40
 */
@Getter
@Setter
public class EmergencyContactResponse implements Serializable {

    /**
     * 主键
     */
    private Long id;
    /**
     * 用户主键
     */
    private Long userId;
    /**
     * 用户昵称
     */
    private String nickname;
    /**
     * 姓名
     */
    private String realname;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 是否删除(1 - 正常 2 - 删除)
     */
    private Integer deleted;
    /**
     * 创建时间
     */
    private Date createDate;
    /**
     * 更新时间
     */
    private Date updateDate;
}

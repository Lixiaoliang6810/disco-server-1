package com.miner.disco.front.model.response;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Created by lubycoder@163.com 2019/1/16
 */
@Getter
@Setter
public class InviteRecordListResponse implements Serializable {

    private static final long serialVersionUID = 585960238079367723L;

    private Long userId;
    private String avatar;
    private String mobile;
    private Date createDate;

//    public String getMobile() {
//        return this.mobile.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
//    }

}

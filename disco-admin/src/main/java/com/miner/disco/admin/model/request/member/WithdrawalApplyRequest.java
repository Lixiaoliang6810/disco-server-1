package com.miner.disco.admin.model.request.member;

import com.miner.disco.basic.model.request.Pagination;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Created by lubycoder@163.com 2019/1/9
 */
@Getter
@Setter
public class WithdrawalApplyRequest extends Pagination implements Serializable {

    private static final long serialVersionUID = 6339338296969974442L;

    private Long userId;

    private String nickname;

    private Integer status;

    private Integer backType;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endDate;

}

package com.miner.disco.admin.model.request.member;

import com.miner.disco.basic.model.request.Pagination;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @author: chencx
 * @create: 2019-01-17
 **/
@Getter
@Setter
public class MemberOrderPageRequest extends Pagination implements Serializable {

    private static final long serialVersionUID = -1340452885203707203L;

    private Long memberId;

    private Integer status;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startArrivalTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endArrivalTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startPaymentDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endPaymentDate;
}

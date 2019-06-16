package com.miner.disco.mch.model.request;

import com.miner.disco.basic.model.request.Pagination;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author Created by lubycoder@163.com 2019/1/9
 */
@Getter
@Setter
public class OrdersListRequest extends Pagination implements Serializable {

    private static final long serialVersionUID = 7877840658855061997L;

    private Long merchantId;
    private Integer status;

}

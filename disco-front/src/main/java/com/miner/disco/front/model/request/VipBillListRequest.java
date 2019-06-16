package com.miner.disco.front.model.request;

import com.miner.disco.basic.model.request.Pagination;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author Created by lubycoder@163.com 2019/06/12
 */
@Getter
@Setter
public class VipBillListRequest extends Pagination implements Serializable {

    private static final long serialVersionUID = -687818446857684941L;

    private Long userId;

}

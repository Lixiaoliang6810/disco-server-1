package com.miner.disco.front.model.request;

import com.miner.disco.basic.model.request.Pagination;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Created by lubycoder@163.com 2018/12/29
 */
@Getter
@Setter
public class MerchantEvaluateListRequest extends Pagination implements Serializable {

    private static final long serialVersionUID = 5270491304841878211L;

    private Long merchantId;

}

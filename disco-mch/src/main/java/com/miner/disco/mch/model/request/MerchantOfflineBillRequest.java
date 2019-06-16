package com.miner.disco.mch.model.request;

import com.miner.disco.basic.model.request.Pagination;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author Created by lubycoder@163.com 2019-2-21
 */
@Getter
@Setter
public class MerchantOfflineBillRequest extends Pagination implements Serializable {

    private static final long serialVersionUID = 3518047831666108947L;

    private Long merchantId;

}

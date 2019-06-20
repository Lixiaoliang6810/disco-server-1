package com.miner.disco.front.model.request;

import com.miner.disco.basic.model.request.Pagination;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author Created by lubycoder@163.com 2019-3-6
 */
@Getter
@Setter
public class AssembleOrdersListRequest extends Pagination implements Serializable {

    private static final long serialVersionUID = 2156258186064520920L;

//    private Integer classifyId;

    private String merchantName;

}

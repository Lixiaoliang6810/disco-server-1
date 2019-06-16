package com.miner.disco.front.model.request;

import com.miner.disco.basic.model.request.Pagination;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author Created by lubycoder@163.com 2018/12/25
 */
@Getter
@Setter
public class FollowListRequest extends Pagination implements Serializable {

    private static final long serialVersionUID = 6078583060911994088L;

    private Long userId;

}

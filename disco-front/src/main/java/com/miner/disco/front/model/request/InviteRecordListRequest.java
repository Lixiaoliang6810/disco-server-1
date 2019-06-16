package com.miner.disco.front.model.request;

import com.miner.disco.basic.model.request.Pagination;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author Created by lubycoder@163.com 2019/1/16
 */
@Getter
@Setter
public class InviteRecordListRequest extends Pagination implements Serializable {

    private static final long serialVersionUID = -641709667538393918L;

    private Long userId;

}

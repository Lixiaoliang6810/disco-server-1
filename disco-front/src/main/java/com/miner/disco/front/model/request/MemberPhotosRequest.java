package com.miner.disco.front.model.request;

import com.miner.disco.basic.model.request.Pagination;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author Created by lubycoder@163.com 2019/1/10
 */
@Getter
@Setter
public class MemberPhotosRequest extends Pagination implements Serializable {

    private static final long serialVersionUID = 8943438117624013296L;

    private Long userId;

}

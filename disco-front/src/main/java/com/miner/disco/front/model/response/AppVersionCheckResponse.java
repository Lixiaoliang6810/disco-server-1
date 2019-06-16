package com.miner.disco.front.model.response;

import com.miner.disco.basic.constants.BasicConst;
import com.miner.disco.basic.constants.BooleanStatus;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Created by lubycoder@163.com 2019-2-14
 */
@Getter
@Setter
public class AppVersionCheckResponse {

    private String file = BasicConst.EMPTY;
    private Integer upgrade = BooleanStatus.NO.getKey();
    private Integer compulsion = BooleanStatus.NO.getKey();

}

package com.miner.disco.pojo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Shield {
    /**
     * 主键id
     */
    Long id;

    /**
     * 当前会员用户id
     */
    Long mid;

    /**
     * 屏蔽用户id
     */
    Long sid;

}

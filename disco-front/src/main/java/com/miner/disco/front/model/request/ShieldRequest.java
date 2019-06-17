package com.miner.disco.front.model.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class ShieldRequest implements Serializable {
    private static final long serialVersionUID = -4298661636299339933L;
    //屏蔽用户ID
    Long id;
}

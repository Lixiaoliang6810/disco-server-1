package com.miner.disco.alipay.support.model.response;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created by lubycoder@163.com on 2018/4/5.
 */
@Getter
@Setter
public class AlipayPreorderResponse implements Serializable {

    private static final long serialVersionUID = 7500271250555382246L;

    private String payInfo;

}

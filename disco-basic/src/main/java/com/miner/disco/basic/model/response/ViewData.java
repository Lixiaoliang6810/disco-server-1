package com.miner.disco.basic.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author Created by lubycoder@163.com 2018/10/31
 */
@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ViewData<T> implements Serializable {

    private static final long serialVersionUID = -143078020652940553L;

    private int status;

    private T data;

    private Integer total;

    private String message;

}

package com.miner.disco.admin.auth.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author: chencx
 * @create: 2018-12-21
 **/
@Getter
@Setter
public class FunctionBean implements Serializable {

  private static final long serialVersionUID = 8733703668607716066L;

  private Long id;

  private String funcCode;  //功能编码
  private String funcName;  //功能名称

}

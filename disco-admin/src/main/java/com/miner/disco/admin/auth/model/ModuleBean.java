package com.miner.disco.admin.auth.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @author: chencx
 * @create: 2018-12-21
 **/
@Getter
@Setter
@ToString
public class ModuleBean implements Serializable {

  private static final long serialVersionUID = -4914865431298446967L;

  private Long id;

  private String moduleCode;  //模块编码
  private String moduleName;  //模块名称

  private String group; // 模块组


  Set<FunctionBean> functions;

  public void addFunction(FunctionBean function) {
    if (this.functions == null) {
      this.functions = new HashSet<>();
    }
    this.functions.add(function);
  }

  public void addFunctions(Collection<FunctionBean> functions) {
    if (this.functions == null) {
      this.functions = new HashSet<>();
    }
    this.functions.addAll(functions);
  }
}

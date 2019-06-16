package com.miner.disco.admin.model.response.system;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * @author: chencx
 * @create: 2019-01-09
 **/
@Getter
@Setter
public class UserDetailsResponse implements Serializable {

    private static final long serialVersionUID = -7542235411044095804L;

    private Set<String> permissions;

    private List<UserMenuResponse> menus;

    private String username;
}

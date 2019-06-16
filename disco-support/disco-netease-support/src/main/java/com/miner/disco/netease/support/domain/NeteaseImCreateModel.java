package com.miner.disco.netease.support.domain;


import com.miner.disco.netease.support.NeteaseImObject;

/**
 * Created by linjw on 2017/8/14.
 */
public class NeteaseImCreateModel extends NeteaseImObject {

    private static final long serialVersionUID = 8750478636852419641L;
    /**
     * 网易云通信ID
     */
    private String accid;
    /**
     * 网易云通讯昵称
     */
    private String name;
    /**
     * 第三方json属性
     */
    private String props;
    /**
     * 网易云头像
     */
    private String icon;
    /**
     * 网易云登陆token
     */
    private String token;

    public String getAccid() {
        return accid;
    }

    public void setAccid(String accid) {
        this.accid = accid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProps() {
        return props;
    }

    public void setProps(String props) {
        this.props = props;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

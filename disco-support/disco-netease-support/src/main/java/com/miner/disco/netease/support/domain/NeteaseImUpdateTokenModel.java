package com.miner.disco.netease.support.domain;

import com.miner.disco.netease.support.NeteaseImObject;

import java.io.Serializable;

/**
 * Created by linjw on 2017/10/23.
 */
public class NeteaseImUpdateTokenModel extends NeteaseImObject implements Serializable {

    private static final long serialVersionUID = 4020145678416612002L;

    private String accid;

    public String getAccid() {
        return accid;
    }

    public void setAccid(String accid) {
        this.accid = accid;
    }
}

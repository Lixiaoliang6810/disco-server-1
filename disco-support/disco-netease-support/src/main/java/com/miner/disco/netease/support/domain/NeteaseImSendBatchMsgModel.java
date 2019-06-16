package com.miner.disco.netease.support.domain;


import com.miner.disco.netease.support.NeteaseImObject;

/**
 * Created by linjw on 2017/8/21.
 */
public class NeteaseImSendBatchMsgModel extends NeteaseImObject {

    private static final long serialVersionUID = -7475368325466475994L;

    private String fromAccid;
    private String toAccids;
    private int type;
    private String body;
    private String attach;


    public String getFromAccid() {
        return fromAccid;
    }

    public void setFromAccid(String fromAccid) {
        this.fromAccid = fromAccid;
    }

    public String getToAccids() {
        return toAccids;
    }

    public void setToAccids(String toAccids) {
        this.toAccids = toAccids;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }
}

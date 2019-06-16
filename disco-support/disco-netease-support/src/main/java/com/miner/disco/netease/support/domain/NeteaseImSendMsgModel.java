package com.miner.disco.netease.support.domain;


import com.miner.disco.netease.support.NeteaseImObject;

/**
 * Created by lubycoder@163.com on 2017/8/7.
 */
public class NeteaseImSendMsgModel extends NeteaseImObject {

    private static final long serialVersionUID = 726802850703879833L;

    private String from;
    private int ope;
    private String to;
    private int type;
    private String body;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public int getOpe() {
        return ope;
    }

    public void setOpe(int ope) {
        this.ope = ope;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
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
}

package com.miner.disco.front.event;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

/**
 * @author Created by lubycoder@163.com 2019/1/8
 */
@Getter
@Setter
public class NeteaseImUpdateUserEvent extends ApplicationEvent {

    private static final long serialVersionUID = -2682324429555610700L;

    private String accid;
    private String name;
    private String icon;

    public NeteaseImUpdateUserEvent(Object source) {
        super(source);
    }

}

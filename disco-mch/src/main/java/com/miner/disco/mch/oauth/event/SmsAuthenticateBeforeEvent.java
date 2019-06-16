package com.miner.disco.mch.oauth.event;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

/**
 * @author Created by lubycoder@163.com 2019/1/9
 */
@Getter
@Setter
public class SmsAuthenticateBeforeEvent extends ApplicationEvent {

    private static final long serialVersionUID = 4443080825995376306L;

    private String mobile;
    private String code;

    public SmsAuthenticateBeforeEvent(Object source) {
        super(source);
    }

}

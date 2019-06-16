package com.miner.disco.front.event;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

/**
 * @author Created by lubycoder@163.com 2019/1/16
 */
@Getter
@Setter
public class InviteMemberEvent extends ApplicationEvent {

    private static final long serialVersionUID = 5170044228511298365L;

    private Long inviteeId;

    private String inviteCode;

    public InviteMemberEvent(Object source) {
        super(source);
    }
}

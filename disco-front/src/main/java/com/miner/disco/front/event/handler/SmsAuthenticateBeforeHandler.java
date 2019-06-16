package com.miner.disco.front.event.handler;

import com.miner.disco.front.model.request.MemberRegisterRequest;
import com.miner.disco.front.oauth.event.SmsAuthenticateBeforeEvent;
import com.miner.disco.front.service.MemberService;
import com.miner.disco.pojo.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @author Created by lubycoder@163.com 2019/1/9
 */
@Component
public class SmsAuthenticateBeforeHandler {

    @Autowired
    private MemberService memberService;

    @EventListener
    public void handle(SmsAuthenticateBeforeEvent event) {
        Member member = memberService.findUserByUsername(event.getMobile());
        if (member != null) return;
        MemberRegisterRequest registerRequest = new MemberRegisterRequest();
        registerRequest.setValidate(false);
        registerRequest.setDigit(event.getCode());
        registerRequest.setMobile(event.getMobile());
        registerRequest.setNickname(event.getNickname());
        memberService.register(registerRequest);
    }

}

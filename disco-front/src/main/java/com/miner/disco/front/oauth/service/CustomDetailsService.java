package com.miner.disco.front.oauth.service;

import com.miner.disco.front.oauth.model.CustomUserDetails;
import com.miner.disco.pojo.Member;
import com.miner.disco.front.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Created by lubycoder@163.com 2018/12/26
 */
@Component
public class CustomDetailsService implements UserDetailsService {

    @Autowired
    private MemberService memberService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Member member = memberService.findUserByUsername(s);
        if (member == null) {
            throw new UsernameNotFoundException("用户名或密码错误");
        }
        return new CustomUserDetails(member.getId(), member.getMobile(), member.getLoginPassword(),
                member.getImAccount(), member.getImPassword());
    }

}

package com.miner.disco.front.event.handler;

import com.miner.disco.basic.constants.BooleanStatus;
import com.miner.disco.basic.util.ShareCodeUtils;
import com.miner.disco.front.dao.InviteRecordMapper;
import com.miner.disco.front.dao.MemberMapper;
import com.miner.disco.front.event.InviteMemberEvent;
import com.miner.disco.pojo.InviteRecord;
import com.miner.disco.pojo.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author Created by lubycoder@163.com 2019/1/16
 */
@Component
public class InviteMemberHandler {

    @Autowired
    private InviteRecordMapper inviteRecordMapper;

    @Autowired
    private MemberMapper memberMapper;

    @EventListener
    @Transactional(rollbackFor = RuntimeException.class)
    public void handle(InviteMemberEvent event) {
        Long inviterId = ShareCodeUtils.codeToId(event.getInviteCode());
        Member user = memberMapper.queryByPrimaryKey(inviterId);
        if (user == null) return;
        if (user.getLeader() != null && user.getLeader().intValue() == BooleanStatus.NO.getKey()) return;
        Integer level = inviteRecordMapper.queryLevelByInviteeId(inviterId);    //邀请者当作被邀者查询邀请级别
        InviteRecord inviteRecord = new InviteRecord();
        inviteRecord.setInviterId(inviterId);
        inviteRecord.setInviteeId(event.getInviteeId());
        inviteRecord.setLevel(level == null ? 1 : level + 1);
        inviteRecord.setCreateDate(new Date());
        inviteRecord.setUpdateDate(new Date());
        inviteRecordMapper.insert(inviteRecord);
    }

}

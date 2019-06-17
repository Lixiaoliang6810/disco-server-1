package com.miner.disco.front.service.impl;

import com.google.common.collect.Lists;
import com.miner.disco.basic.assertion.Assert;
import com.miner.disco.basic.constants.BooleanStatus;
import com.miner.disco.basic.util.DtoTransition;
import com.miner.disco.basic.util.Encrypt;
import com.miner.disco.basic.util.ShareCodeUtils;
import com.miner.disco.front.consts.Const;
import com.miner.disco.front.dao.CollectMapper;
import com.miner.disco.front.dao.FriendMapper;
import com.miner.disco.front.dao.MemberMapper;
import com.miner.disco.front.dao.VipApplyMapper;
import com.miner.disco.front.event.InviteMemberEvent;
import com.miner.disco.front.event.NeteaseImUpdateUserEvent;
import com.miner.disco.front.exception.BusinessException;
import com.miner.disco.front.exception.BusinessExceptionCode;
import com.miner.disco.front.helper.IdentifierService;
import com.miner.disco.front.model.request.FriendListRequest;
import com.miner.disco.front.model.request.MemberInfoModifyRequest;
import com.miner.disco.front.model.request.MemberRegisterRequest;
import com.miner.disco.front.model.request.VipMemberListRequest;
import com.miner.disco.front.model.response.FriendListResponse;
import com.miner.disco.front.model.response.MemberMeCenterResponse;
import com.miner.disco.front.model.response.MemberTaCenterResponse;
import com.miner.disco.front.model.response.VipMemberListResponse;
import com.miner.disco.netease.support.DefaultNeteaseImClient;
import com.miner.disco.netease.support.domain.NeteaseImCreateModel;
import com.miner.disco.netease.support.request.NeteaseImCreateRequest;
import com.miner.disco.netease.support.response.NeteaseImCreateResponse;
import com.miner.disco.pojo.Friend;
import com.miner.disco.pojo.Member;
import com.miner.disco.front.service.MemberService;
import com.miner.disco.pojo.Shield;
import com.miner.disco.pojo.VipApply;
import com.spatial4j.core.context.SpatialContext;
import com.spatial4j.core.distance.DistanceUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Created by lubycoder@163.com 2018/12/24
 */
@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private CollectMapper collectMapper;

    @Autowired
    private FriendMapper friendMapper;

    @Resource(name = "redisTemplate")
    private ValueOperations<String, String> vOps;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private VipApplyMapper vipApplyMapper;

    @Autowired
    private ApplicationEventPublisher publisher;

    @Autowired
    private IdentifierService identifierService;

    @Autowired
    private DefaultNeteaseImClient neteaseImClient;

    @Override
    public Member findUserByUsername(String username) throws BusinessException {
        return memberMapper.queryByMobile(username);
    }

    @Override
    public boolean exist(String mobile) throws BusinessException {
        return memberMapper.queryByMobile(mobile) != null;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void register(MemberRegisterRequest request) throws BusinessException {
        if (request.isValidate()) {
            String cacheCode = vOps.get(String.format(Const.REDIS_CACHE_SMS_PREFIX, request.getMobile()));
            if (StringUtils.isBlank(cacheCode) || !StringUtils.equals(cacheCode, request.getDigit())) {
                throw new BusinessException(BusinessExceptionCode.SMS_CODE_ERROR.getCode(), "验证码错误或已过期");
            }
        }
        Member member = this.findUserByUsername(request.getMobile());
        if (member != null) {
            throw new BusinessException(BusinessExceptionCode.MEMBER_REGISTERED.getCode(), "该手机号已被注册");
        }
        member = (Member) DtoTransition.trans(Member.class, request);
        Assert.notNull(member, BusinessExceptionCode.OBJECT_CONVERSION_ERROR.getCode(), "数据转换错误");
        String salt = UUID.randomUUID().toString();
        String imAccount = String.valueOf(identifierService.generate());
        String imPassword = Encrypt.HMACSHA.hmacSha1(imAccount, salt);
        NeteaseImCreateRequest neteaseImCreateRequest = new NeteaseImCreateRequest();
        NeteaseImCreateModel neteaseImCreateModel = new NeteaseImCreateModel();
        neteaseImCreateModel.setAccid(imAccount);
        neteaseImCreateModel.setToken(imPassword);
        neteaseImCreateRequest.setBizModel(neteaseImCreateModel);
        NeteaseImCreateResponse neteaseImCreateResponse = neteaseImClient.execute(neteaseImCreateRequest);
        if (!neteaseImCreateResponse.isSuccess()) {
            throw new BusinessException(BusinessExceptionCode.OBJECT_CONVERSION_ERROR.getCode(), "IM初始化失败");
        }

        member.setImAccount(imAccount);
        member.setImPassword(imPassword);
        member.setBalance(BigDecimal.ZERO);
        member.setSalt(salt);
        member.setLeader(BooleanStatus.NO.getKey());
        member.setVip(BooleanStatus.NO.getKey());
        member.setIntegral(BigDecimal.ZERO);
        member.setRecommend(BooleanStatus.NO.getKey());
        member.setCreateDate(new Date());
        member.setUpdateDate(new Date());
        if (StringUtils.isNotBlank(request.getLoginPassword())) {
            member.setLoginPassword(passwordEncoder.encode(request.getLoginPassword()));
        }
        memberMapper.insert(member);

        String ivCode = ShareCodeUtils.idToCode(member.getId());
        member.setInviteCode(ivCode);
        String coupon = ShareCodeUtils.idToCode(member.getId());
        member.setCoupon(coupon);
        memberMapper.updateByPrimaryKey(member);

        if (StringUtils.isNotBlank(request.getInviteCode())) {
            InviteMemberEvent inviteMemberEvent = new InviteMemberEvent(this);
            inviteMemberEvent.setInviteeId(member.getId());
            inviteMemberEvent.setInviteCode(request.getInviteCode());
            publisher.publishEvent(inviteMemberEvent);
        }
        vOps.getOperations().delete(String.format(Const.REDIS_CACHE_SMS_PREFIX, request.getMobile()));
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void resetLoginPassword(String mobile, String digit, String password) throws BusinessException {
        String cacheCode = vOps.get(String.format(Const.REDIS_CACHE_SMS_PREFIX, mobile));
        if (!StringUtils.equals(cacheCode, digit)) {
            throw new BusinessException(BusinessExceptionCode.SMS_CODE_ERROR.getCode(), "验证码错误");
        }
        Member member = memberMapper.queryByMobile(mobile);
        if (member == null) {
            throw new BusinessException(BusinessExceptionCode.OBJECT_NOT_FOUND.getCode(), "账号不存在");
        }
        Member memberSave = new Member();
        memberSave.setId(member.getId());
        memberSave.setLoginPassword(passwordEncoder.encode(password.trim()));
        memberMapper.updateByPrimaryKey(memberSave);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void modifyInfo(MemberInfoModifyRequest request) throws BusinessException {
        Member member = (Member) DtoTransition.trans(Member.class, request);
        Assert.notNull(member, BusinessExceptionCode.OBJECT_CONVERSION_ERROR.getCode(), "数据转换错误");
        memberMapper.updateByPrimaryKey(member);

        if (StringUtils.isNotBlank(request.getAvatar()) || StringUtils.isNotBlank(request.getNickname())) {
            Member imModifyMember = memberMapper.queryByPrimaryKey(member.getId());
            NeteaseImUpdateUserEvent neteaseImUpdateUserEvent = new NeteaseImUpdateUserEvent(this);
            neteaseImUpdateUserEvent.setAccid(imModifyMember.getImAccount());
            neteaseImUpdateUserEvent.setName(request.getNickname());
            neteaseImUpdateUserEvent.setIcon(request.getAvatar());
            publisher.publishEvent(neteaseImUpdateUserEvent);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public MemberMeCenterResponse meCenter(Long userId) throws BusinessException {
        Member member = memberMapper.queryByPrimaryKey(userId);
        MemberMeCenterResponse response = (MemberMeCenterResponse) DtoTransition.trans(MemberMeCenterResponse.class, member);
        Assert.notNull(response, BusinessExceptionCode.OBJECT_CONVERSION_ERROR.getCode(), "数据转换错误");
        Integer collectCount = collectMapper.countByUserId(userId);
        response.setCollectCount(collectCount == null ? 0 : collectCount);
        Integer friendCount = friendMapper.countByUserId(userId);
        response.setFriendCount(friendCount == null ? 0 : friendCount);
        if (friendCount != null && friendCount > 0) {
            FriendListRequest friendListRequest = new FriendListRequest();
            friendListRequest.setUserId(userId);
            friendListRequest.setOffset(1);
            friendListRequest.setLimit(3);
            List<FriendListResponse> friendListResponse = friendMapper.queryByUserId(friendListRequest);
            response.setFriends(friendListResponse);
        }
        VipApply vipApply = vipApplyMapper.queryLastApplyByUserId(userId);
        response.setVipApplyStatus(vipApply != null ? vipApply.getStatus() : -1);
        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public MemberTaCenterResponse taCenter(Long currentUserId, Long userId) throws BusinessException {
        Member member = memberMapper.queryByPrimaryKey(userId);
        Assert.notNull(member, BusinessExceptionCode.OBJECT_NOT_FOUND.getCode(), "该用户不存在");
        MemberTaCenterResponse response = (MemberTaCenterResponse) DtoTransition.trans(MemberTaCenterResponse.class, member);
        Assert.notNull(response, BusinessExceptionCode.OBJECT_CONVERSION_ERROR.getCode(), "数据转换错误");
        Friend friend = friendMapper.queryByOwnAndHim(currentUserId, userId);
        response.setFriended(friend != null ? BooleanStatus.YES.getKey() : BooleanStatus.NO.getKey());
        return response;
    }

    @Override
    public List<VipMemberListResponse> vips(VipMemberListRequest request) throws BusinessException {
        List<VipMemberListResponse> responses = memberMapper.queryByVip(request);
        DecimalFormat df = new DecimalFormat("######0.00");
        SpatialContext spatialContext = SpatialContext.GEO;
        if (responses == null) return Lists.newArrayList();
        responses.forEach(m -> {
            Double distance = spatialContext.calcDistance(spatialContext.makePoint(request.getLongitude(), request.getLatitude()),
                    spatialContext.makePoint(m.getLongitude() == null ? 0.0 : m.getLongitude(), m.getLatitude() == null ? 0.0 : m.getLatitude())) * DistanceUtils.DEG_TO_KM;
            m.setDistance(Double.valueOf(df.format(distance)));
        });
        responses = responses.stream()
                .filter(m -> {
                    if (request.getDistance() == -1) {
                        return m.getDistance() > request.getDistance();
                    }
                    return m.getDistance() <= request.getDistance();
                })
                .sorted(Comparator.comparing(VipMemberListResponse::getDistance)).collect(Collectors.toList());
        if (responses.isEmpty()) return responses;
        if (request.getOffset() <= 0 && responses.size() <= request.getLimit()) return responses;
        if (responses.size() <= request.getOffset()) return Lists.newArrayList();
        int index = request.getOffset() + request.getLimit();
        return responses.subList(request.getOffset(), responses.size() < index ? responses.size() - 1 : index);
    }

    @Override
    public Long chatSession(String imAccount) throws BusinessException {
        Member member = memberMapper.queryByImAccount(imAccount);
        return member == null ? -1L : member.getId();
    }

    @Override
    public Long queryUser(String name) {
         return memberMapper.queryUser(name);
    }
}

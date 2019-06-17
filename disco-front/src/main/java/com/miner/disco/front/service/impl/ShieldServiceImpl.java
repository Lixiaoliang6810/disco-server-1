package com.miner.disco.front.service.impl;

import com.miner.disco.front.dao.ShieldMapper;
import com.miner.disco.front.model.request.ShieldRequest;
import com.miner.disco.front.model.response.VipMemberListResponse;
import com.miner.disco.front.service.ShieldService;
import com.miner.disco.pojo.Shield;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName ShieldServiceImpl
 * @Auther: wz1016_vip@163.com
 * @Date: 2019/6/17 16:15
 * @Description: TODO
 */
@Service
public class ShieldServiceImpl implements ShieldService {

    private ShieldMapper shieldMapper;

    @Autowired
    public ShieldServiceImpl(ShieldMapper shieldMapper){
        this.shieldMapper = shieldMapper;
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public void shield(Long mid,ShieldRequest request){
        Shield shield = new Shield();
        shield.setMid(mid);
        shield.setSid(request.getId());
        shieldMapper.insert(shield);
    }

    @Override
    public List<VipMemberListResponse> screenList(Long currentUserId, List<VipMemberListResponse> vips) {
        // 获取当前用户的屏蔽列表
        List<Shield> shieldList = this.getShieldList(currentUserId);
        // 取差集
        return vips.stream().filter(m->
                !shieldList.stream().map(Shield::getSid).collect(Collectors.toList())
                        .contains(m.getUserId())).collect(Collectors.toList());
//        List<VipMemberListResponse> reduce = vips.stream().filter(item -> !shieldList.contains(item)).collect(Collectors.toList());
//        reduce.parallelStream().forEach(System.out :: println);
    }

    private List<Shield> getShieldList(Long currentUserId) {
        Example example = new Example(Shield.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("mid", currentUserId);
        return shieldMapper.selectByExample(example);
    }
}

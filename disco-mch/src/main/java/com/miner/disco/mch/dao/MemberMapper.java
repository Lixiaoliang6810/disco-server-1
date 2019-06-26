package com.miner.disco.mch.dao;

import com.miner.disco.mybatis.support.BasicMapper;
import com.miner.disco.pojo.Member;
import org.apache.ibatis.annotations.Param;

/**
 * @author Created by lubycoder@163.com 2019-06-21
 */
public interface MemberMapper extends BasicMapper<Member> {

    Member queryByPrimaryKeyForUpdate(@Param("id") Long id);

}

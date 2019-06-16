package com.miner.disco.admin.dao.member;

import com.miner.disco.admin.model.response.member.FollowRecordListResponse;
import com.miner.disco.basic.model.request.Pagination;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * author:linjw Date:2019/1/7 Time:17:42
 */
public interface FollowRecordDao {

    /**
     * 偶像列表
     * @param id
     * @return
     */
    List<FollowRecordListResponse> idolList(Pagination page, @Param(value = "id") Long id);

    int countIdol(@Param(value = "id") Long id);

    /**
     * 粉丝列表
     * @param id
     * @return
     */
    List<FollowRecordListResponse> fansList(Pagination page,@Param(value = "id") Long id);

    int countFans(@Param(value = "id")Long id);
}

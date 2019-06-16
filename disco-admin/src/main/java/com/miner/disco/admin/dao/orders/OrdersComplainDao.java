package com.miner.disco.admin.dao.orders;

import com.miner.disco.admin.model.response.orders.ComplainListResponse;
import com.miner.disco.basic.model.request.Pagination;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * author:linjw Date:2019/1/7 Time:15:46
 */
public interface OrdersComplainDao {

    List<ComplainListResponse> complainList(@Param(value = "page") Pagination page, @Param(value = "no") String no);

    int countList(@Param(value = "no") String no);
}

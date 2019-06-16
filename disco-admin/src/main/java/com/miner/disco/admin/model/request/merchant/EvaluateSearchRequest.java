package com.miner.disco.admin.model.request.merchant;

import com.miner.disco.basic.model.request.Pagination;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 * author:linjw Date:2019/1/4 Time:18:12
 */
@Getter
@Setter
public class EvaluateSearchRequest extends Pagination implements Serializable {
    /**
     * 商户主键
     */
    private Long merchantId;
    /**
     * 商户名称
     */
    private String merchantName;
    /**
     * 用户昵称
     */
    private String nickname;
    /**
     * 匿名评价(1 - 匿名  2 - 公开)
     */
    private Integer anonymous;
}

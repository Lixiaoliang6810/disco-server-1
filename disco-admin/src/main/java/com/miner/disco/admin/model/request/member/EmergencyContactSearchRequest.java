package com.miner.disco.admin.model.request.member;

import com.miner.disco.basic.model.request.Pagination;
import lombok.Getter;
import lombok.Setter;

/**
 * author:linjw Date:2019/1/8 Time:10:54
 */
@Getter
@Setter
public class EmergencyContactSearchRequest extends Pagination {

    private String nickname;

}

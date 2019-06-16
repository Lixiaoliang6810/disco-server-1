package com.miner.disco.front.model.request;



import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author Created by lubycoder@163.com 2018/12/24
 */

@Getter
@Setter
public class MemberRegisterRequest implements Serializable {

    private static final long serialVersionUID = -5170092317996529461L;

    private String digit;
    private String mobile;
    private String nickname;
    private String inviteCode;
    private String loginPassword;
    private boolean validate = true;

}

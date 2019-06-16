package com.miner.disco.front.model.request;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Created by lubycoder@163.com 2019-2-14
 */
@Getter
@Setter
public class FeedbackRequest {

    private Long userId;
    private String content;

}

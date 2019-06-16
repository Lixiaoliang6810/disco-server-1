package com.miner.disco.mch.model.response;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author Created by lubycoder@163.com 2019/1/9
 */
@Getter
@Setter
public class IndexResponse implements Serializable {

    private static final long serialVersionUID = -1981013644649212348L;

    private Integer todayOrdersAmount = 0;
    private Integer todayBrowseAmount = 0;
    private BigDecimal todayIncomeMoney = BigDecimal.ZERO;
    private Integer thisMonthOrdersAmount = 0;
    private Integer thisMonthBrowseAmount = 0;
    private BigDecimal thisMonthIncomeMoney = BigDecimal.ZERO;
    private Integer totalOrdersAmount = 0;
    private Integer totalBrowseAmount = 0;
    private BigDecimal totalIncomeMoney = BigDecimal.ZERO;
    private Integer currentOrdersAmount = 0;

//    public String getReceivablesCode() {
//        return String.format("disco://merchant/receivables?code=%s", receivables);
//    }

}

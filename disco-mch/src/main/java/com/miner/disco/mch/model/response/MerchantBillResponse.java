package com.miner.disco.mch.model.response;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author Created by lubycoder@163.com 2019-2-18
 */
@Setter
@Getter
public class MerchantBillResponse implements Serializable {

    private static final long serialVersionUID = -2311195209672794378L;

    private BigDecimal onlineIncome = BigDecimal.ZERO;

    private BigDecimal unLineIncome = BigDecimal.ZERO;

    private BigDecimal usableBalance = BigDecimal.ZERO;

    private Map<Integer, MerchantBillListWrap> bills;

    @Getter
    @Setter
    public static class MerchantBillListWrap implements Serializable {

        private static final long serialVersionUID = -6038172917067051347L;

        private BigDecimal onlineIncome = BigDecimal.ZERO;

        private BigDecimal unLineIncome = BigDecimal.ZERO;

        private List<MerchantBillListResponse> bills;

    }

}

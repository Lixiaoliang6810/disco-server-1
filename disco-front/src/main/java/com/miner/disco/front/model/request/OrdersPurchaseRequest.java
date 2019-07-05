package com.miner.disco.front.model.request;

import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.BrowserType;
import eu.bitwalker.useragentutils.DeviceType;
import eu.bitwalker.useragentutils.OperatingSystem;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Created by lubycoder@163.com 2019/1/7
 */
@Getter
@Setter
public class OrdersPurchaseRequest implements Serializable {

    private static final long serialVersionUID = -4836658884971088742L;

    private Long userId;
    private Long goodsId;
    private Date arrivalTime;
    private String fullname;
    /**
     * 预定人数
     */
    private Integer number;
    private String mobile;
    private String remark;
    private String salutation;
    private Integer channel;

    public void setChannel(Browser browser, OperatingSystem os) {
        if (browser.getBrowserType() == BrowserType.MOBILE_BROWSER) {
            this.channel = CHANEL.WEB.getKey();
        } else if (browser.getBrowserType() == BrowserType.UNKNOWN && (os.getName().contains("iOS") || os.getName().contains("Mac"))) {
            this.channel = CHANEL.IOS.getKey();
        } else if (browser.getBrowserType() == BrowserType.UNKNOWN && (os.getName().contains("Android"))) {
            this.channel = CHANEL.AOS.getKey();
        } else {
            this.channel = CHANEL.OTHER.getKey();
        }
    }

    public enum CHANEL {
        OTHER(1),
        IOS(2),
        AOS(3),
        WEB(3);

        Integer key;

        CHANEL(Integer key) {
            this.key = key;
        }

        public Integer getKey() {
            return key;
        }
    }

}

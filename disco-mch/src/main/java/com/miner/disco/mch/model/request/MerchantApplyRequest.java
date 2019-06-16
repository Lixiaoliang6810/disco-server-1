package com.miner.disco.mch.model.request;

import com.spatial4j.core.io.GeohashUtils;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author Created by lubycoder@163.com 2019/1/15
 */
@Getter
@Setter
public class MerchantApplyRequest implements Serializable {

    private static final long serialVersionUID = 363012200750846939L;

    private Long id;
    private String name;
    private Long classifyId;
    private String address;
    private Double longitude;
    private Double latitude;
    private String city;
    private String logo;
    private String businessLicense;
    private String contactName;
    private String contactMobile;
    private String geohash;

    public String getGeohash() {
        return longitude != null && latitude != null ? GeohashUtils.encodeLatLon(latitude, longitude) : null;
    }

}

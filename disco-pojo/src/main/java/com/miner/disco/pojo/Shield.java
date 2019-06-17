package com.miner.disco.pojo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Table(name = "ds_shield")
@Entity
@Data
public class Shield {
    /**
     * 主键id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    /**
     * 当前会员用户id
     */
    Long mid;

    /**
     * 屏蔽用户id
     */
    Long sid;

}

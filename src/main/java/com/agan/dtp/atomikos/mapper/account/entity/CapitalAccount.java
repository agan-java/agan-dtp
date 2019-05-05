package com.agan.dtp.atomikos.mapper.account.entity;

import java.util.Date;
import javax.persistence.*;
import lombok.Data;

@Data
@Table(name = "capital_account")
public class CapitalAccount {
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    /**
     * 用户ID
     */
    @Column(name = "user_id")
    private Integer userId;

    /**
     * 账户余额
     */
    @Column(name = "balance_amount")
    private Long balanceAmount;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private Date updateTime;
}
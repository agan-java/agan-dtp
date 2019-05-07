package com.agan.dtp.atomikos.service.impl;

import com.agan.dtp.atomikos.mapper.account.entity.CapitalAccount;
import com.agan.dtp.atomikos.mapper.account.mapper.CapitalAccountMapper;
import com.agan.dtp.atomikos.mapper.redaccount.entity.RedPacketAccount;
import com.agan.dtp.atomikos.mapper.redaccount.mapper.RedPacketAccountMapper;
import com.agan.dtp.atomikos.service.PayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PayServiceImpl implements PayService {

    @Autowired
    private CapitalAccountMapper capitalAccountMapper;
    @Autowired
    private RedPacketAccountMapper redPacketAccountMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void pay(int userId,int account, int redAccount) {
        CapitalAccount ca=new CapitalAccount();
        ca.setUserId(userId);
        CapitalAccount capitalDTO=this.capitalAccountMapper.selectOne(ca);
        System.out.println(capitalDTO);
        //账户余额扣除
        capitalDTO.setBalanceAmount(capitalDTO.getBalanceAmount()-account);
        this.capitalAccountMapper.updateByPrimaryKey(capitalDTO);

        RedPacketAccount red= new RedPacketAccount();
        red.setUserId(userId);
        RedPacketAccount redDTO=this.redPacketAccountMapper.selectOne(red);
        System.out.println(redDTO);
        //红包余额扣除
        redDTO.setBalanceAmount(redDTO.getBalanceAmount()-redAccount);
        this.redPacketAccountMapper.updateByPrimaryKey(redDTO);
        //int n=9/0;
    }
}

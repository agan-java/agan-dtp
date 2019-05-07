package com.agan;

import com.agan.dtp.atomikos.service.PayService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class AtomikosTests {

    @Autowired
    PayService payService;

    @Test
    //@Transactional
    public void contextLoads() {
        try {
           this.payService.pay(1,10,10);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


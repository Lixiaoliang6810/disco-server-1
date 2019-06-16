package com.miner.disco.admin;

import com.miner.disco.admin.auth.scan.AuthAbstractScan;
import com.miner.disco.admin.auth.scan.AuthScan;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DiscoAdminApplication.class)
public class DiscoAdminApplicationTests {

    @Autowired
    private AuthAbstractScan authAbstractScan;

    @Test
    public void contextLoads() {
        authAbstractScan.scan();
    }

}

